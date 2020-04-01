public interface JDVisitor {

    void visitDiagram(JustificationDiagram diagram);
    void visitNode(Node node);
    void visitConclusion(Conclusion conclusion);
    void visitSubConclusion(SubConclusion subConclusion);
    void visitStrategy(Strategy strategy);
    void visitDomain(Domain domain);
    void visitRationale(Rationale rationale);
    void visitSupport(Support support);
    void visitRelation(Relation relation);
}
