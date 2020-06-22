package export;

import justificationDiagram.JustificationDiagram;
import models.*;

public class GraphDrawerRealization implements JDVisitor {
    private StringBuilder gv = new StringBuilder();
    
    // color to use according to the state of the node (todo or done)
    private String[] colorUsedModel = {"red3","blue3"}; 
    private String colorUsed;

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
        gv.append("\t").append(node.getAlias()).append(" [shape=box, label=").append(node.getLabel()).append("];\n");
    }
    
    @Override
    public void visitConclusion(Conclusion conclusion) {
    	setColorUsed(conclusion);

        if (conclusion.restriction != null) {

            gv.append("\t").append(conclusion.getAlias()).append(" [shape=none margin=0 fontcolor= " + colorUsed + " label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"gray75\" " +
                    "COLOR=\"" + colorUsed + "\">").append(conclusion.getLabel(), 1, conclusion.getLabel().length() - 1) 
                    .append("</td><td sides=\"TR\" BGCOLOR=\"gray75\" COLOR=\"" + colorUsed + "\"></td><td sides=\"L\" " +
                    "COLOR=\"" + colorUsed + "\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"gray75\" COLOR=\"" + colorUsed + "\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(conclusion.restriction, 1, conclusion.restriction.length() -1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(conclusion.getAlias()).append(" [shape=box, style=\"filled,rounded\", fontcolor= " + colorUsed + " color=\"" + colorUsed + "\", " +
                    "fillcolor=gray75, label=").append(conclusion.getLabel()).append("];\n");
        }
    }
 
    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {

    	setColorUsed(subConclusion); 

        if (subConclusion.restriction != null) {
        	gv.append("\t").append(subConclusion.getAlias()).append(" [shape=none margin=0  fontcolor= " + colorUsed + " label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"gray75\" " +
                    "COLOR=\"" + colorUsed + "\">").append(subConclusion.getLabel(), 1, subConclusion.getLabel().length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\"gray75\" COLOR=\"" + colorUsed + "\"></td><td sides=\"L\" " +
                    "COLOR=\"" + colorUsed + "\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"gray75\" COLOR=\"" + colorUsed + "\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"khaki1\" style=\"dashed\">")
                    .append(subConclusion.restriction, 1, subConclusion.restriction.length() -1)
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(subConclusion.getAlias()).append(" [shape=box, style=\"filled,rounded\", fontcolor= " + colorUsed + " color=\"" + colorUsed + "\", " +
                    "fillcolor=none, label=").append(subConclusion.getLabel()).append("];\n");
        }
    }

    @Override
    public void visitStrategy(Strategy strategy) {
    	setColorUsed(strategy);

        gv.append("\t").append(strategy.getAlias()).append(" [shape=polygon, style=filled, fontcolor= " + colorUsed + " fillcolor=darkolivegreen3, color=\"" + colorUsed + "\" sides=4, skew=.4, label=")
                .append(strategy.getLabel()).append("];\n");
    }

    @Override
    public void visitDomain(Domain domain) {
    	setColorUsed(domain);
        gv.append("\t").append(domain.getAlias()).append(" [shape=ellipse, style=\"filled,dashed\", fontcolor= " + colorUsed + " fillcolor=darkseagreen1, color=\"" + colorUsed + "\" label=").append(domain.getLabel()).append("];\n");
    }

    @Override
    public void visitRationale(Rationale rationale) {
    	setColorUsed(rationale);
        gv.append("\t").append(rationale.getAlias()).append(" [shape=ellipse, style=filled,fontcolor= " + colorUsed + ", fillcolor=peachpuff, color=\"" + colorUsed + "\" label=").append(rationale.getLabel()).append("];\n");
    }

    @Override
    public void visitSupport(Support support) {
    	setColorUsed(support);

        gv.append("\t").append(support.getAlias()).append(" [shape=box, style=\"filled,rounded\", fontcolor= " + colorUsed + ", fillcolor=skyblue, color=\"" + colorUsed + "\" label=").append(support.getLabel()).append("];\n");
    }

    @Override
    public void visitRelation(Relation relation) {
        gv.append("\t").append(relation.from.getAlias()).append(" -> ").append(relation.to.getAlias());

        if (relation.collapsed) {
            gv.append(" [style=dashed]");
        }
        gv.append(";\n");
    }
    
    /**
     * Analyse the state of the current node and set the corresponding color
     * 
     * @param node to anlyses
     */
    public void setColorUsed(Node node) {
 
    	colorUsed = colorUsedModel[0];

    	if(node.getState().equals(State.DONE)) {
    		colorUsed = colorUsedModel[1];
    	}
    }
    
    
    }
