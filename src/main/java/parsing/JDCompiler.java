package parsing;

import export.GraphDrawer;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import justificationDiagram.JustificationDiagram;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.cli.*;
import java.io.*;

public class JDCompiler {

    public static void main(String[] args) throws IOException {
        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file path");
        output.setRequired(true);
        options.addOption(output);

        options.addOption("gv", "output gv files");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            String inputFilePath = cmd.getOptionValue("input");
            String outputFilePath = cmd.getOptionValue("output");

            JustificationDiagram diagram = createDiagram(inputFilePath);
            GraphDrawer drawer = new GraphDrawer();
            StringBuilder gv = drawer.draw(diagram);

            if (cmd.hasOption("gv")) {
                String fileName = outputFilePath.split("\\.png")[0];
                PrintWriter out = new PrintWriter(new FileWriter(fileName + ".gv"));
                out.print(gv);
                out.close();
            }

            InputStream dot = new ByteArrayInputStream(gv.toString().getBytes());
            MutableGraph g = new guru.nidi.graphviz.parse.Parser().read(dot);
            Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(outputFilePath));

        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
    }

    public static JustificationDiagram createDiagram(String file) {
        JDInitializer factory = new JDInitializer();
        factory.visit(parseAntlr(file));
        JDLinker linker = new JDLinker(factory.diagram);
        linker.visit(parseAntlr((file)));
        return linker.diagram;
    }

    public static ParseTree parseAntlr(String file) {
        // create a CharStream that reads from standard input
        CharStream input = null; // create a lexer that feeds off of input CharStream
        try {
            input = CharStreams.fromFileName(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlantUMLLexer lexer = new PlantUMLLexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        PlantUMLParser parser = new PlantUMLParser(tokens);
        return parser.diagram();
    }
}
