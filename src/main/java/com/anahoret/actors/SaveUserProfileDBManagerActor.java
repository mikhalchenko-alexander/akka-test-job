package com.anahoret.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.anahoret.actors.messages.db_manager.SaveUserProfile;
import com.anahoret.services.DBManagerService;

public class SaveUserProfileDBManagerActor extends UntypedActor {

    private DBManagerService dbManagerService;
    private ActorRef workProgressTracker;

    public SaveUserProfileDBManagerActor(DBManagerService dbManagerService, ActorRef workProgressTracker) {
        this.dbManagerService = dbManagerService;
        this.workProgressTracker = workProgressTracker;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SaveUserProfile) {
            dbManagerService.saveUserProfile(((SaveUserProfile) message).getId());
            workProgressTracker.tell(message, self());
        }
    }

}
