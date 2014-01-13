package de.tu.berlin.control;

import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Armin on 13.01.14.
 */
public class ChartController implements Initializable{

    public ScatterChart<Number, Number> chart;
    public NumberAxis xAxis;
    public NumberAxis yAxis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBController db = DBController.getInstance();


        List<Point2D> chartPoints = db.getChartPoints();

        xAxis.setLabel("Actions");
        yAxis.setLabel("Grade in Test");

        XYChart.Series series = new XYChart.Series();
        for (Point2D chartPoint : chartPoints) {
            XYChart.Data<Double, Double> doubleDoubleData = new XYChart.Data<>(chartPoint.getX(), chartPoint.getY());
            series.getData().add(doubleDoubleData);
        }

        chart.getData().add(series);
    }

}
