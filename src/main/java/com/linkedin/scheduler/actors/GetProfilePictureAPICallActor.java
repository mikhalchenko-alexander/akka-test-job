package com.linkedin.scheduler.actors;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.linkedin.scheduler.actors.messages.api_call.GetProfilePicture;
import com.linkedin.scheduler.actors.messages.db_manager.SaveProfilePicture;
import com.linkedin.scheduler.services.APICallService;

public class GetProfilePictureAPICallActor extends UntypedActor {

    private APICallService apiCallService;

    public GetProfilePictureAPICallActor(APICallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof GetProfilePicture) {
            GetProfilePicture getProfilePicture = (GetProfilePicture) message;
            long result = apiCallService.userProfilePictureAPI(getProfilePicture.getId());
            forwardToDbManagerRouter(new SaveProfilePicture(result, getProfilePicture.getWorkProgressTracker()));
        }
    }

    private void forwardToDbManagerRouter(Object message) {
        ActorSelection dbManagerRouter = getContext().system().actorSelection("/user/SaveProfilePictureDBManagerActor");
        dbManagerRouter.tell(message, self());
    }

}
