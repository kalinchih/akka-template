package kc_akka.akka_template;

import akka.actor.ActorSystem;
import kc_akka.akka_template.actor.SupervisorActor;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Main main = new Main();
            List<String> messages = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                messages.add(String.valueOf(i));
            }
            main.run(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run(List<String> messages) throws ActorSystemCreationError {
        String systemName = getClass().getPackage().getName();
        ActorSystem actorSystem = ActorSystem.create();
        try {
            actorSystem.actorOf(SupervisorActor.props(messages), SupervisorActor.class.getSimpleName());
        } catch (Exception e) {
            throw new ActorSystemCreationError(String.format("Failed to create actor system, %s.", systemName), e);
        }
    }
}
