package vn.edu.iuh.fit.utils.parseTranslate.impl;

import vn.edu.iuh.fit.models.TypeDetail;
import vn.edu.iuh.fit.models.Word;
import vn.edu.iuh.fit.models.WordDetail;
import vn.edu.iuh.fit.utils.parseTranslate.Parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InformatikParse implements Parse {
    @Override
    public Map<String, Word> parse(String fileName) throws FileNotFoundException {
        TreeMap<String, Word> map = new TreeMap<>();

        File myObj = new File(fileName);
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

            if (data.startsWith("@00-database"))
                continue;

            Matcher matcher = pattern.matcher(data);

            if (matcher.find()) {
                if (word != null) {
                    if (wordDetail != null)
                        wordDetail.addTypeDetail(typeDetail);
                    word.addWordDetail(wordDetail);

                    map.put(word.getWord(), word);

                    wordDetail = null;
                    typeDetail = null;
                }

                String[] split = data.split(" /");

                word = new Word();
                word.setWord(split[0].substring(1).trim());

                if (split.length > 1)
                    word.setPronounce(split[1].split("/")[0].trim());
            } else if (word == null) {
                continue;
            } else if (typePattern.matcher(data).matches()) {
                if (wordDetail != null) {
                    if (typeDetail != null && typeDetail.getExamples() != null && !typeDetail.getExamples().isEmpty()) {
                        wordDetail.addTypeDetail(typeDetail);
                        typeDetail = new TypeDetail();
                    }
                    word.addWordDetail(wordDetail);
                }

                wordDetail = new WordDetail();
                wordDetail.setType(data.substring(1).trim());
            } else if (theoryPattern.matcher(data).matches()) {
                if (wordDetail == null) wordDetail = new WordDetail();

                if (typeDetail != null && typeDetail.getTheory() != null && !typeDetail.getTheory().isEmpty())
                    wordDetail.addTypeDetail(typeDetail);

                typeDetail = new TypeDetail();
                typeDetail.setTheory(data.substring(1).trim());
            } else if (examplePattern.matcher(data).matches()) {
                if (typeDetail == null)
                    typeDetail = new TypeDetail();

                typeDetail.addExample(data.substring(1).trim());
            }
        }

        if (word != null) {
            if (wordDetail != null)
                wordDetail.addTypeDetail(typeDetail);
            word.addWordDetail(wordDetail);
            map.put(word.getWord(), word);
        }

        return map;
    }
}
