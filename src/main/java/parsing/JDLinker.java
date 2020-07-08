package parsing;

import justificationDiagram.JustificationDiagram;
import models.Relation;
import models.RelationFactory;

public class JDLinker extends JustificationDiagramBaseVisitor<String> {
    public JustificationDiagram diagram;

    public JDLinker(JustificationDiagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public String visitDiagram(JustificationDiagramParser.DiagramContext ctx) {
        return super.visitDiagram(ctx);
    }

    @Override
    public String visitDeclaration(JustificationDiagramParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    @Override
    public String visitRelation(JustificationDiagramParser.RelationContext ctx) {
        Relation relation = RelationFactory.create(ctx.LINK().getText(), diagram.getNodes().get(ctx.ALIAS(0).getText()),
                diagram.getNodes().get(ctx.ALIAS(1).getText()));
        diagram.getRelations().add(relation);
        diagram.getNodes().get(ctx.ALIAS(0).getText()).addOutput(relation); 
        diagram.getNodes().get(ctx.ALIAS(1).getText()).addInput(relation);
        return super.visitRelation(ctx);
    }

    @Override
    public String visitInstruction(JustificationDiagramParser.InstructionContext ctx) {
        return super.visitInstruction(ctx);
    }

    @Override
    public String visitElement(JustificationDiagramParser.ElementContext ctx) {
        return super.visitElement(ctx);
    }

    @Override
    public String visitConclusion(JustificationDiagramParser.ConclusionContext ctx) {
        return super.visitConclusion(ctx);
    }
}
