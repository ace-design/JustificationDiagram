package models;

import export.*;

import java.util.Objects;

public class Relation implements Visitable {
    private Node from;
    private Node to;
    private boolean collapsed;

    public Relation(Node from, Node to) {
        this.from = from;
        this.to = to;
        this.collapsed = false;
    } 

    public Relation(Node from, Node to, boolean collapsed) {
        this.from = from;
        this.to = to;
        this.collapsed = collapsed;
    }

    public void accept(JDVisitor visitor) {
        visitor.visitRelation(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return Objects.equals(getFrom(), relation.getFrom()) &&
                Objects.equals(getTo(), relation.getTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo());
    }

	public Node getFrom() {
		return from;
	}

	public Node getTo() {
		return to;
	}

	public boolean isCollapsed() {
		return collapsed;
	}
}
