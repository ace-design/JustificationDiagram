public class Rationale extends Node {

    public Rationale(String alias, String label) {
        super(alias, label);
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitRationale(this);
    }
}
