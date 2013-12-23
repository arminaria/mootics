package de.tu.berlin.control;

import au.com.bytecode.opencsv.CSVReader;
import de.tu.berlin.model.Data;
import de.tu.berlin.model.Material;
import de.tu.berlin.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            Data data = new Data();

            // User
            User user = new User();
            user.setId(Long.parseLong(s[POS.userId.get()]));

            // Material
            Material material = new Material();
            material.setName(s[POS.info.get()]);

            data.setTime(parseTime(s[POS.time.get()]));
            data.setAction(s[POS.action.get()].split("\\(")[0].trim());
            data.setUrl(s[POS.action.get()].split("\\(")[1].split("\\)")[0].trim());

            data.setUser(user);
            data.setMaterial(material);

            log.info("parsed {}" , data);

            dataList.add(data);
        }

        return dataList;
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
