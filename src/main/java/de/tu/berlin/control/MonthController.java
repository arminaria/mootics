package de.tu.berlin.control;

import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Armin on 20.01.14.
 */
public class MonthController  implements Initializable{


    public NumberAxis yAxis;
    public CategoryAxis xAxis;
    public LineChart lineChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBController db = DBController.getInstance();
        final XYChart.Series<String , Number> series2 = new XYChart.Series<String , Number>();
        for (int i = 1; i <= 31; i++) {
            int clicksForDay = db.getClicksForDay(i);
            series2.getData().add(new XYChart.Data<String , Number>(String.valueOf(i), clicksForDay));
        }
        lineChart.getData().add(series2);
    }
}
