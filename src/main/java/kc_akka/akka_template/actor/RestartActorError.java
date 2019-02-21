package kc_akka.akka_template.actor;

import akka.actor.ActorRef;

public class RestartActorError extends ActorBaseError {

    private int delayMsToRestart;
    private ActorRef actorRef;

    public RestartActorError(String message, int delayMsToRestart, ActorRef actorRef) {
        super(message);
        this.delayMsToRestart = delayMsToRestart;
        this.actorRef = actorRef;
    }

    public RestartActorError(Throwable cause, int delayMsToRestart, ActorRef actorRef) {
        super(cause);
        this.delayMsToRestart = delayMsToRestart;
        this.actorRef = actorRef;
    }

    public ActorRef getActorRef() {
        return actorRef;
    }

    public int getDelayMsToRestart() {
        return delayMsToRestart;
    }
}
