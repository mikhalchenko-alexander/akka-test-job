package com.linkedin.scheduler.actors.messages.db_manager;

import akka.actor.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveUserConnection implements Comparable<SaveUserConnection> {

    private long id;
    private ActorRef workProgressTracker;

    public SaveUserConnection(ActorRef workProgressTracker) {
        this.workProgressTracker = workProgressTracker;
    }

    @Override
    public int compareTo(SaveUserConnection other) {
        return this.id > other.id ?  1 :
               this.id < other.id ? -1 : 0;
    }
}
