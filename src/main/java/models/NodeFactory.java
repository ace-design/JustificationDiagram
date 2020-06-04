package models;

import java.util.ArrayList;

public class NodeFactory {
	/** 
	 * 
	 * @param type (Domain,Rationale,Strategy,SubConclusion,Support or Node)
	 * @param alias of the node in the jd file
	 * @param label of the node used in todo and diagramme files
	 * @param realizationResult String list of information obtain when we read the 'realization' file 
	 * @return
	 */
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
