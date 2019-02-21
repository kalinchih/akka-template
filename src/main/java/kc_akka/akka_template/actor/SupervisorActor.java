package kc_akka.akka_template.actor;

import akka.actor.*;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;
import akka.routing.RoundRobinPool;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SupervisorActor extends AbstractActor {

    private ActorRef dataWriterActor = null;
    private ActorRef eventProducerActor = null;
    private int totalMessageCount = 0;
    private int totalTaskCount = 0;
    private int finishedTaskCount = 0;
    private SupervisorStrategy strategy = new OneForOneStrategy(-1, Duration.Inf(), new Function<Throwable, Directive>() {

        public Directive apply(Throwable exception) {
            return handleActorError(exception);
        }
    });

    public SupervisorActor(List<String> messages) {
        totalMessageCount = messages.size();
        totalTaskCount = totalMessageCount * 2;
        dataWriterActor = getContext().actorOf(DataWriterActor.props(), DataWriterActor.class.getSimpleName());
        eventProducerActor =
                getContext().actorOf(EventProducerActor.props().withRouter(new RoundRobinPool(2)), EventProducerActor.class.getSimpleName());
        System.out.println(String.format("******************************* start loop (%s messages -> %s tasks)", totalMessageCount, totalTaskCount));
        for (String message : messages) {
            dataWriterActor.tell(ActorState.START, getSelf());
            eventProducerActor.tell(ActorState.START, getSelf());
        }
        System.out.println("******************************* end loop");
    }

    public static Props props(List<String> messages) {
        return Props.create(SupervisorActor.class, () -> new SupervisorActor(messages));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals(ActorState.END, handle -> {
            finishedTaskCount++;
            System.out.println(String.format("[%s/%s] %s: %s finish task .", finishedTaskCount, totalTaskCount, getSender().path().toString(),
                    getSender().hashCode()));
            if (finishedTaskCount == totalTaskCount) {
                getContext().getSystem().terminate();
            }
        }).build();
    }

    @Override
    public void postStop() {
        //getContext().getSystem().terminate();
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    private Directive handleActorError(Throwable cause) {
        if (!(cause instanceof ActorBaseError) && cause.getCause() instanceof ActorBaseError) {
            cause = cause.getCause();
        }
        if (cause instanceof ActorBaseError) {
            if (cause instanceof ResumeActorError) {
                return SupervisorStrategy.resume();
            } else if (cause instanceof RestartActorError) {
                FiniteDuration delay = Duration.create(((RestartActorError) cause).getDelayMsToRestart(), TimeUnit.MILLISECONDS);
                getContext().system().scheduler()
                        .scheduleOnce(delay, ((RestartActorError) cause).getActorRef(), ActorState.START, getContext().system().dispatcher(),
                                getSelf());
                return SupervisorStrategy.restart();
            } else {
                return SupervisorStrategy.stop();
            }
        } else {
            return SupervisorStrategy.stop();
        }
    }
}
