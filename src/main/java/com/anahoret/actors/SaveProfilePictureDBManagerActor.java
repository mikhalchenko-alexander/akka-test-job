package com.anahoret.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.anahoret.actors.messages.db_manager.SaveProfilePicture;
import com.anahoret.services.DBManagerService;

public class SaveProfilePictureDBManagerActor extends UntypedActor {

    private DBManagerService dbManagerService;
    private ActorRef workProgressTracker;

    public SaveProfilePictureDBManagerActor(DBManagerService dbManagerService, ActorRef workProgressTracker) {
        this.dbManagerService = dbManagerService;
        this.workProgressTracker = workProgressTracker;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SaveProfilePicture) {
            dbManagerService.saveUserProfilePicture(((SaveProfilePicture) message).getId());
            workProgressTracker.tell(message, self());
        }
    }

}
