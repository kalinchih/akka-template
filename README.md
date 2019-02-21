AKKA Template
===

This project demo how an actor system concurrently invokes upstream Actors



Class Role
---

- App
    - create the ActorSystem with SupervisorActor
- SupervisorActor
    - Initial 2 upstream actors:
        - DataWriterActor (1 instance)
        - EventProducerActor (2 instances)
    - Handle actor exception
        - Resume
        - Restart
        - Stop


Non-blocking IO
---

1. App passes 10 messages to SupervisorActor
2. SupervisorActor loops the messages
    - SupervisorActor passes every single message to upstream actors (DataWriterActor, EventProducerActor)
    - The loop is not blocked by upstream actors' response



Project Build Type
---
Maven


Demo Result
---
### Main Class
kc_akka.akka_template.Main
(target>java -cp akka-template-1.0-SNAPSHOT-jar-with-dependencies.jar kc_akka.akka_template.Main)


### Check the Console Output
- The loop is not blocked by the upstream tasks.
- After all tasks finished, terminate the actor system.

```concept
******************************* start loop (10 messages -> 20 tasks)
******************************* end loop
[1/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .
[2/20] akka://default/user/SupervisorActor/EventProducerActor/$a: -1794545720 finish task .
[3/20] akka://default/user/SupervisorActor/EventProducerActor/$b: 1603719644 finish task .
[4/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .
[5/20] akka://default/user/SupervisorActor/EventProducerActor/$a: -1794545720 finish task .
[6/20] akka://default/user/SupervisorActor/EventProducerActor/$b: 1603719644 finish task .
[7/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .
[8/20] akka://default/user/SupervisorActor/EventProducerActor/$b: 1603719644 finish task .
[9/20] akka://default/user/SupervisorActor/EventProducerActor/$a: -1794545720 finish task .
[10/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .
[11/20] akka://default/user/SupervisorActor/EventProducerActor/$b: 1603719644 finish task .
[12/20] akka://default/user/SupervisorActor/EventProducerActor/$a: -1794545720 finish task .
[13/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .
[14/20] akka://default/user/SupervisorActor/EventProducerActor/$a: -1794545720 finish task .
[15/20] akka://default/user/SupervisorActor/EventProducerActor/$b: 1603719644 finish task .
[16/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .
[17/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .
[18/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .
[19/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .
[20/20] akka://default/user/SupervisorActor/DataWriterActor: 1278340179 finish task .

```

