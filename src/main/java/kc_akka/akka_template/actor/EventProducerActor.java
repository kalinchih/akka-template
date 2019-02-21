package kc_akka.akka_template.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

public class EventProducerActor extends AbstractActor {

    final private FiniteDuration DURATION_ZERO = Duration.create(0, TimeUnit.MILLISECONDS);

    public EventProducerActor() {
    }

    public static Props props() {
        return Props.create(EventProducerActor.class, () -> new EventProducerActor());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals(ActorState.START, handle -> {
            process();
        }).build();
    }

    private void process() throws StopActorError {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sender().tell(ActorState.END, getSelf());
    }

    @Override
    public void postStop() {
    }
}
