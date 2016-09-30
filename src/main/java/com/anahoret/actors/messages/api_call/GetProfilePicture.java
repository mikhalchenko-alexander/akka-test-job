package com.anahoret.actors.messages.api_call;

import akka.actor.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProfilePicture {

    private long id;
    private ActorRef workProgressTracker;

    public GetProfilePicture(ActorRef workProgressTracker) {
        this.workProgressTracker = workProgressTracker;
    }

}
