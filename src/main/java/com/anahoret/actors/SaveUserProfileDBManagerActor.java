package com.anahoret.actors;

import akka.actor.UntypedActor;
import com.anahoret.actors.messages.db_manager.SaveUserProfile;
import com.anahoret.services.DBManagerService;

public class SaveUserProfileDBManagerActor extends UntypedActor {

    private DBManagerService dbManagerService;

    public SaveUserProfileDBManagerActor(DBManagerService dbManagerService) {
        this.dbManagerService = dbManagerService;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SaveUserProfile) {
            SaveUserProfile saveUserProfile = (SaveUserProfile) message;
            dbManagerService.saveUserProfile(saveUserProfile.getId());
            saveUserProfile.getWorkProgressTracker().tell(message, self());
        }
    }

}
