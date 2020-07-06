package models;


public class NodeFactory {
	/** 
	 * 
	 * @param type (Domain,Rationale,Strategy,SubConclusion,Support or Node)
	 * @param alias of the node in the jd file
	 * @param label of the node used in todo and diagramme files
	 * @return
	 */
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
    
    private NodeFactory() {
        throw new IllegalStateException("Utility class");
      }
    
}
