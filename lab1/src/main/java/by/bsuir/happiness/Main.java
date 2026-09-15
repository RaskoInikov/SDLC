package by.bsuir.happiness;

import by.bsuir.happiness.controller.HappinessController;
import by.bsuir.happiness.model.HappinessModel;
import by.bsuir.happiness.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        HappinessModel model = new HappinessModel();
        MainView view = new MainView();
        new HappinessController(model, view, primaryStage);

        primaryStage.setTitle("Калькулятор счастья");
        primaryStage.setScene(new Scene(view.getRoot(), 520, 430));
        primaryStage.setMinWidth(520);
        primaryStage.setMinHeight(430);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
