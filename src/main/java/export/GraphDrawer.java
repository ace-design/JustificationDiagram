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

        for (String alias : diagram.nodes.keySet()) {
            diagram.nodes.get(alias).accept(this);
        }

        for (Relation relation : diagram.relations) {
            relation.accept(this);
        }

        gv.append("}\n");
    }

    @Override
    public void visitNode(Node node) {
        gv.append("\t").append(node.alias).append(" [shape=box, label=").append(node.label).append("];\n");
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
        if (conclusion.restriction != null) {
            gv.append("\t").append(conclusion.alias).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"gray75\" " +
                    "COLOR=\"royalblue\">").append(conclusion.label, 1, conclusion.label.length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\"gray75\" COLOR=\"royalblue\"></td><td sides=\"L\" " +
                    "COLOR=\"royalblue\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"gray75\" COLOR=\"royalblue\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(conclusion.restriction, 1, conclusion.restriction.length() - 1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(conclusion.alias).append(" [shape=box, style=\"filled,rounded\", color=royalblue, " +
                    "fillcolor=gray75, label=").append(conclusion.label).append("];\n");
        }
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {
        if (subConclusion.restriction != null) {
            gv.append("\t").append(subConclusion.alias).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"none\" " +
                    "COLOR=\"royalblue\">").append(subConclusion.label, 1, subConclusion.label.length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\"none\" COLOR=\"royalblue\"></td><td sides=\"L\" " +
                            "COLOR=\"royalblue\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"none\" COLOR=\"royalblue\"></td>" +
                            "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(subConclusion.restriction, 1, subConclusion.restriction.length() - 1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(subConclusion.alias).append(" [shape=box, style=\"filled,rounded\", color=royalblue, " +
                    "fillcolor=none, label=").append(subConclusion.label).append("];\n");
        }
    }

    @Override
    public void visitStrategy(Strategy strategy) {
        gv.append("\t").append(strategy.alias).append(" [shape=polygon, style=filled, fillcolor=darkolivegreen3, sides=4, skew=.4, label=")
                .append(strategy.label).append("];\n");
    }

    @Override
    public void visitDomain(Domain domain) {
        gv.append("\t").append(domain.alias).append(" [shape=ellipse, style=\"filled,dashed\", fillcolor=darkseagreen1, label=").append(domain.label).append("];\n");
    }

    @Override
    public void visitRationale(Rationale rationale) {
        gv.append("\t").append(rationale.alias).append(" [shape=ellipse, style=filled, fillcolor=peachpuff, label=").append(rationale.label).append("];\n");
    }

    @Override
    public void visitSupport(Support support) {
        gv.append("\t").append(support.alias).append(" [shape=box, style=\"filled,rounded\", fillcolor=skyblue, label=").append(support.label).append("];\n");
    }

    @Override
    public void visitRelation(Relation relation) {
        gv.append("\t").append(relation.from.alias).append(" -> ").append(relation.to.alias).append(";\n");

        if (relation.from instanceof Domain) {
            gv.append("\t{rank = same; ").append(relation.from.alias).append("; ").append(relation.to.alias).append(";}\n");
        } else if (relation.from instanceof Rationale) {
            gv.append("\t").append(relation.to.alias).append(" -> ").append(relation.from.alias).append(" [style=invis];\n");
            gv.append("\t{rank = same; ").append(relation.from.alias).append("; ").append(relation.to.alias).append(";}\n");
        }
    }
}
