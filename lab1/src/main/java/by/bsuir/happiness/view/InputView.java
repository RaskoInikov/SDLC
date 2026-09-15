package by.bsuir.happiness.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InputView {

    private final Stage stage = new Stage();

    private final TextField sleepField = new TextField();
    private final TextField coffeeField = new TextField();
    private final TextField communicationField = new TextField();
    private final TextField workField = new TextField();
    private final TextField weekendsField = new TextField();

    private final Button saveButton = new Button("Сохранить");
    private final Button cancelButton = new Button("Отмена");

    public InputView(Stage owner) {
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Ввод данных");
        stage.setResizable(false);

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        addRow(grid, 0, "Сон:", sleepField);
        addRow(grid, 1, "Кофе:", coffeeField);
        addRow(grid, 2, "Общение:", communicationField);
        addRow(grid, 3, "Работа:", workField);
        addRow(grid, 4, "Выходные:", weekendsField);

        HBox buttons = new HBox(10, saveButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(18, grid, buttons);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 390, 280));
    }

    private void addRow(GridPane grid, int row, String name, TextField field) {
        grid.add(new Label(name), 0, row);
        grid.add(field, 1, row);
        field.setPrefWidth(220);
    }

    public void show() {
        stage.showAndWait();
    }

    public void close() {
        stage.close();
    }

    public TextField getSleepField() {
        return sleepField;
    }

    public TextField getCoffeeField() {
        return coffeeField;
    }

    public TextField getCommunicationField() {
        return communicationField;
    }

    public TextField getWorkField() {
        return workField;
    }

    public TextField getWeekendsField() {
        return weekendsField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }
}
