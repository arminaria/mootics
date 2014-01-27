package de.tu.berlin.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Armin on 13.01.14.
 */

public class ChartController implements Initializable {

    public String currentContext;
    public ScatterChart<Number, Number> chart;
    public NumberAxis xAxis;
    public NumberAxis yAxis;
    public ListView list;
    public CheckBox checkBoxTimeFilter;
    public TextField textFieldFilterTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final DBController db = DBController.getInstance();
        final List<String> selfTests = db.getSelfTests();

        currentContext = selfTests.get(0);
        List<Point2D> chartPoints = db.getChartPoints(currentContext, null);

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
                    currentContext = btn.getText();
                    List<Point2D> chartPoints = db.getChartPoints(btn.getText(), null);
                    chart.setTitle(btn.getText());
                    XYChart.Series series = new XYChart.Series();
                    for (Point2D chartPoint : chartPoints) {
                        XYChart.Data<Double, Double> doubleDoubleData = new XYChart.Data<Double, Double>(chartPoint.getX(), chartPoint.getY());
                        series.getData().add(doubleDoubleData);
                    }
                    chart.getData().clear();
                    if (series.getData().isEmpty()) {
                        yAxis = new NumberAxis(0, 100, 100);
                    } else {
                        chart.getData().add(series);
                    }
                }
            });
            list.getItems().add(button);
        }


    }

    public void timeFilter(ActionEvent actionEvent) {
        List<XYChart.Series> listSeries = new ArrayList<>();

        DBController db = DBController.getInstance();
        List<Point2D> chartPoints = db.getChartPoints(this.currentContext, null);
        XYChart.Series series = new XYChart.Series();
        for (Point2D chartPoint : chartPoints) {
            XYChart.Data<Double, Double> doubleDoubleData =
                    new XYChart.Data<Double,Double>(chartPoint.getX(), chartPoint.getY());
            series.getData().add(doubleDoubleData);
        }
        listSeries.add(series);

        if(checkBoxTimeFilter.isSelected() && !textFieldFilterTime.getText().isEmpty()){
            chartPoints = db.getChartPoints(this.currentContext, TimeFilter.class,
                    Integer.parseInt(textFieldFilterTime.getText()));
            series = new XYChart.Series();
            for (Point2D chartPoint : chartPoints) {
                XYChart.Data<Double, Double> doubleDoubleData =
                        new XYChart.Data<Double,Double>(chartPoint.getX(), chartPoint.getY());
                series.getData().add(doubleDoubleData);
            }
            listSeries.add(series);
        }


        chart.getData().clear();
        for (XYChart.Series listSery : listSeries) {
            chart.getData().add(listSery);
        }
    }

    public void changeTime(ActionEvent actionEvent) {
        String text = textFieldFilterTime.getText();
        if(!text.isEmpty()){
            try{
                int i = Integer.parseInt(text);
                timeFilter(null);
            }catch (Exception e){

            }
        }
    }
}
