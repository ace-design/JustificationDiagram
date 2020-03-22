package nodes;

public class Support extends Node {

    public Support(String alias, String label) {
        super(alias, label);
    }

    @Override
    public StringBuilder toGv() {
        return new StringBuilder("\t" + alias + " [shape=box, label=" + label + "];\n");
    }
}
