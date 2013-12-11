package de.tu.berlin.control;

import au.com.bytecode.opencsv.CSVReader;
import de.tu.berlin.model.Content;
import de.tu.berlin.model.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
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
    public List<Data> parseAll(File csvFile) throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(csvFile.getAbsoluteFile()), getSeparator());

        List<String[]> strings = csvReader.readAll();
        List<Data> dataList = new ArrayList<Data>(strings.size());
        Calendar now = Calendar.getInstance();
        for (String[] s : strings) {

            Data data = new Data();

            data.setCreated(now);

            // use the same id for the data as in moodle
            // if the id is not a number the data is invalid
            try {
                data.setId(Long.valueOf(s[POS.id.get()]));
            } catch (Exception e) {
                continue;
            }

            // calculate the time given with timestamp in seconds
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Timestamp(Long.parseLong(s[POS.time.get()]) * 1000));
            data.setTime(calendar);

            // if userId not set, set it to 0
            data.setUserId(s[POS.userId.get()].isEmpty() ? "NONE" : s[POS.userId.get()]);

            // if Course is not set, set it to 0
            data.setCourse(s[POS.course.get()].isEmpty() ? new Long(0) : Long.valueOf(s[POS.course.get()]));

            data.setModule(s[POS.module.get()]);

            // create Content
            Content c = new Content();
            c.setId(s[POS.cmid.get()].isEmpty() ? (long) 0 : Long.parseLong(s[POS.cmid.get()]));
            c.setName("TODO");
            data.setCmid(c);

            data.setAction(s[POS.action.get()]);
            data.setUrl(s[POS.url.get()]);
            data.setInfo(s.length > 11 ? s[POS.info.get()] : "");

            log.debug("PARSED: {}", data);

            dataList.add(data);
        }

        return dataList;
    }


}
