package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Armin on 20.01.14.
 */
public class WeeksController implements Initializable {

    Logger log = LoggerFactory.getLogger(WeeksController.class);

    public Text text;
    public StackedBarChart barChart;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    public LineChart barChart2;
    public CategoryAxis xAxis2;
    public NumberAxis yAxis2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBController db = DBController.getInstance();

        final XYChart.Series<String, Long> series = new XYChart.Series<String, Long>();
        List<Data> allData = db.getAllData();

        String[] weekDays = {"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
        String[] categories = {"Video", "Homework", "Exercise", "Lecture", "Test", "Forum", "View"};

        xAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList(weekDays)));
        for (String category : categories) {
            XYChart.Series<String, Number> s = new XYChart.Series<String, Number>();
            s.setName(category);
            log.info("Category {}", category);
            for (String weekDay : weekDays) {
                Long clicksForDayOfWeekOn = db.getClicksForDayOfWeekOn(weekDay, category, allData);
                log.info("Woche {}, clicks {}", weekDay, clicksForDayOfWeekOn);

                s.getData().add(
                        new XYChart.Data<String, Number>(weekDay, clicksForDayOfWeekOn));
            }
            barChart.getData().add(s);
        }

    /*
        long mo = 0;
        long di = 0;
        long mi = 0;
        long don = 0;
        long fr = 0;
        long sa = 0;
        long so = 0;

        for (Data data : allData) {
            Calendar time = data.getTime();
            int i = time.get(Calendar.DAY_OF_WEEK);
            String DayOfWeek = time.getDisplayName(Calendar.DAY_OF_WEEK, 0, Locale.GERMAN);
            switch (DayOfWeek){
                case "Mo":
                    mo++;
                break;
                case "Di":di++;break;
                case "Mi":mi++;break;
                case "Do":don++;break;
                case "Fr":fr++;break;
                case "Sa":sa++;break;
                case "So":so++;break;
            }
        }

        series.getData().add(new XYChart.Data<String, Long>("Mo",mo));
        series.getData().add(new XYChart.Data<String, Long>("Di",di));
        series.getData().add(new XYChart.Data<String, Long>("Mi",mi));
        series.getData().add(new XYChart.Data<String, Long>("Do",don));
        series.getData().add(new XYChart.Data<String, Long>("Fr",fr));
        series.getData().add(new XYChart.Data<String, Long>("Sa",sa));
        series.getData().add(new XYChart.Data<String, Long>("So",so));

        barChart.getData().add(series);

        final XYChart.Series<String, Integer> series2 = new XYChart.Series<String, Integer>();
        for (int i = 1; i <= 31; i++) {
            int clicksForDay = db.getClicksForDay(i);
            series2.getData().add(new XYChart.Data<String, Integer>(String.valueOf(i), clicksForDay));
        }
        barChart2.getData().add(series2);
        */
    }
}
