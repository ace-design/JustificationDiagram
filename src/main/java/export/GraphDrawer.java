package export;

import justificationDiagram.JustificationDiagram;
import models.*;

public class GraphDrawer implements JDVisitor {
    private StringBuilder gv = new StringBuilder();

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
        GraphDrawerLayout layout = new GraphDrawerLayout();
        gv.append(layout.draw(diagram));
        gv.append("}\n");
    }

    @Override
    public void visitNode(Node node) {
        gv.append("\t").append(node.alias).append(" [shape=box, label=").append(node.label).append("];\n");
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
    	
        if (conclusion.restriction != null) {
        	String color = setColorUsed(conclusion);

            gv.append("\t").append(conclusion.alias).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"gray75\" " +
                    "COLOR=\"" + color + "\">").append(conclusion.label, 1, conclusion.label.length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\"gray75\" COLOR=\"" + color + "\"></td><td sides=\"L\" " +
                    "COLOR=\"" + color + "\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"gray75\" COLOR=\"" + color + "\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(conclusion.restriction, 1, conclusion.restriction.length() -1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(conclusion.alias).append(" [shape=box, style=\"filled,rounded\", color=\"" + setColorUsed(conclusion) + "\", " +
                    "fillcolor=gray75, label=").append(conclusion.label).append("];\n");
        }
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {

        if (subConclusion.restriction != null) {
        	String color = setColorUsed(subConclusion);
        	gv.append("\t").append(subConclusion.alias).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"gray75\" " +
                    "COLOR=\"" + color + "\">").append(subConclusion.label, 1, subConclusion.label.length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\"gray75\" COLOR=\"" + color + "\"></td><td sides=\"L\" " +
                    "COLOR=\"" + color + "\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"gray75\" COLOR=\"" + color + "\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(subConclusion.restriction, 1, subConclusion.restriction.length() -1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(subConclusion.alias).append(" [shape=box, style=\"filled,rounded\", color=\"" + setColorUsed(subConclusion) + "\", " +
                    "fillcolor=none, label=").append(subConclusion.label).append("];\n");
        }
    }

    @Override
    public void visitStrategy(Strategy strategy) {
        gv.append("\t").append(strategy.alias).append(" [shape=polygon, style=filled, fillcolor=darkolivegreen3, color=\"" + setColorUsed(strategy) + "\" sides=4, skew=.4, label=")
                .append(strategy.label).append("];\n");
    }

    @Override
    public void visitDomain(Domain domain) {
        gv.append("\t").append(domain.alias).append(" [shape=ellipse, style=\"filled,dashed\", fillcolor=darkseagreen1, color=\"" + setColorUsed(domain) + "\" label=").append(domain.label).append("];\n");
    }

    @Override
    public void visitRationale(Rationale rationale) {
        gv.append("\t").append(rationale.alias).append(" [shape=ellipse, style=filled, fillcolor=peachpuff, color=\"" + setColorUsed(rationale) + "\" label=").append(rationale.label).append("];\n");
    }

    @Override
    public void visitSupport(Support support) {

    	/*gv.append("\t").append(support.alias).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"skyblue\" " +
                "COLOR=\"black\">").append(support.label, 1, support.label.length() - 1)
                .append("</td><td sides=\"TR\" BGCOLOR=\"skyblue\" COLOR=\"black\"></td><td sides=\"L\" " +
                        "COLOR=\"black\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"skyblue\" COLOR=\"black\"></td>" +
                        "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"" + setColorUsed(support) + "\" style=\"filled\">")
                .append(support.state.toString(), 0, support.state.toString().length() )
                .append("</td></tr></table>>];\n");*/
    	
        gv.append("\t").append(support.alias).append(" [shape=box, style=\"filled,rounded\", fillcolor=skyblue, color=\"" + setColorUsed(support) + "\" label=").append(support.label).append("];\n");
        
     //   
    }

    @Override
    public void visitRelation(Relation relation) {
        gv.append("\t").append(relation.from.alias).append(" -> ").append(relation.to.alias);

        if (relation.collapsed) {
            gv.append(" [style=dashed]");
        }
        gv.append(";\n");
    }
    
    public String setColorUsed(Node node) {

    	String colorUsed = "red";

    	if(node.state.equals(State.DONE)) {
    		colorUsed = "blue"; 
    	}
    	return colorUsed;
    
    	}
    }
