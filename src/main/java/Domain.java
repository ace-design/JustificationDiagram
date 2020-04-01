public class Domain extends Node {

    public Domain(String alias, String label) {
        super(alias, label);
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitDomain(this);
    }
}
