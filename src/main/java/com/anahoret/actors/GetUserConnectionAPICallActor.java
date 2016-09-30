package com.anahoret.actors;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.anahoret.actors.messages.api_call.GetUserConnection;
import com.anahoret.actors.messages.db_manager.SaveUserConnection;
import com.anahoret.services.APICallService;

public class GetUserConnectionAPICallActor extends UntypedActor {

    private APICallService apiCallService;

    public GetUserConnectionAPICallActor(APICallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof GetUserConnection) {
            GetUserConnection getUserConnection = (GetUserConnection) message;
            long result = apiCallService.userConnectionAPI(getUserConnection.getId());
            forwardToDbManagerRouter(new SaveUserConnection(result, getUserConnection.getWorkProgressTracker()));
        }
    }

    private void forwardToDbManagerRouter(Object message) {
        ActorSelection dbManagerRouter = getContext().system().actorSelection("/user/SaveUserConnectionDBManagerActor");
        dbManagerRouter.tell(message, self());
    }

}
