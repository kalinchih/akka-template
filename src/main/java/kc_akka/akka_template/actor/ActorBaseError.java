package kc_akka.akka_template.actor;

public class ActorBaseError extends Exception {

    static final long serialVersionUID = 1L;

    public ActorBaseError(String message) {
        super(message);
    }

    public ActorBaseError(Throwable cause) {
        super(cause);
    }
}
