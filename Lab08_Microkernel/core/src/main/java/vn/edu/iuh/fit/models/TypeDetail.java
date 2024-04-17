package vn.edu.iuh.fit.models;

import java.util.ArrayList;
import java.util.List;

public class TypeDetail {
    private String theory;
    private List<String> examples;

    public TypeDetail() {
        examples = new ArrayList<>();
    }

    public String getTheory() {
        return theory;
    }

    public void setTheory(String theory) {
        this.theory = theory;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public void addExample(String example) {
        examples.add(example);
    }

    @Override
    public String toString() {
        return "TypeDetail{" +
                "theory='" + theory + '\'' +
                ", examples=" + examples +
                '}';
    }
}
