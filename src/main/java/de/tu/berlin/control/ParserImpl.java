package de.tu.berlin.control;

import au.com.bytecode.opencsv.CSVReader;
import de.tu.berlin.model.Data;
import de.tu.berlin.model.Grades;
import de.tu.berlin.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Armin on 27.11.13.
 */
public class ParserImpl implements Parser {
    private Logger log = LoggerFactory.getLogger(Parser.class);
    private char separator = ';';


    @Override
    public char getSeparator() {
        return separator;
    }

    @Override
    public void setSeparator(char separator) {
        this.separator = separator;
    }

    @Override
    public List<Data> parseAllData(File csvFile) throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(csvFile.getAbsoluteFile()), getSeparator());

        List<String[]> strings = csvReader.readAll();
        List<Data> dataList = new ArrayList<Data>(strings.size());

        for (String[] s : strings) {
            try {
                Long.parseLong(s[POS.userId.get()]);
            } catch (Exception e) {
                continue;
            }

            Data data = new Data();

            // User
            User user = new User();
            user.setId(Long.parseLong(s[POS.userId.get()]));

            data.setTime(parseTime(s[POS.time.get()]));
            data.setAction(s[POS.action.get()].split("\\(")[0].trim());
            data.setUrl(s[POS.action.get()].split("\\(")[1].split("\\)")[0].trim());
            data.setUser(user);
            data.setMaterial(s[POS.info.get()]);
            data.setCategory(getCategory(s[POS.info.get()]));
            data.setLecture("TODO");

            log.info("parsed {}" , data);

            dataList.add(data);
        }

        return dataList;
    }

    @Override
    public List<Grades> parseGrades(File csvFile) throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(csvFile.getAbsoluteFile()), getSeparator());

        List<String[]> strings = csvReader.readAll();
        List<Grades> gradesList = new ArrayList<Grades>(strings.size());

        String[] names = strings.get(0);

        for (String[] s : strings) {
            try{
                for (int i = 1; i < s.length; i++) {
                    Grades g = new Grades();
                    Long userId = Long.valueOf(s[0]);

                    DBController db = DBController.getInstance();
                    User user = db.getUser(userId);
                    if(user == null){
                        user = new User();
                        user.setId(userId);
                        db.createUser(user);
                    }
                    g.setUser(user);
                    String grade = s[i];
                    if(!grade.equals("") && grade != null && !grade.equals("-")){
                        g.setGrade(Long.parseLong(grade));
                    }
                    String name = names[i];
                    g.setName(name);

                    g.setCategory(getCategory(name));
                    g.setLecture("TODO");
                    gradesList.add(g);
                }
            }catch (NumberFormatException e){
                continue;
            }
        }
        return gradesList;
    }

    private String getCategory(String info) {
        info = info.toLowerCase();

        if(info.contains("homework")){
            return "Homework";
        }else if(info.contains("video")){
            return "Video";
        }else if(info.contains("book") || info.contains("lecture") || info.contains("vorlesungsfolien")){
            return "Lecture";
        }else if(info.contains("exercise")){
            return "Exercise";
        }else if(info.contains("self-test")){
            return "Test";
        }else if(info.contains("kahoot")){
            return "Kahoot";
        }else if(info.contains("sprachkommunikation")){
            return "View";
        }else if(info.contains("skript") || info.contains("script")){
            return "Script";
        }else {
            return "TODO";
        }
    }

    private Calendar parseTime(String time){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("DD.MM.yyyy HH:mm");
        try {
            cal.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

}
