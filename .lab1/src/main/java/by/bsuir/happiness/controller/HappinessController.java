package by.bsuir.happiness.controller;

import by.bsuir.happiness.model.HappinessModel;
import by.bsuir.happiness.view.InputView;
import by.bsuir.happiness.view.MainView;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class HappinessController {

    private final HappinessModel model;
    private final MainView mainView;
    private final Stage mainStage;

    public HappinessController(HappinessModel model, MainView mainView, Stage mainStage) {
        this.model = model;
        this.mainView = mainView;
        this.mainStage = mainStage;

        mainView.bindToModel(model);
        mainView.getEnterDataButton().setOnAction(event -> openInputWindow());
    }

    private void openInputWindow() {
        InputView inputView = new InputView(mainStage);

        inputView.getSleepField().setText(format(model.getSleep()));
        inputView.getCoffeeField().setText(format(model.getCoffee()));
        inputView.getCommunicationField().setText(format(model.getCommunication()));
        inputView.getWorkField().setText(format(model.getWork()));
        inputView.getWeekendsField().setText(format(model.getWeekends()));

        inputView.getSaveButton().setOnAction(event -> saveData(inputView));
        inputView.getCancelButton().setOnAction(event -> inputView.close());

        inputView.show();
    }

    private void saveData(InputView inputView) {
        try {
            double sleep = parse(inputView.getSleepField().getText(), "сон");
            double coffee = parse(inputView.getCoffeeField().getText(), "кофе");
            double communication = parse(inputView.getCommunicationField().getText(), "общение");
            double work = parse(inputView.getWorkField().getText(), "работа");
            double weekends = parse(inputView.getWeekendsField().getText(), "выходные");

            validate(sleep, coffee, communication, work, weekends);

            model.setData(sleep, coffee, communication, work, weekends);
            inputView.close();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private double parse(String text, String fieldName) {
        String normalized = text.trim().replace(',', '.');

        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Поле «" + fieldName + "» не заполнено.");
        }

        try {
            double value = Double.parseDouble(normalized);

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException(
                        "Поле «" + fieldName + "» содержит некорректное число.");
            }

            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Поле «" + fieldName + "» содержит некорректное число.");
        }
    }

    private void validate(double sleep, double coffee, double communication,
                          double work, double weekends) {
        if (sleep < 0 || coffee < 0 || communication < 0 || work < 0 || weekends < 0) {
            throw new IllegalArgumentException("Значения не могут быть отрицательными.");
        }

        if (sleep > 24) {
            throw new IllegalArgumentException("Количество сна должно быть от 0 до 24 часов в сутки.");
        }

        if (work > 168) {
            throw new IllegalArgumentException("Количество работы должно быть от 0 до 168 часов в неделю.");
        }

        if (weekends > 7) {
            throw new IllegalArgumentException("Количество выходных должно быть от 0 до 7 дней в неделю.");
        }
    }

    private String format(double value) {
        return String.format(java.util.Locale.US, "%.2f", value);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Некорректные данные");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
