package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import de.tu.berlin.model.Grades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Armin on 27.11.13.
 */
public interface Parser {

    public void setSeparator(char separator);
    public char getSeparator();
    public List<Data> parseAllData(File csvFile) throws IOException;
    public List<Grades> parseGrades(File csvFile) throws IOException;
}
