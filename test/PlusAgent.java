package test;

import test.TopicManagerSingleton.TopicManager;

import java.util.function.BinaryOperator;

public class PlusAgent implements Agent {

    //private BinOpAgent agent;
    private Double x = 0.0;
    private Double y = 0.0;
    private String[] subs;
    private String[] pubs;


    public PlusAgent(String[] subs, String[] pubs) {
//agent = new BinOpAgent("PlusAgent",subs[0],subs[1],pubs[0],(BinaryOperator<Double>) ((x, y) -> x + y));
        this.subs = subs;
        this.pubs = pubs;
        TopicManagerSingleton.get().getTopic(subs[0]).subscribe(this);
        TopicManagerSingleton.get().getTopic(subs[1]).subscribe(this);


    }

    @Override
    public void callback(String topic, Message msg) {
        if (!Double.isNaN(msg.asDouble)) {
            if (topic.equals(subs[0])) {
                x = msg.asDouble;
            } else if (topic.equals(subs[1])) {
                y = msg.asDouble;
            }

            if (x != null && y != null) {
                TopicManagerSingleton.get().getTopic(pubs[0]).publish(new Message(x + y));
            }
        }
    }

    @Override
    public String getName() {
        return "PlusAgent";
    }

    @Override
    public void reset() {
        x = null;
        y = null;
    }

    @Override
    public void close() {
        TopicManagerSingleton.get().getTopic(subs[0]).unsubscribe(this);
        TopicManagerSingleton.get().getTopic(subs[1]).unsubscribe(this);
    }
}
