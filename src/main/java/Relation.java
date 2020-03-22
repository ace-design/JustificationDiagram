import nodes.Node;

public class Relation {
    String from;
    String to;

    public Relation(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public StringBuilder toGv() {
        return new StringBuilder("\t" + from + " -> " + to + ";\n");
    }
}
