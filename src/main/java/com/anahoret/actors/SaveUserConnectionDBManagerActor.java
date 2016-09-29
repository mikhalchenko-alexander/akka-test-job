package com.anahoret.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.anahoret.actors.messages.db_manager.SaveUserConnection;
import com.anahoret.services.DBManagerService;

public class SaveUserConnectionDBManagerActor extends UntypedActor {

    private DBManagerService dbManagerService;
    private ActorRef workProgressTracker;

    public SaveUserConnectionDBManagerActor(DBManagerService dbManagerService, ActorRef workProgressTracker) {
        this.dbManagerService = dbManagerService;
        this.workProgressTracker = workProgressTracker;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SaveUserConnection) {
            dbManagerService.saveUserConnection(((SaveUserConnection) message).getId());
            workProgressTracker.tell(message, self());
        }
    }

}
