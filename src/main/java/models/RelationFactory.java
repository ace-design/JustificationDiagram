package models;

public class RelationFactory {

    public static Relation create(String type, Node from, Node to) {
        if ("..>".equals(type)) {
            return new Relation(from, to, true);
        }
        return new Relation(from, to, false);
    }
}
 