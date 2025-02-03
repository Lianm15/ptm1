package test;

import test.TopicManagerSingleton.TopicManager;
import java.util.function.BinaryOperator;

public class IncAgent implements Agent {
    private String[] subs;
    private String[] pubs;

    public IncAgent(String[] subs, String[] pubs) {
        this.subs = subs;
        this.pubs = pubs;
        TopicManagerSingleton.get().getTopic(subs[0]).subscribe(this);
    }

    @Override
    public void callback(String topic, Message msg) {
        if (!Double.isNaN(msg.asDouble)) {
            TopicManagerSingleton.get().getTopic(pubs[0]).publish(new Message(msg.asDouble + 1));
        }
    }

    @Override
    public String getName() {
        return "IncAgent";
    }

    @Override
    public void reset() {
        //nothing to reset
    }

    @Override
    public void close() {
        TopicManagerSingleton.get().getTopic(subs[0]).unsubscribe(this);
    }
}
