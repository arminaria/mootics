package de.tu.berlin.control;

import de.tu.berlin.model.Data;
import de.tu.berlin.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Armin on 20.01.14.
 */
public class MidTermController implements Initializable {
    public ListView list;
    public Button selectAllBtn;
    public Button noneBtn;
    public ScatterChart scatterChart;
    public NumberAxis yAxis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        DBController db = DBController.getInstance();
        List<String> categories = db.getCategories();
        categories.remove("Exam");

        list.setItems(FXCollections.observableArrayList(categories));
        selectAllBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                list.getSelectionModel().selectAll();
                updateChart(list.getSelectionModel().getSelectedItems());
            }
        });

        noneBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                list.getSelectionModel().clearSelection();
                scatterChart.getData().clear();
            }
        });

        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                updateChart(list.getSelectionModel().getSelectedItems());
            }
        });
    }

    private void updateChart(ObservableList selectedItems) {

        final DBController db = DBController.getInstance();
        List<User> allUser = db.getAllUser();
        XYChart.Series<Number, Number> s = new XYChart.Series<Number, Number>();

        for (User user : allUser) {
            List<Data> clicksForCategory = db.getClicksForCategory(user, selectedItems);
            int count = clicksForCategory.size();
            long midTermExamGrade = db.getMidTermExamGrade(user);
            if(midTermExamGrade > 0){
                s.getData().add(new XYChart.Data<Number, Number>(count, midTermExamGrade));
            }
        }
        scatterChart.getData().clear();
        scatterChart.getData().add(s);


    }

}
