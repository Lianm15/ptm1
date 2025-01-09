package test;

import java.util.ArrayList;
import java.util.List;


public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
        msg = null;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }


    public List<Node> getEdges() {
        return edges;
    }

    public void setEdges(List<Node> edges) {
        this.edges = edges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEdge(Node n) {
        edges.add(n);
    }

    private boolean hasCyclesHelper(Node curr, List<Node> visited)
    {
     if(visited.contains(curr))
     {
         return true;
     }

     visited.add(curr);

     for (Node n : curr.edges) {
         if (hasCyclesHelper(n, visited)) {
             return true;
         }

     }

     return false;

    }

    public boolean hasCycles()
    {
        ArrayList<Node> visited = new ArrayList<>();
        return hasCyclesHelper(this, visited);

    }



}