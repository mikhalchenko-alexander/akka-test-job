package com.anahoret.actors.messages.api_call;

import akka.actor.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserConnection {

    private long id;
    private ActorRef workProgressTracker;

    public GetUserConnection(ActorRef workProgressTracker) {
        this.workProgressTracker = workProgressTracker;
    }

}
