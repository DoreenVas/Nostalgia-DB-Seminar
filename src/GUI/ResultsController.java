package GUI;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ResultsController {

    @FXML
    private TableView<Person> results;
    @FXML
    private TextArea column;
    @FXML
    private TextArea name;
    @FXML
    private TextArea surname;

    @FXML
    protected void addRow() {
        Person person = new Person(this.name.getText(), this.surname.getText());
        this.name.setText("");
        this.surname.setText("");
        this.results.getItems().add(person);
    }

    @FXML
    protected void clearAll() {
        this.results.getItems().clear();
    }

    @FXML
    protected void addColumn() {
//        TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        TableColumn<Person, String> surnameColumn = new TableColumn<>("Surname");
//        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
//        this.results.getColumns().add(nameColumn);
//        this.results.getColumns().add(surnameColumn);
        TableColumn<Person, String> column = new TableColumn<>(this.column.getText());
        column.setCellValueFactory(new PropertyValueFactory<>(this.column.getText()));
        this.results.getColumns().add(column);
        this.column.setText("");
    }

    public class Person {
        private String name;
        private String surname;

        public Person(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }
    }
}
