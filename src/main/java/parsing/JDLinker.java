package parsing;

import justificationDiagram.JustificationDiagram;
import models.RelationFactory;

public class JDLinker extends PlantUMLBaseVisitor<String> {
    public JustificationDiagram diagram;

    public JDLinker(JustificationDiagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public String visitDiagram(PlantUMLParser.DiagramContext ctx) {
        return super.visitDiagram(ctx);
    }

    @Override
    public String visitDeclaration(PlantUMLParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    @Override
    public String visitRelation(PlantUMLParser.RelationContext ctx) {
        diagram.relations.add(RelationFactory.create(ctx.LINK().getText(), diagram.nodes.get(ctx.ALIAS(0).getText()),
                diagram.nodes.get(ctx.ALIAS(1).getText())));
        diagram.nodes.get(ctx.ALIAS(0).getText()).addParent(diagram.nodes.get(ctx.ALIAS(1).getText()));
        diagram.nodes.get(ctx.ALIAS(1).getText()).addChild(diagram.nodes.get(ctx.ALIAS(0).getText()));
        return super.visitRelation(ctx);
    }

    @Override
    public String visitInstruction(PlantUMLParser.InstructionContext ctx) {
        return super.visitInstruction(ctx);
    }

    @Override
    public String visitElement(PlantUMLParser.ElementContext ctx) {
        return super.visitElement(ctx);
    }

    @Override
    public String visitConclusion(PlantUMLParser.ConclusionContext ctx) {
        return super.visitConclusion(ctx);
    }
}
