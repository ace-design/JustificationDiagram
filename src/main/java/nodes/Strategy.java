package nodes;

public class Strategy extends Node {

    public Strategy(String alias, String label) {
        super(alias, label);
    }

    @Override
    public StringBuilder toGv() {
        return new StringBuilder("\t" + alias + " [shape=polygon, sides=4, skew=.4, label=" + label + "];\n");
    }
}
