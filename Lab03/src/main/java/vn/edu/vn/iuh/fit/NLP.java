package vn.edu.vn.iuh.fit;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

import java.io.FileInputStream;
import java.io.InputStream;

public class NLP {
    private static Parser parser = null;

    static {
        try {
            InputStream modelIn = new FileInputStream("models/en-parser-chunking.bin");

            ParserModel model = new ParserModel(modelIn);

            parser = ParserFactory.create(model);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    public static boolean isNounOrNounPhrase(String string) {
        Parse[] parses = ParserTool.parseLine(string, parser, 1);

        for (Parse pars : parses) {
            String substring = pars.toStringPennTreebank().substring(6);

            if (!substring.startsWith("NP") && !substring.startsWith("NN"))
                return false;
        }

        return true;
    }
}