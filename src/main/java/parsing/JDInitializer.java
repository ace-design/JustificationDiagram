package parsing;

import justificationDiagram.JustificationDiagram;
import models.*;

public class JDInitializer extends PlantUMLBaseVisitor<String> {
    public JustificationDiagram diagram;

    @Override
    public String visitDiagram(PlantUMLParser.DiagramContext ctx) {
        diagram = new JustificationDiagram();
        return super.visitDiagram(ctx);
    }

    @Override
    public String visitDeclaration(PlantUMLParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    @Override
    public String visitRelation(PlantUMLParser.RelationContext ctx) {
        return super.visitRelation(ctx);
    }

    @Override
    public String visitInstruction(PlantUMLParser.InstructionContext ctx) {
        return super.visitInstruction(ctx);
    }

    @Override
    public String visitElement(PlantUMLParser.ElementContext ctx) {
        diagram.nodes.put(ctx.ALIAS().getText(),
                NodeFactory.create(ctx.TYPE().getText(), ctx.ALIAS().getText(),  ctx.label.getText()));
        return super.visitElement(ctx);
    }

    @Override
    public String visitConclusion(PlantUMLParser.ConclusionContext ctx) {
        diagram.nodes.put(ctx.ALIAS().getText(), new Conclusion(ctx.ALIAS().getText(),
                ctx.label.getText(), ctx.restriction != null? ctx.restriction.getText() : null));
        return super.visitConclusion(ctx);
    }
}
