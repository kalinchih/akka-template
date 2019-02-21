package kc_akka.akka_template.actor;

public class StopActorError extends ActorBaseError {

    public StopActorError(String message) {
        super(message);
    }

    public StopActorError(Throwable cause) {
        super(cause);
    }
}
