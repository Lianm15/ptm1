package test;

import test.TopicManagerSingleton.TopicManager;

public class PlusAgent implements Agent {

    private Double x = 0.0;
    private Double y = 0.0;
    private String[] subs;
    private String[] pubs;


    public PlusAgent(String[] subs, String[] pubs) {
        this.subs = subs;
        this.pubs = pubs;
        TopicManagerSingleton.get().getTopic(subs[0]).subscribe(this);
        TopicManagerSingleton.get().getTopic(subs[1]).subscribe(this);


    }

    @Override
    public void callback(String topic, Message msg) {

        if (topic.equals(subs[0])) {
            if (!Double.isNaN(msg.asDouble)) {
                x = msg.asDouble;
            }
        }

        if (topic.equals(subs[1])) {
            if (!Double.isNaN(msg.asDouble)) {
                y = msg.asDouble;
            }
        }

        if (!Double.isNaN(x) && !Double.isNaN(y)) {
            TopicManagerSingleton.get().getTopic(pubs[0]).publish(new Message(x + y));
        }

    }


    @Override
    public String getName() {
        return "";
    }

    @Override
    public void reset() {
        x = 0.0;
        y = 0.0;

    }


    @Override
    public void close() {

        TopicManagerSingleton.get().getTopic(subs[0]).unsubscribe(this);
        TopicManagerSingleton.get().getTopic(subs[1]).unsubscribe(this);

    }
}
