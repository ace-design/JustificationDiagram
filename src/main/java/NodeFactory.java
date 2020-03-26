import nodes.*;

public class NodeFactory {

    public static Node create(String type, String alias, String label) {
        switch (type) {
            case "strategy":
                return new Strategy(alias, label);
            case "support":
                return new Support(alias, label);
            default:
                return new Node(alias, label);
        }
    }
}
