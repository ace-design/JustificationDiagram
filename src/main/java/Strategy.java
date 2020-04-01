public class Strategy extends Node {

    public Strategy(String alias, String label) {
        super(alias, label);
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitStrategy(this);
    }

}
