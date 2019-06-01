package kc_akka.akka_template;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import kc_akka.akka_template.actor.SupervisorActor;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Main main = new Main();
            List<String> messages = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                messages.add(String.valueOf(i));
            }
            main.run(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run(List<String> messages) throws ActorSystemCreationError {
        String systemName = getClass().getPackage().getName();
        ActorSystem actorSystem = ActorSystem.create("dev-" + this.getClass().getSimpleName(), ConfigFactory.load().getConfig("kalin"));
        try {
            actorSystem.actorOf(SupervisorActor.props(messages), SupervisorActor.class.getSimpleName());
            ObjectMapper objectMapper = new ObjectMapper();
            Config config = actorSystem.dispatchers().defaultDispatcherConfig();
            System.out.println(Runtime.getRuntime().availableProcessors());
            System.out.println(config.getString("fork-join-executor.parallelism-min"));
            System.out.println(config.getString("fork-join-executor.parallelism-max"));
            System.out.println(config.getString("fork-join-executor.parallelism-factor"));
            System.out.println(config.getString("throughput"));
        } catch (Exception e) {
            throw new ActorSystemCreationError(String.format("Failed to create actor system, %s.", systemName), e);
        }
    }
}

