package com.linkedin.scheduler.actors;

import akka.actor.UntypedActor;
import com.linkedin.scheduler.actors.messages.db_manager.SaveUserProfile;
import com.linkedin.scheduler.services.DBManagerService;

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
