package com.anahoret.services;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.anahoret.actors.*;
import com.anahoret.actors.messages.api_call.GetProfilePicture;
import com.anahoret.actors.messages.api_call.GetUserConnection;
import com.anahoret.actors.messages.api_call.GetUserProfile;
import com.anahoret.config.AppProperties;
import org.springframework.stereotype.Service;

@Service
public class InitService {

    private ActorSystem actorSystem;
    private AppProperties appProperties;
    private APICallService apiCallService;
    private DBManagerService dbManagerService;

    // Change flags to turn on/off checks
    private boolean checkUserProfile = true;
    private boolean checkUserConnection = true;
    private boolean checkProfilePicture = true;

    public InitService(ActorSystem actorSystem, AppProperties appProperties,
                       APICallService apiCallService, DBManagerService dbManagerService) {
        this.actorSystem = actorSystem;
        this.appProperties = appProperties;
        this.apiCallService = apiCallService;
        this.dbManagerService = dbManagerService;
    }

    public void initialCheck() {
        if (checkUserProfile || checkUserConnection || checkProfilePicture) {
            int userProfileCount = checkUserProfile ? 500 : 0;
            int userProfilePictureCount = checkProfilePicture ? 700 : 0;
            int userConnectionCount = checkUserConnection ? 600 : 0;

            Props workProgressTrackerActorProps = Props.create(WorkProgressTrackerActor.class,
                userProfileCount, userProfilePictureCount, userConnectionCount);
            ActorRef workProgressTracker = actorSystem.actorOf(workProgressTrackerActorProps, "workProgressTracker");

            ActorRef getProfilePictureAPICallActor = createApiRouter(GetProfilePictureAPICallActor.class, "GetProfilePictureAPICallActor", apiCallService);
            ActorRef getUserConnectionAPICallActor = createApiRouter(GetUserConnectionAPICallActor.class, "GetUserConnectionAPICallActor", apiCallService);
            ActorRef getUserProfileAPICallActor = createApiRouter(GetUserProfileAPICallActor.class, "GetUserProfileAPICallActor", apiCallService);

            createApiRouter(SaveProfilePictureDBManagerActor.class, "SaveProfilePictureDBManagerActor", dbManagerService, workProgressTracker);
            createApiRouter(SaveUserConnectionDBManagerActor.class, "SaveUserConnectionDBManagerActor", dbManagerService, workProgressTracker);
            createApiRouter(SaveUserProfileDBManagerActor.class, "SaveUserProfileDBManagerActor", dbManagerService, workProgressTracker);

            checkUserProfile(getUserProfileAPICallActor);
            checkUserConnection(getUserConnectionAPICallActor);
            checkProfilePicture(getProfilePictureAPICallActor);
        }
    }

    private ActorRef createApiRouter(Class<?> clazz, String name, Object... args) {
        RoundRobinPool apiCallRoundRobinPool = new RoundRobinPool(appProperties.getRouterPoolSize());
        Props apiCallRouteeProps = Props.create(clazz, args);
        return actorSystem.actorOf(apiCallRoundRobinPool.props(apiCallRouteeProps), name);
    }

    private void checkProfilePicture(ActorRef apiCallActor) {
        if (checkProfilePicture) {
            for (int i = 0; i < 700; i++) {
                apiCallActor.tell(new GetProfilePicture(i), ActorRef.noSender());
            }
        }
    }

    private void checkUserConnection(ActorRef apiCallActor) {
        if (checkUserConnection) {
            for (int i = 0; i < 600; i++) {
                apiCallActor.tell(new GetUserConnection(i), ActorRef.noSender());
            }
        }
    }

    private void checkUserProfile(ActorRef apiCallActor) {
        if (checkUserProfile) {
            for (int i = 0; i < 500; i++) {
                apiCallActor.tell(new GetUserProfile(i), ActorRef.noSender());
            }
        }
    }


}
