package com.anahoret.actors;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.anahoret.actors.messages.api_call.GetUserProfile;
import com.anahoret.actors.messages.db_manager.SaveUserProfile;
import com.anahoret.services.APICallService;

public class GetUserProfileAPICallActor extends UntypedActor {

    private APICallService apiCallService;

    public GetUserProfileAPICallActor(APICallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof GetUserProfile) {
            GetUserProfile getUserProfile = (GetUserProfile) message;
            long result = apiCallService.userProfilesAPI(getUserProfile.getId());
            forwardToDbManagerRouter(new SaveUserProfile(result, getUserProfile.getWorkProgressTracker()));
        }
    }

    private void forwardToDbManagerRouter(Object message) {
        ActorSelection dbManagerRouter = getContext().system().actorSelection("/user/SaveUserProfileDBManagerActor");
        dbManagerRouter.tell(message, self());
    }

}
