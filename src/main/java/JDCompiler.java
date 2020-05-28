import export.GraphDrawer;
import export.RequirementsLister;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import justificationDiagram.JustificationDiagram;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.cli.*;
import parsing.*;
import java.io.*;

public class JDCompiler {

    public static void main(String[] args) throws IOException {
        CommandLine cmd = setup(args);
        String outputOption = cmd.getOptionValue("output");

        if (outputOption != null) {
            if (!outputIsValid(outputOption)) {
                System.exit(1);
            }
        }
        for (int i = 0; i < cmd.getArgs().length; ++i) {
            String inputFile = cmd.getArgs()[i];
            if (!inputIsValid(inputFile)) {
                continue;
            }
            generateFiles(cmd, cmd.getArgs()[i], generateOutputName(outputOption, i, inputFile));
        }
    }

    private static CommandLine setup(String[] args) {
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        Option output = new Option("o", "output", true, "output file path");
        options.addOption(output);

        options.addOption("png", "generate graph");
        options.addOption("gv", "generate gv file");
        options.addOption("td", "generate todo list");

        try {
            return parser.parse(options, args);
        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return null;
        }
    }

    private static void generateFiles(CommandLine cmd, String inputFilePath, String outputFilePath) throws IOException {
    	System.out.println("Generate from " + inputFilePath + "  To  " + outputFilePath);
        JustificationDiagram diagram = createDiagram(inputFilePath);
        diagram.analyseDiagrammeRelation();
        GraphDrawer drawer = new GraphDrawer();
        
        StringBuilder gv = drawer.draw(diagram);

        if (cmd.hasOption("png")) {
            InputStream dot = new ByteArrayInputStream(gv.toString().getBytes());
            MutableGraph g = new guru.nidi.graphviz.parse.Parser().read(dot);
            Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(outputFilePath + ".png"));
        }
        if (cmd.hasOption("gv")) {
            PrintWriter out = new PrintWriter(new FileWriter(outputFilePath + ".gv"));
            out.print(gv);
            out.close();
        }
        if (cmd.hasOption("td")) {
            PrintWriter out = new PrintWriter(new FileWriter(outputFilePath + ".todo"));
            RequirementsLister lister = new RequirementsLister();
            out.print(lister.generate(diagram));
            out.close();
        }
    }

    private static boolean inputIsValid(String in) {
        if (!in.matches(".*\\.(jd|txt)")) {
            System.err.println(in + " is not a valid name. The input file should end with .jd or .txt");
            return false;
        }
        return true;
    }

    private static String generateOutputName(String outputOption, int index, String inputOption) {
        if (outputOption != null) {
            if (index > 0) {
                return outputOption + index;
            } else {
                return outputOption;
            }
        } else {
        	String res = inputOption.split("\\.")[0];
            return res;
        }
    }

    private static boolean outputIsValid(String out) {
        if (out.matches(".*\\..*")) {
            System.err.println(out + "The output file should not have an extension");
            return false;
        }
        
        //Create path to outputfile if needed
        File outputFile = new File(out);
        if (!(outputFile.exists())) {
        	//File outputpathDir = new File(outputFile.getAbsolutePath());
        	String[] parts = out.split("/");
        	if (parts.length != 1) {
        		String outDir = out.substring(0, out.lastIndexOf("/"));
        		new File(outDir).mkdirs();
        	}
        }
        
        return true;
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
        JustificationDiagramLexer lexer = new JustificationDiagramLexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        JustificationDiagramParser parser = new JustificationDiagramParser(tokens);
        return parser.diagram();
    }
    
}
