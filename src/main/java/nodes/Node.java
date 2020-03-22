package nodes;

public class Node {
    String alias;
    String label;

    public Node(String alias, String label) {
        this.alias = alias;
        this.label = label;
    }

    public StringBuilder toGv() {
        return new StringBuilder("\t" + alias + " [label=" + label + "];\n");
    }
}
