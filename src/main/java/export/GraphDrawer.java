package export;

import justificationDiagram.JustificationDiagram;
import models.*;

public class GraphDrawer implements JDVisitor {
    private StringBuilder gv = new StringBuilder();

    public StringBuilder draw(JustificationDiagram diagram) {
        this.visitDiagram(diagram);
        return gv;
    	/*
    	TopologicalSort sort = new TopologicalSort(diagram);
        
        for (Node node : sort.order) {
            node.accept(this);
        }
        
        this.visitDiagram(diagram);
        return gv;
        */
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
    	node.analyseRelation(node);
    	System.out.println(node.state);
    	System.out.println(node.label);

        gv.append("\t").append(node.alias).append(" [shape=box, label=").append(node.label).append("];\n");
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
    	conclusion.analyseRelation(conclusion);
    	System.out.println(conclusion.state);
    	System.out.println(conclusion.label);
    	//System.out.println();

        if (conclusion.restriction != null) {
        	String string = conclusion.restriction.replaceAll("\"", "") + " - " + conclusion.state.toString();
            gv.append("\t").append(conclusion.alias).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                    "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"gray75\" " +
                    "COLOR=\"royalblue\">").append(conclusion.label, 1, conclusion.label.length() - 1)
                    .append("</td><td sides=\"TR\" BGCOLOR=\"gray75\" COLOR=\"royalblue\"></td><td sides=\"L\" " +
                    "COLOR=\"royalblue\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"gray75\" COLOR=\"royalblue\"></td>" +
                    "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"" + setColorUsed(conclusion) + "\" style=\"dashed\">")
                    .append(string, 0, string.length())
                    .append("</td></tr></table>>];\n");
        } else {
            gv.append("\t").append(conclusion.alias).append(" [shape=box, style=\"filled,rounded\", color=royalblue, " +
                    "fillcolor=gray75, label=").append(conclusion.label).append("];\n");
        }
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {
    	subConclusion.analyseRelation(subConclusion);
    	System.out.println(subConclusion.state);
    	System.out.println(subConclusion.label);
    	//System.out.println();

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
    	strategy.analyseRelation(strategy);
    	System.out.println(strategy.state);
    	System.out.println(strategy.label);
    	/*for (Relation relation : strategy.inputs) {
			System.out.println("inputs");
			System.out.println(relation.from.label);
			System.out.println(relation.from.state.toString());
			System.out.println(relation.to.label);
		}
    	System.out.println();
    	
    	*/


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
    	support.analyseRelation(support);

    	gv.append("\t").append(support.alias).append(" [shape=none margin=0 label=<<table cellspacing=\"0\" " +
                "cellborder=\"1\" border=\"0\"><tr><td COLSPAN=\"2\" sides=\"LT\" BGCOLOR=\"skyblue\" " +
                "COLOR=\"black\">").append(support.label, 1, support.label.length() - 1)
                .append("</td><td sides=\"TR\" BGCOLOR=\"skyblue\" COLOR=\"black\"></td><td sides=\"L\" " +
                        "COLOR=\"black\"></td></tr><tr><td sides=\"LB\" BGCOLOR=\"skyblue\" COLOR=\"black\"></td>" +
                        "<td sides=\"LTRB\" ROWSPAN=\"2\" colspan=\"3\" port=\"a\" BGCOLOR=\"" + setColorUsed(support) + "\" style=\"filled\">")
                .append(support.state.toString(), 0, support.state.toString().length() )
                .append("</td></tr></table>>];\n");
    	
        //gv.append("\t").append(support.alias).append(" [shape=box, style=\"filled,rounded\", fillcolor=skyblue, label=").append(support.label).append("];\n");
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
    	String colorUsed = "khaki1";
    	if(node.state.equals(State.DONE)) {
    		colorUsed = "darkolivegreen1"; 
    	}
    return colorUsed;
    
    }
    }
