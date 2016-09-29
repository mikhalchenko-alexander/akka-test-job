package com.anahoret.actors;

import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import com.anahoret.actors.messages.db_manager.SaveProfilePicture;
import com.anahoret.actors.messages.db_manager.SaveUserConnection;
import com.anahoret.actors.messages.db_manager.SaveUserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class WorkProgressTrackerActor extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(WorkProgressTrackerActor.class);

    private final int userProfileCount;
    private final int userProfilePictureCount;
    private final int userConnectionCount;

    private Set<SaveUserProfile> saveUserProfileDone;
    private Set<SaveProfilePicture> saveProfilePictureDone;
    private Set<SaveUserConnection> saveUserConnectionDone;

    public WorkProgressTrackerActor(int userProfileCount, int userProfilePictureCount, int userConnectionCount) {
        this.userProfileCount = userProfileCount;
        this.userProfilePictureCount = userProfilePictureCount;
        this.userConnectionCount = userConnectionCount;

        saveUserProfileDone = new HashSet<>(userProfileCount);
        saveProfilePictureDone = new HashSet<>(userProfilePictureCount);
        saveUserConnectionDone = new HashSet<>(userConnectionCount);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof SaveProfilePicture) {
            saveProfilePictureDone.add((SaveProfilePicture) message);
        }
        if (message instanceof SaveUserConnection) {
            saveUserConnectionDone.add((SaveUserConnection) message);
        }
        if (message instanceof SaveUserProfile) {
            saveUserProfileDone.add((SaveUserProfile) message);
        }
        if (isDone()) {
            LOG.info("Jobs finished");
            self().tell(PoisonPill.getInstance(), self());
        }
    }

    private boolean isDone() {
        return saveUserProfileDone.size() == userProfileCount &&
                saveProfilePictureDone.size() == userProfilePictureCount &&
                saveUserConnectionDone.size() == userConnectionCount;
    }

}
