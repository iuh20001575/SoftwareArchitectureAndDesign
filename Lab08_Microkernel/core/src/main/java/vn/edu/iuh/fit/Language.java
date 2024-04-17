package vn.edu.iuh.fit;

import vn.edu.iuh.fit.models.Word;

import java.io.FileNotFoundException;
import java.util.Map;

public interface Language extends Plugin {
    Word lookup(String word);
    void readData() throws FileNotFoundException;
}
