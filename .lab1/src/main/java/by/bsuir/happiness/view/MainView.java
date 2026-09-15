package by.bsuir.happiness.view;

import by.bsuir.happiness.model.HappinessModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainView {

    private final VBox root = new VBox(15);
    private final Button enterDataButton = new Button("Ввести данные");

    private final Label sleepValue = new Label("-");
    private final Label coffeeValue = new Label("-");
    private final Label communicationValue = new Label("-");
    private final Label workValue = new Label("-");
    private final Label weekendsValue = new Label("-");
    private final Label indexValue = new Label("-");
    private final Label recommendationValue = new Label("-");

    public MainView() {
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Калькулятор счастья");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        enterDataButton.setPrefWidth(180);
        enterDataButton.setPrefHeight(35);

        GridPane resultGrid = new GridPane();
        resultGrid.setHgap(20);
        resultGrid.setVgap(10);
        resultGrid.setAlignment(Pos.CENTER_LEFT);

        addRow(resultGrid, 0, "Сон:", sleepValue);
        addRow(resultGrid, 1, "Кофе:", coffeeValue);
        addRow(resultGrid, 2, "Общение:", communicationValue);
        addRow(resultGrid, 3, "Работа:", workValue);
        addRow(resultGrid, 4, "Выходные:", weekendsValue);
        addRow(resultGrid, 5, "Индекс счастья:", indexValue);
        addRow(resultGrid, 6, "Рекомендация:", recommendationValue);

        root.getChildren().addAll(title, enterDataButton, resultGrid);
    }

    private void addRow(GridPane grid, int row, String name, Label value) {
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold;");
        grid.add(nameLabel, 0, row);
        grid.add(value, 1, row);
    }

    public void bindToModel(HappinessModel model) {
        sleepValue.textProperty().bind(model.sleepProperty().asString("%.2f"));
        coffeeValue.textProperty().bind(model.coffeeProperty().asString("%.2f"));
        communicationValue.textProperty().bind(model.communicationProperty().asString("%.2f"));
        workValue.textProperty().bind(model.workProperty().asString("%.2f"));
        weekendsValue.textProperty().bind(model.weekendsProperty().asString("%.2f"));
        indexValue.textProperty().bind(model.happinessIndexProperty().asString("%.2f"));
        recommendationValue.textProperty().bind(model.recommendationProperty());
    }

    public Parent getRoot() {
        return root;
    }

    public Button getEnterDataButton() {
        return enterDataButton;
    }
}
