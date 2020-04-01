public class SubConclusion extends Node {

    public SubConclusion(String alias, String label) {
        super(alias, label);
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitSubConclusion(this);
    }
}
