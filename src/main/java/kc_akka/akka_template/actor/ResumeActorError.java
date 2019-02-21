package kc_akka.akka_template.actor;

public class ResumeActorError extends ActorBaseError {

    public ResumeActorError(String message) {
        super(message);
    }

    public ResumeActorError(Throwable cause) {
        super(cause);
    }
}
