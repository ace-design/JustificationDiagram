package models;

import export.*;
import java.util.Objects;

public class Relation implements Visitable {
    public Node from;
    public Node to;

    public Relation(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    public void accept(GraphDrawer visitor) {
        visitor.visitRelation(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return Objects.equals(from, relation.from) &&
                Objects.equals(to, relation.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
