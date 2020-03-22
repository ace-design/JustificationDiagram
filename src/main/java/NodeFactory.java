import nodes.*;

public class NodeFactory {

    public static Node create(String type, String alias, String label, String restriction) {
        switch (type) {
            case "conclusion":
                if (restriction != null) {
                    return new Conclusion(alias, label, restriction);
                } else {
                    return new Conclusion(alias, label);
                }
            case "strategy":
                return new Strategy(alias, label);
            case "support":
                return new Support(alias, label);
            default:
                return new Node(alias, label);
        }
    }
}
