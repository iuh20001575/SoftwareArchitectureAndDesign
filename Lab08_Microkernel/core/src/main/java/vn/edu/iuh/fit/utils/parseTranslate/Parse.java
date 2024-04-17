package vn.edu.iuh.fit.utils.parseTranslate;

import vn.edu.iuh.fit.models.Word;

import java.io.FileNotFoundException;
import java.util.Map;

public interface Parse {
    Map<String, Word> parse(String fileName) throws FileNotFoundException;
}
