package vn.edu.iuh.fit.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class WordDetail {
    private String type;
    private List<TypeDetail> typeDetails;

    public WordDetail() {
        typeDetails = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TypeDetail> getTypeDetails() {
        return typeDetails;
    }

    public void setTypeDetails(List<TypeDetail> typeDetails) {
        this.typeDetails = typeDetails;
    }

    public void addTypeDetail(TypeDetail typeDetail) {
        typeDetails.add(typeDetail);
    }

    @Override
    public String toString() {
        return "WordDetail{" +
                "type='" + type + '\'' +
                ", typeDetails=" + typeDetails +
                '}';
    }
}
