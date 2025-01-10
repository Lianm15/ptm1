package test;

import test.TopicManagerSingleton.TopicManager;

public class IncAgent implements Agent {

    private Double value = 0.0;
    private String[] subs;
    private String[] pubs;

    public IncAgent(String[] subs, String[] pubs) {
        this.subs = subs;
        this.pubs = pubs;
        TopicManagerSingleton.get().getTopic(subs[0]).subscribe(this);

    }

    @Override
    public void callback(String topic, Message msg) {
        if (topic.equals(subs[0])) {
            if (!Double.isNaN(msg.asDouble)) {
                value = msg.asDouble;
            }
        }

        if (!Double.isNaN(value)) {
            TopicManagerSingleton.get().getTopic(pubs[0]).publish(new Message(value + 1));
        }

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void reset() {
        value = 0.0;

    }


    @Override
    public void close() {
        TopicManagerSingleton.get().getTopic(subs[0]).unsubscribe(this);

    }
}
