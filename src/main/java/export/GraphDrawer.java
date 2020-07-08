package export;

import justificationDiagram.JustificationDiagram;
import models.*;

public class GraphDrawer implements JDVisitor {
    private final StringBuilder gv = new StringBuilder();

    public StringBuilder draw(JustificationDiagram diagram) {
        this.visitDiagram(diagram);
        return gv;
    }

    @Override
    public void visitDiagram(JustificationDiagram diagram) {
        gv.append("digraph G {\n\trankdir = \"BT\"\n");

        for (String alias : diagram.getNodes().keySet()) {
            diagram.getNodes().get(alias).accept(this);
        }
        for (Relation relation : diagram.getRelations()) {
            relation.accept(this);
        }
        GraphDrawerLayout layout = new GraphDrawerLayout(); 
        gv.append(layout.draw(diagram));
        gv.append("}\n");
    }

    @Override
    public void visitNode(Node node) {
        gv.append("\t").append(node.getAlias()).append(" [shape=box, label=").append(node.getAlias()).append("];\n");
    }
 
    @Override
    public void visitConclusion(Conclusion conclusion) {
        if (conclusion.getRestriction() != null) {
            gv.append("\t").append(conclusion.getAlias()).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"gray75\" " +
                    "COLOR=\"royalblue\">").append(conclusion.getLabel(), 1, conclusion.getLabel().length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\"gray75\" COLOR=\"royalblue\"></td><td sides=\"L\" " +
                    "COLOR=\"royalblue\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"gray75\" COLOR=\"royalblue\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(conclusion.getRestriction(), 1, conclusion.getRestriction().length() - 1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(conclusion.getAlias()).append(" [shape=box, style=\"filled,rounded\", color=royalblue, " +
                    "fillcolor=gray75, label=").append(conclusion.getLabel()).append("];\n");
        }
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {
        if (subConclusion.getRestriction() != null) {
            gv.append("\t").append(subConclusion.getAlias()).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"none\" " +
                    "COLOR=\"royalblue\">").append(subConclusion.getLabel(), 1, subConclusion.getLabel().length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\"none\" COLOR=\"royalblue\"></td><td sides=\"L\" " +
                            "COLOR=\"royalblue\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"none\" COLOR=\"royalblue\"></td>" +
                            "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(subConclusion.getRestriction(), 1, subConclusion.getRestriction().length() - 1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(subConclusion.getAlias()).append(" [shape=box, style=\"filled,rounded\", color=royalblue, " +
                    "fillcolor=none, label=").append(subConclusion.getLabel()).append("];\n");
        }
    }

    @Override
    public void visitStrategy(Strategy strategy) {
        gv.append("\t").append(strategy.getAlias()).append(" [shape=polygon, style=filled, fillcolor=darkolivegreen3, sides=4, skew=.4, label=")
                .append(strategy.getLabel()).append("];\n");
    }

    @Override
    public void visitDomain(Domain domain) {
        gv.append("\t").append(domain.getAlias()).append(" [shape=ellipse, style=\"filled,dashed\", fillcolor=darkseagreen1, label=").append(domain.getLabel()).append("];\n");
    }

    @Override
    public void visitRationale(Rationale rationale) {
        gv.append("\t").append(rationale.getAlias()).append(" [shape=ellipse, style=filled, fillcolor=peachpuff, label=").append(rationale.getLabel()).append("];\n");
    }

    @Override
    public void visitSupport(Support support) {
        gv.append("\t").append(support.getAlias()).append(" [shape=box, style=\"filled,rounded\", color=black fillcolor=skyblue, label=").append(support.getLabel()).append("];\n");
    }

    @Override
    public void visitRelation(Relation relation) {
        gv.append("\t").append(relation.getFrom().getAlias()).append(" -> ").append(relation.getTo().getAlias());

        if (relation.isCollapsed()) {
            gv.append(" [style=dashed]");
        }
        gv.append(";\n");
    }
}
