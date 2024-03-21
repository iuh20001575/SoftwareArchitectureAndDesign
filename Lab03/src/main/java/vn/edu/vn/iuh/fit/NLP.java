package vn.edu.vn.iuh.fit;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.FileInputStream;
import java.io.InputStream;

public class NLP {
    private static Parser parser = null;
    public static POSTaggerME posTaggerME = null;

    static {
        try {
            InputStream modelIn = new FileInputStream("models/en-parser-chunking.bin");

            ParserModel model = new ParserModel(modelIn);

            parser = ParserFactory.create(model);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        try (InputStream modelIn = new FileInputStream("models/en-pos-maxent.bin")){
            POSModel model = new POSModel(modelIn);
            posTaggerME = new POSTaggerME(model);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static boolean isNounOrNounPhrase(String string) {
        Parse[] parses = ParserTool.parseLine(string, parser, 1);

        for (Parse pars : parses) {
            String substring = pars.toStringPennTreebank().substring(6);

            if (!substring.startsWith("NP") && !substring.startsWith("NN"))
                return false;

            if (substring.startsWith("NNP") || substring.startsWith("NNS"))
                return false;
        }

        return true;
    }

    public static boolean isVerb(String word) {
        Parse[] parses = ParserTool.parseLine(word, parser, 1);

        for (Parse pars : parses) {
            String substring = pars.toStringPennTreebank().substring(6);

            if (!substring.startsWith("V"))
                return false;

            if (substring.startsWith("VBP") || substring.startsWith("VBN") || substring.startsWith("VBG"))
                return false;
        }

        return true;
    }
}