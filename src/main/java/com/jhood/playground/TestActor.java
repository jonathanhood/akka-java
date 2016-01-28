package com.jhood.playground;

import akka.actor.UntypedActor;

public class TestActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Ping) {
            getSender().tell(message,getSelf());
        }
    }
}
