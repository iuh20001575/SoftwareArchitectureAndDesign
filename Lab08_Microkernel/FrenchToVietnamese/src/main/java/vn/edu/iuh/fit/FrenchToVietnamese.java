package vn.edu.iuh.fit;

import vn.edu.iuh.fit.models.Word;
import vn.edu.iuh.fit.utils.parseTranslate.impl.InformatikParse;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;

public class FrenchToVietnamese implements Language {
    private final Map<String, Word> map = new TreeMap<>();

    @Override
    public Word lookup(String word) {
        return map.get(word);
    }

    @Override
    public void readData() throws FileNotFoundException {
        Map<String, Word> parse = new InformatikParse().parse("dictionary/phapviet.dict");

        map.putAll(parse);
    }

    @Override
    public String name() {
        return "fr-vn";
    }
}
