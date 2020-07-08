package models;

public class RelationFactory {

    private RelationFactory() {
    	 throw new IllegalStateException("Utility class");
	}

	public static Relation create(String type, Node from, Node to) {
        if ("..>".equals(type)) {
            return new Relation(from, to, true);
        }
        return new Relation(from, to, false);
    }
}
 