package nodes;

public class Conclusion extends Node {
    String restriction;

    public Conclusion(String alias, String label, String restriction) {
        super(alias, label);
        this.restriction = restriction;
    }

    @Override
    public StringBuilder toGv() {
        return new StringBuilder("\t" + alias + " [shape=box, label=" + label + "];\n");
    }
}
