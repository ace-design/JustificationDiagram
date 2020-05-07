package models;

import export.*;

import java.util.HashSet;
import java.util.Objects;

public class Node implements Visitable {
    public String alias;
    public String label;
    public HashSet<Node> parents;
    public HashSet<Node> children;

    public Node(String alias, String label) {
        this.alias = alias;
        this.label = label;
        this.parents = new HashSet<>();
        this.children = new HashSet<>();
    }

    public void addParent(Node parent) {
        this.parents.add(parent);
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void accept(JDVisitor visitor) {
        visitor.visitNode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(alias, node.alias) &&
                Objects.equals(label, node.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, label);
    }
}
