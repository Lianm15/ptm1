package test;

import java.util.function.BinaryOperator;

import test.TopicManagerSingleton.TopicManager;

public class BinOpAgent implements Agent {

   private String agentName;
Fן   String topic1Name, topic2Name,outputTopicName;
    private BinaryOperator<Double> operator;
    Double input1,input2;

public BinOpAgent(String agentName,String topic1Name,String topic2Name,String outputTopicName,BinaryOperator<Double> operator){
    this.agentName = agentName;
    this.operator = operator;
    this.topic1Name = topic1Name;
    this.topic2Name = topic2Name;
    this.outputTopicName = outputTopicName;


    TopicManagerSingleton.get().getTopic(topic1Name).subscribe(this);
    TopicManagerSingleton.get().getTopic(topic2Name).subscribe(this);
    TopicManagerSingleton.get().getTopic(outputTopicName).addPublisher(this);


}

    @Override
    public void callback(String topic, Message msg) {
        if (!Double.isNaN(msg.asDouble)) {
            if (topic.equals(topic1Name)) {
                input1 = msg.asDouble;
            } else if (topic.equals(topic2Name)) {
                input2 = msg.asDouble;
            }

            if (input1 != null && input2 != null) {
                Double result = operator.apply(input1, input2);

                if (outputTopicName != null) {
                    TopicManagerSingleton.get().getTopic(outputTopicName).publish(new Message(result));
                }
            }
        } else {

            System.out.println("Invalid Double" + topic);
        }
    }



    @Override
    public String getName() {
        return agentName;
    }



    @Override
    public void close() {
        TopicManagerSingleton.get().getTopic(topic1Name).unsubscribe(this);
        TopicManagerSingleton.get().getTopic(topic2Name).unsubscribe(this);
    }

    public void reset()
    {
        input1 = 0.0;
        input2 = 0.0;

    }

}
