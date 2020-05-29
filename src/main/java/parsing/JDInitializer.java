package parsing;

import java.util.ArrayList;

import justificationDiagram.JustificationDiagram;
import models.*;

public class JDInitializer extends JustificationDiagramBaseVisitor<String> {
    public JustificationDiagram diagram;
    public ArrayList<String> realizationList;
    
    
    public void setRealizationList(ArrayList<String> realizationList) {
    	this.realizationList = realizationList;
    }

    @Override
    public String visitDiagram(JustificationDiagramParser.DiagramContext ctx) {
        diagram = new JustificationDiagram();
        return super.visitDiagram(ctx);
    } 

    @Override
    public String visitDeclaration(JustificationDiagramParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    @Override
    public String visitRelation(JustificationDiagramParser.RelationContext ctx) {
        return super.visitRelation(ctx);
    }

    @Override
    public String visitInstruction(JustificationDiagramParser.InstructionContext ctx) {
        return super.visitInstruction(ctx);
    }

    @Override
    public String visitElement(JustificationDiagramParser.ElementContext ctx) {
        diagram.nodes.put(ctx.ALIAS().getText(),
                NodeFactory.create(ctx.TYPE().getText(), ctx.ALIAS().getText(),  ctx.label.getText(),realizationList));
        return super.visitElement(ctx);
    }

    @Override
    public String visitConclusion(JustificationDiagramParser.ConclusionContext ctx) {
        diagram.nodes.put(ctx.ALIAS().getText(), new Conclusion(ctx.ALIAS().getText(),
                ctx.label.getText(), ctx.restriction != null? ctx.restriction.getText() : null));
        return super.visitConclusion(ctx);
    }
}
