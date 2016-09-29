package com.anahoret.actors;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.anahoret.actors.messages.api_call.GetProfilePicture;
import com.anahoret.actors.messages.db_manager.SaveProfilePicture;
import com.anahoret.services.APICallService;

public class GetProfilePictureAPICallActor extends UntypedActor {

    private APICallService apiCallService;

    public GetProfilePictureAPICallActor(APICallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof GetProfilePicture) {
            long result = apiCallService.userProfilePictureAPI(((GetProfilePicture) message).getId());
            forwardToDbManagerRouter(new SaveProfilePicture(result));
        }
    }

    private void forwardToDbManagerRouter(Object message) {
        ActorSelection dbManagerRouter = getContext().system().actorSelection("/user/SaveProfilePictureDBManagerActor");
        dbManagerRouter.tell(message, self());
    }

}
