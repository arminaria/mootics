package de.tu.berlin.control;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

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
    public ListView list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final DBController db = DBController.getInstance();
        final List<String> selfTests = db.getSelfTests();

        List<Point2D> chartPoints = db.getChartPoints(selfTests.get(0));

        yAxis = new NumberAxis(0,105,10);

        xAxis.setLabel("Actions");
        yAxis.setLabel("Grade in Test");

        XYChart.Series series = new XYChart.Series();
        for (Point2D chartPoint : chartPoints) {
            XYChart.Data<Double, Double> doubleDoubleData = new XYChart.Data<Double, Double>(chartPoint.getX(), chartPoint.getY());
            series.getData().add(doubleDoubleData);
        }

        chart.getData().add(series);

        for (String selfTest : selfTests) {
            Button button = new Button(selfTest);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Button btn = (Button) mouseEvent.getSource();
                    List<Point2D> chartPoints = db.getChartPoints(btn.getText());
                    chart.setTitle(btn.getText());
                    XYChart.Series series = new XYChart.Series();
                    for (Point2D chartPoint : chartPoints) {
                        XYChart.Data<Double, Double> doubleDoubleData = new XYChart.Data<Double,Double>(chartPoint.getX(), chartPoint.getY());
                        series.getData().add(doubleDoubleData);
                    }
                    chart.getData().clear();
                    if(series.getData().isEmpty()){
                        yAxis = new NumberAxis(0,100,100);
                    }
                    else {
                        chart.getData().add(series);
                    }
                }
            });
            list.getItems().add(button);
        }


    }

}
