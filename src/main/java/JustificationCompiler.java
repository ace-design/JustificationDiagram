import nodes.Conclusion;

public class JustificationCompiler extends PlantUMLBaseVisitor<String> {
    JustificationDiagram diagram;

    @Override
    public String visitDiagram(PlantUMLParser.DiagramContext ctx) {
        diagram = new JustificationDiagram();
        String ret = super.visitDiagram(ctx);
        return ret;
    }

    @Override
    public String visitDeclaration(PlantUMLParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    @Override
    public String visitRelation(PlantUMLParser.RelationContext ctx) {
        diagram.relations.add(new Relation(ctx.ALIAS(0).getText(), ctx.ALIAS(1).getText()));
        return super.visitRelation(ctx);
    }

    @Override
    public String visitInstruction(PlantUMLParser.InstructionContext ctx) {
        return super.visitInstruction(ctx);
    }

    @Override
    public String visitElement(PlantUMLParser.ElementContext ctx) {
        diagram.nodes.add(NodeFactory.create(ctx.TYPE().getText(), ctx.ALIAS().getText(),  ctx.label.getText()));
        return super.visitElement(ctx);
    }

    @Override
    public String visitConclusion(PlantUMLParser.ConclusionContext ctx) {
        diagram.nodes.add(new Conclusion(ctx.ALIAS().getText(),  ctx.label.getText(), ctx.restriction != null? ctx.restriction.getText() : null));
        return super.visitConclusion(ctx);
    }
}
