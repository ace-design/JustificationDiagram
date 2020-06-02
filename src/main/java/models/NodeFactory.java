package models;

import java.util.ArrayList;

public class NodeFactory {

    public static Node create(String type, String alias, String label,ArrayList<String> realizationResult) {
        switch (type) {
            case "domain":
                return new Domain(alias, label,realizationResult);
            case "rationale":
                return new Rationale(alias, label,realizationResult);
            case "strategy":
                return new Strategy(alias, label,realizationResult);
            case "subconclusion":
                return new SubConclusion(alias, label,realizationResult);
            case "support":
                return new Support(alias, label,realizationResult);
            default:
                return new Node(alias, label,realizationResult);
        }
    } 
}
