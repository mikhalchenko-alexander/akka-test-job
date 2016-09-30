package com.anahoret.actors.messages.db_manager;

import akka.actor.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveUserProfile implements Comparable<SaveUserProfile> {

    private long id;
    private ActorRef workProgressTracker;

    public SaveUserProfile(ActorRef workProgressTracker) {
        this.workProgressTracker = workProgressTracker;
    }

    @Override
    public int compareTo(SaveUserProfile other) {
        return this.id > other.id ?  1 :
               this.id < other.id ? -1 : 0;
    }
}
