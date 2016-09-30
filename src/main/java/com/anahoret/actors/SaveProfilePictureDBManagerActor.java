package com.anahoret.actors;

import akka.actor.UntypedActor;
import com.anahoret.actors.messages.db_manager.SaveProfilePicture;
import com.anahoret.services.DBManagerService;

public class SaveProfilePictureDBManagerActor extends UntypedActor {

    private DBManagerService dbManagerService;

    public SaveProfilePictureDBManagerActor(DBManagerService dbManagerService) {
        this.dbManagerService = dbManagerService;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SaveProfilePicture) {
            SaveProfilePicture saveProfilePicture = (SaveProfilePicture) message;
            dbManagerService.saveUserProfilePicture(saveProfilePicture.getId());
            saveProfilePicture.getWorkProgressTracker().tell(message, self());
        }
    }

}
