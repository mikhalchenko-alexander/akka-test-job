package com.linkedin.scheduler.services;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.linkedin.scheduler.actors.*;
import com.linkedin.scheduler.actors.messages.api_call.GetProfilePicture;
import com.linkedin.scheduler.actors.messages.api_call.GetUserConnection;
import com.linkedin.scheduler.actors.messages.api_call.GetUserProfile;
import com.linkedin.scheduler.config.AppProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InitService {

    private ActorSystem actorSystem;
    private AppProperties appProperties;
    private APICallService apiCallService;
    private DBManagerService dbManagerService;
    private TaskFlagService taskFlagService;

    public InitService(ActorSystem actorSystem, AppProperties appProperties,
                       APICallService apiCallService, DBManagerService dbManagerService,
                       TaskFlagService taskFlagService) {
        this.actorSystem = actorSystem;
        this.appProperties = appProperties;
        this.apiCallService = apiCallService;
        this.dbManagerService = dbManagerService;
        this.taskFlagService = taskFlagService;

        // Change flags to enable/disable checks
        taskFlagService.setFlagEnabled("checkUserProfile", true);
        taskFlagService.setFlagEnabled("checkUserConnection", true);
        taskFlagService.setFlagEnabled("checkProfilePicture", true);
    }

    private void initialCheck() {
        initRouters();
        check();
    }

    private void check() {
        boolean checkUserProfile = taskFlagService.isFlagEnabled("checkUserProfile");
        boolean checkUserConnection = taskFlagService.isFlagEnabled("checkUserConnection");
        boolean checkProfilePicture = taskFlagService.isFlagEnabled("checkProfilePicture");

        if (checkUserProfile || checkUserConnection || checkProfilePicture) {
            int userProfileCount = checkUserProfile ? 500 : 0;
            int userProfilePictureCount = checkProfilePicture ? 700 : 0;
            int userConnectionCount = checkUserConnection ? 600 : 0;

            Props workProgressTrackerActorProps = Props.create(WorkProgressTrackerActor.class,
                    userProfileCount, userProfilePictureCount, userConnectionCount);
            ActorRef workProgressTracker = actorSystem.actorOf(workProgressTrackerActorProps, "workProgressTracker");

            checkUserProfile(workProgressTracker);
            checkUserConnection(workProgressTracker);
            checkProfilePicture(workProgressTracker);
        }
    }

    private void initRouters() {
        createApiRouter(GetProfilePictureAPICallActor.class, "GetProfilePictureAPICallActor",
                        appProperties.getProfilePictureApiRouterPoolSize(), apiCallService);

        createApiRouter(SaveProfilePictureDBManagerActor.class, "SaveProfilePictureDBManagerActor",
                appProperties.getProfilePictureDbRouterPoolSize(), dbManagerService);

        createApiRouter(GetUserConnectionAPICallActor.class, "GetUserConnectionAPICallActor",
                        appProperties.getUserConnectionApiRouterPoolSize(), apiCallService);

        createApiRouter(SaveUserConnectionDBManagerActor.class, "SaveUserConnectionDBManagerActor",
                appProperties.getUserConnectionDbRouterPoolSize(), dbManagerService);

        createApiRouter(GetUserProfileAPICallActor.class, "GetUserProfileAPICallActor",
                        appProperties.getUserProfileApiRouterPoolSize(), apiCallService);

        createApiRouter(SaveUserProfileDBManagerActor.class, "SaveUserProfileDBManagerActor",
                appProperties.getUserProfileDbRouterPoolSize(), dbManagerService);
    }

    private ActorRef createApiRouter(Class<?> clazz, String name, int poolSize, Object... args) {
        RoundRobinPool apiCallRoundRobinPool = new RoundRobinPool(poolSize);
        Props apiCallRouteeProps = Props.create(clazz, args);
        return actorSystem.actorOf(apiCallRoundRobinPool.props(apiCallRouteeProps), name);
    }

    private void checkProfilePicture(ActorRef workProgressTracker) {
        boolean checkProfilePicture = taskFlagService.isFlagEnabled("checkProfilePicture");
        if (checkProfilePicture) {
            ActorSelection getProfilePictureAPICallActor = actorSystem.actorSelection("/user/GetProfilePictureAPICallActor");
            for (int i = 0; i < 700; i++) {
                getProfilePictureAPICallActor.tell(new GetProfilePicture(i, workProgressTracker), ActorRef.noSender());
            }
        }
    }

    private void checkUserConnection(ActorRef workProgressTracker) {
        boolean checkUserConnection = taskFlagService.isFlagEnabled("checkUserConnection");
        ActorSelection getUserConnectionAPICallActor = actorSystem.actorSelection("/user/GetUserConnectionAPICallActor");
        if (checkUserConnection) {
            for (int i = 0; i < 600; i++) {
                getUserConnectionAPICallActor.tell(new GetUserConnection(i, workProgressTracker), ActorRef.noSender());
            }
        }
    }

    private void checkUserProfile(ActorRef workProgressTracker) {
        boolean checkUserProfile = taskFlagService.isFlagEnabled("checkUserProfile");
        if (checkUserProfile) {
            ActorSelection getUserProfileAPICallActor = actorSystem.actorSelection("/user/GetUserProfileAPICallActor");
            for (int i = 0; i < 500; i++) {
                getUserProfileAPICallActor.tell(new GetUserProfile(i, workProgressTracker), ActorRef.noSender());
            }
        }
    }

    @PostConstruct
    public void init() {
        initialCheck();
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void reportCurrentTime() {
        check();
    }
}
