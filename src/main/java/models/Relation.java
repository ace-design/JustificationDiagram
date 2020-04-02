package models;

import export.*;

public class Relation implements Visitable {
    public String from;
    public String to;

    public Relation(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitRelation(this);
    }

}
