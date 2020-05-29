package models;

import java.util.ArrayList;

public class NodeFactory {

    public static Node create(String type, String alias, String label,ArrayList<String> realizationList) {
        switch (type) {
            case "domain":
                return new Domain(alias, label,realizationList);
            case "rationale":
                return new Rationale(alias, label,realizationList);
            case "strategy":
                return new Strategy(alias, label);
            case "subconclusion":
                return new SubConclusion(alias, label);
            case "support":
                return new Support(alias, label,realizationList);
            default:
                return new Node(alias, label);
        }
    }
}
