package vn.edu.iuh.fit.models;

import java.util.ArrayList;
import java.util.List;

public class Word {
    private String word;
    private String pronounce;
    private List<WordDetail> details;

    public Word() {
        details = new ArrayList<>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public List<WordDetail> getDetails() {
        return details;
    }

    public void setDetails(List<WordDetail> details) {
        this.details = details;
    }

    public void addWordDetail(WordDetail detail) {
        details.add(detail);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", pronounce='" + pronounce + '\'' +
                ", details=" + details +
                '}';
    }
}
