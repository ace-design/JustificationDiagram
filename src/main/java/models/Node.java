package models;

import export.*;
import java.util.*;

public class Node implements Visitable {
    public String alias;
    public String label;
    public Set<Relation> inputs;
    public Set<Relation> outputs;

    public Node(String alias, String label) {
        this.alias = alias;
        this.label = label;
        this.inputs = new HashSet<>();
        this.outputs = new HashSet<>();
    }

    public Node(Node node) {
        this.alias = node.alias;
        this.label = node.label;
        this.inputs = node.inputs;
        this.outputs = node.outputs;
    }

    public void addInput(Relation input) {
        this.inputs.add(input);
    }

    public void addOutput(Relation output) {
        this.outputs.add(output);
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
