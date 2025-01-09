package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import test.TopicManagerSingleton.TopicManager;

public class Graph extends ArrayList<Node>{

    private final HashMap<String, Node> nodeMap;

    public Graph(){
        super();
        nodeMap = new HashMap<>();
    }
    
    public boolean hasCycles() {
        for (Node n : this) {
            if(n.hasCycles()) {
                return true;
            }
        }
        return false;
    }
    public void createFromTopics(){

        Collection<Topic> topics = TopicManagerSingleton.get().getTopics();

        for (Topic topic : topics) {
            Node topicNode = new Node(("T" + topic.getName()));
            this.add(topicNode);
            nodeMap.put("T" + topic.getName(), topicNode);


            for (Agent agent : topic.getSubs()) {
                Node agentNode;
                if(!nodeMap.containsKey("A" + agent.getName())) {
                    agentNode = new Node("A" + agent.getName());
                    this.add(agentNode);
                    nodeMap.put("A" + agent.getName(), agentNode);
                }
                else {
                    agentNode = nodeMap.get("A" + agent.getName());
                }
                topicNode.addEdge(agentNode);
            }

            for (Agent publisher : topic.getPubs()) {
                Node publisherNode = nodeMap.get("A" + publisher.getName());
                publisherNode.addEdge(topicNode);
            }

        }


    }    

    
}
