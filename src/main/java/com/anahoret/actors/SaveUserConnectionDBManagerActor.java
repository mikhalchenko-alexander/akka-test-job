package com.anahoret.actors;

import akka.actor.UntypedActor;
import com.anahoret.actors.messages.db_manager.SaveUserConnection;
import com.anahoret.services.DBManagerService;

public class SaveUserConnectionDBManagerActor extends UntypedActor {

    private DBManagerService dbManagerService;

    public SaveUserConnectionDBManagerActor(DBManagerService dbManagerService) {
        this.dbManagerService = dbManagerService;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SaveUserConnection) {
            SaveUserConnection saveUserConnection = (SaveUserConnection) message;
            dbManagerService.saveUserConnection(saveUserConnection.getId());
            saveUserConnection.getWorkProgressTracker().tell(message, self());
        }
    }

}
