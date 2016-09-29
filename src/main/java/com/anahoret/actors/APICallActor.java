package com.anahoret.actors;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.anahoret.actors.messages.api_call.GetProfilePicture;
import com.anahoret.actors.messages.api_call.GetUserConnection;
import com.anahoret.actors.messages.api_call.GetUserProfile;
import com.anahoret.actors.messages.db_manager.SaveProfilePicture;
import com.anahoret.actors.messages.db_manager.SaveUserConnection;
import com.anahoret.actors.messages.db_manager.SaveUserProfile;
import com.anahoret.services.APICallService;

public class APICallActor extends UntypedActor {

    private APICallService apiCallService;

    public APICallActor(APICallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof GetProfilePicture) {
            long result = apiCallService.userProfilePictureAPI(((GetProfilePicture) message).getId());
            forwardToDbManagerRouter(new SaveProfilePicture(result));
        }
        if (message instanceof GetUserConnection) {
            long result = apiCallService.userConnectionAPI(((GetUserConnection) message).getId());
            forwardToDbManagerRouter(new SaveUserConnection(result));
        }
        if (message instanceof GetUserProfile) {
            long result = apiCallService.userProfilesAPI(((GetUserProfile) message).getId());
            forwardToDbManagerRouter(new SaveUserProfile(result));
        }
    }

    private void forwardToDbManagerRouter(Object message) {
        ActorSelection dbManagerRouter = getContext().system().actorSelection("/user/dbManagerRouter");
        dbManagerRouter.tell(message, self());
    }

}
