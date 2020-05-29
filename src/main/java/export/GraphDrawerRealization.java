package export;

import justificationDiagram.JustificationDiagram;
import models.*;

public class GraphDrawerRealization implements JDVisitor {
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
    	String color = setColorUsed(conclusion);

        if (conclusion.restriction != null) {

            gv.append("\t").append(conclusion.alias).append(" [shape=none margin=0 fontcolor= " + color + " label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"gray75\" " +
                    "COLOR=\"" + color + "\">").append(conclusion.label, 1, conclusion.label.length() - 1) 
                    .append("</td><td sides=\"TR\" BGCOLOR=\"gray75\" COLOR=\"" + color + "\"></td><td sides=\"L\" " +
                    "COLOR=\"" + color + "\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"gray75\" COLOR=\"" + color + "\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(conclusion.restriction, 1, conclusion.restriction.length() -1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(conclusion.alias).append(" [shape=box, style=\"filled,rounded\", fontcolor= " + color + " color=\"" + color + "\", " +
                    "fillcolor=gray75, label=").append(conclusion.label).append("];\n");
        }
    }
 
    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {

    	String color = setColorUsed(subConclusion);

        if (subConclusion.restriction != null) {
        	gv.append("\t").append(subConclusion.alias).append(" [shape=none margin=0  fontcolor= " + color + " label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"gray75\" " +
                    "COLOR=\"" + color + "\">").append(subConclusion.label, 1, subConclusion.label.length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\"gray75\" COLOR=\"" + color + "\"></td><td sides=\"L\" " +
                    "COLOR=\"" + color + "\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"gray75\" COLOR=\"" + color + "\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(subConclusion.restriction, 1, subConclusion.restriction.length() -1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(subConclusion.alias).append(" [shape=box, style=\"filled,rounded\", fontcolor= " + color + " color=\"" + color + "\", " +
                    "fillcolor=none, label=").append(subConclusion.label).append("];\n");
        }
    }

    @Override
    public void visitStrategy(Strategy strategy) {
    	String color = setColorUsed(strategy);

        gv.append("\t").append(strategy.alias).append(" [shape=polygon, style=filled, fontcolor= " + color + " fillcolor=darkolivegreen3, color=\"" + setColorUsed(strategy) + "\" sides=4, skew=.4, label=")
                .append(strategy.label).append("];\n");
    }

    @Override
    public void visitDomain(Domain domain) {
    	String color = setColorUsed(domain);
        gv.append("\t").append(domain.alias).append(" [shape=ellipse, style=\"filled,dashed\", fontcolor= " + color + " fillcolor=darkseagreen1, color=\"" + color + "\" label=").append(domain.label).append("];\n");
    }

    @Override
    public void visitRationale(Rationale rationale) {
    	String color = setColorUsed(rationale);
        gv.append("\t").append(rationale.alias).append(" [shape=ellipse, style=filled,fontcolor= " + color + ", fillcolor=peachpuff, color=\"" + color + "\" label=").append(rationale.label).append("];\n");
    }

    @Override
    public void visitSupport(Support support) {
    	String color = setColorUsed(support);

        gv.append("\t").append(support.alias).append(" [shape=box, style=\"filled,rounded\", fontcolor= " + color + ", fillcolor=skyblue, color=\"" + color + "\" label=").append(support.label).append("];\n");
    }
    /*
    @Override
    public void visitConclusion(Conclusion conclusion) {
    	String color = setColorUsed(conclusion);

        if (conclusion.restriction != null) {
            gv.append("\t").append(conclusion.alias).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"" + color +"\" " +
                    "COLOR=\"royalblue\">").append(conclusion.label, 1, conclusion.label.length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\""+ color + "\" COLOR=\"royalblue\"></td><td sides=\"L\" " +
                    "COLOR=\"royalblue\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"" + color + "\" COLOR=\"royalblue\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(conclusion.restriction, 1, conclusion.restriction.length() - 1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(conclusion.alias).append(" [shape=box, style=\"filled,rounded\", color=royalblue, " +
                    "fillcolor="+ color + ", label=").append(conclusion.label).append("];\n");
        }
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {
    	String color = setColorUsed(subConclusion);
        if (subConclusion.restriction != null) {
            gv.append("\t").append(subConclusion.alias).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\""+ color + "\" " +
                    "COLOR=\"royalblue\">").append(subConclusion.label, 1, subConclusion.label.length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\""+ color + "\" COLOR=\"royalblue\"></td><td sides=\"L\" " +
                            "COLOR=\"royalblue\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\""+ color +"\" COLOR=\"royalblue\"></td>" +
                            "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(subConclusion.restriction, 1, subConclusion.restriction.length() - 1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(subConclusion.alias).append(" [shape=box, style=\"filled,rounded\", color=royalblue, " +
                    "fillcolor=" + color + ", label=").append(subConclusion.label).append("];\n");
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
*/
    @Override
    public void visitRelation(Relation relation) {
        gv.append("\t").append(relation.from.alias).append(" -> ").append(relation.to.alias);

        if (relation.collapsed) {
            gv.append(" [style=dashed]");
        }
        gv.append(";\n");
    }
    
    public String setColorUsed(Node node) {
 
    	String colorUsed = "red3";

    	if(node.state.equals(State.DONE)) {
    		colorUsed = "blue3"; 
    	}
    	return colorUsed;
    
    	}
    }
