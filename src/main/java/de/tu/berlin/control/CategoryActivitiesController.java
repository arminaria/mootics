package de.tu.berlin.control;

import de.tu.berlin.model.Grades;
import de.tu.berlin.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Armin on 13.01.14.
 */
public class CategoryActivitiesController implements Initializable{

    public ListView userList;
    public CategoryAxis xAxis;
    public BarChart barChart;
    public TableView gradesTable;
    public TableColumn grade;
    public TableColumn test;
    public TableColumn avgGrade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        test.setCellValueFactory(new PropertyValueFactory<Grades, String>("name"));
        grade.setCellValueFactory(new PropertyValueFactory<Grades, Long>("grade"));

        final DBController db = DBController.getInstance();
        final List<String> categories = db.getCategories();
        final List<User> users = db.getAllUser();
        final List<Grades> grades = db.getGrades(users.get(0));

        final ObservableList<Grades> gs= FXCollections.observableArrayList();
        gs.addAll(grades);
        gradesTable.setItems(gs);

        final XYChart.Series<String, Long> series = new XYChart.Series<>();
        for (String category : categories) {
            users.get(0);
            int clicksOn = db.getClicksOn(users.get(0), category);
            series.getData().add(new XYChart.Data<String, Long>(category,new Long(clicksOn)));
        }

        barChart.getData().add(series);


        for (User user : users) {
            final Button btn = new Button(String.valueOf(user.getId()));
            userList.getItems().add(btn);
            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Long userId = Long.parseLong(btn.getText());
                    User user = db.getUser(userId);
                    List<Grades> userGrades = db.getGrades(user);
                    gs.clear();
                    gs.addAll(userGrades);
                    gradesTable.setItems(gs);

                    XYChart.Series<String, Long> series = new XYChart.Series<>();
                    for (String category : categories) {
                        int clicksOn = db.getClicksOn(user, category);
                        series.getData().add(new XYChart.Data<String, Long>(category,new Long(clicksOn)));
                    }
                    barChart.getData().clear();
                    barChart.getData().addAll(series);
                }
            });
        }
    }
}
