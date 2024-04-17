package vn.edu.iuh.fit;

import vn.edu.iuh.fit.models.TypeDetail;
import vn.edu.iuh.fit.models.Word;
import vn.edu.iuh.fit.models.WordDetail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishToVietnamese implements Language {
    private final Map<String, Word> map = new TreeMap<>();

    @Override
    public Word lookup(String word) {
        return map.get(word);
    }

    @Override
    public void readData() throws FileNotFoundException {
        File myObj = new File("dictionary/anhviet109K.dict");
        Scanner myReader = new Scanner(myObj);
        Pattern pattern = Pattern.compile("^@.*$");
        Pattern typePattern = Pattern.compile("^\\*.*$");
        Pattern theoryPattern = Pattern.compile("^-.*$");
        Pattern examplePattern = Pattern.compile("^=.*$");

        Word word = null;
        WordDetail wordDetail = null;
        TypeDetail typeDetail = null;

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();

            Matcher matcher = pattern.matcher(data);

            if (matcher.find()) {
                if (word != null) {
                    if (wordDetail != null && wordDetail.getTypeDetails().isEmpty())
                        wordDetail.addTypeDetail(typeDetail);

                    if (word.getDetails().isEmpty())
                        word.addWordDetail(wordDetail);

                    map.put(word.getWord(), word);
                }

                String[] split = data.split("/");

                word = new Word();
                word.setWord(split[0].substring(1).trim());

                if (split.length > 1)
                    word.setPronounce(split[1].trim());
            } else if (typePattern.matcher(data).matches()) {
                if (word == null) word = new Word();

                if (wordDetail != null)
                    word.addWordDetail(wordDetail);

                wordDetail = new WordDetail();
                wordDetail.setType(data.substring(1).trim());
            } else if (theoryPattern.matcher(data).matches()) {
                if (wordDetail == null) wordDetail = new WordDetail();

                if (typeDetail != null)
                    wordDetail.addTypeDetail(typeDetail);

                typeDetail = new TypeDetail();
                typeDetail.setTheory(data.substring(1).trim());
            } else if (examplePattern.matcher(data).matches()) {
                if (typeDetail == null)
                    typeDetail = new TypeDetail();

                typeDetail.addExample(data.substring(1).trim());
            }
        }
    }

    @Override
    public String name() {
        return "en-vn";
    }
}
