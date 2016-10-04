package com.linkedin.scheduler.actors.messages.api_call;

import akka.actor.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserProfile {

    private long id;
    private ActorRef workProgressTracker;

    public GetUserProfile(ActorRef workProgressTracker) {
        this.workProgressTracker = workProgressTracker;
    }

}
