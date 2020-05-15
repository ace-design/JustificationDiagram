package export;

import justificationDiagram.JustificationDiagram;
import models.Node;
import models.Relation;
import java.util.*;

public class TopologicalSort {
    private Map<String, Node> toBeVisited;
    private Queue<OrderedNode> finishingTime;
    private int time;
    LinkedList<Node> order;

    private static class OrderedNode implements Comparable<OrderedNode> {
        int weight;
        Node node;

        public OrderedNode(int weight, Node node) {
            this.weight = weight;
            this.node = node;
        }

        public int compareTo(OrderedNode n) {
            if (n != null) {
                return this.weight - n.weight;
            } else {
                return -1;
            }
        }
    }

    // Algorithm provided by Thomas H. Cormen et alt. in Introduction to algorithms 3rd Edition (2009), pp.603-615
    public TopologicalSort(JustificationDiagram diagraph) {
        order = new LinkedList<>();
        depthFirstSearch(diagraph);

        while (!finishingTime.isEmpty()) {
            order.addFirst(finishingTime.poll().node);
        }
    }

    public void depthFirstSearch(JustificationDiagram diagraph) {
        toBeVisited = new HashMap<>();
        finishingTime = new PriorityQueue<>();

        for (String u: diagraph.nodes.keySet()) {
            toBeVisited.put(u, diagraph.nodes.get(u));
        }
        time = 0;
        for (String u: diagraph.nodes.keySet()) {
            if (toBeVisited.containsKey(u)) {
                depthFirstSearchVisit(diagraph.nodes.get(u));
            }
        }
    }

    private void depthFirstSearchVisit(Node u) {
        ++time;
        toBeVisited.remove(u.alias);

        for (Relation relation: u.outputs) {
            Node v = relation.to;

            if (toBeVisited.containsValue(v)) {
                depthFirstSearchVisit(v);
            }
        }
        time = time + 1;
        finishingTime.add(new OrderedNode(time, u));
    }
}
