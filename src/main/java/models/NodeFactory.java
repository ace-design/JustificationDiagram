package models;

public class NodeFactory {

    public static Node create(String type, String alias, String label) {
        switch (type) {
            case "domain":
                return new Domain(alias, label);
            case "rationale":
                return new Rationale(alias, label);
            case "strategy":
                return new Strategy(alias, label);
            case "subconclusion":
                return new SubConclusion(alias, label);
            case "support":
                return new Support(alias, label);
            default:
                return new Node(alias, label);
        }
    }
}
