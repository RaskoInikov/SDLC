package by.bsuir.happiness.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class HappinessModel {

    private final DoubleProperty sleep = new SimpleDoubleProperty(0);
    private final DoubleProperty coffee = new SimpleDoubleProperty(0);
    private final DoubleProperty communication = new SimpleDoubleProperty(0);
    private final DoubleProperty work = new SimpleDoubleProperty(0);
    private final DoubleProperty weekends = new SimpleDoubleProperty(0);

    private final SimpleDoubleProperty happinessIndex = new SimpleDoubleProperty(0);
    private final SimpleStringProperty recommendation = new SimpleStringProperty("");
    private final SimpleObjectProperty<Boolean> hasData = new SimpleObjectProperty<>(false);

    public HappinessModel() {
        sleep.addListener((obs, oldValue, newValue) -> recalculate());
        coffee.addListener((obs, oldValue, newValue) -> recalculate());
        communication.addListener((obs, oldValue, newValue) -> recalculate());
        work.addListener((obs, oldValue, newValue) -> recalculate());
        weekends.addListener((obs, oldValue, newValue) -> recalculate());
    }

    public void setData(double sleep, double coffee, double communication,
            double work, double weekends) {
        this.sleep.set(sleep);
        this.coffee.set(coffee);
        this.communication.set(communication);
        this.work.set(work);
        this.weekends.set(weekends);
        hasData.set(true);
        recalculate();
    }

    private void recalculate() {
        if (!hasData.get()) {
            return;
        }


        double index = sleep.get()
                + communication.get()
                + weekends.get()
                - coffee.get()
                - work.get();

        happinessIndex.set(index);

        if (sleep.get() < 6) {
            recommendation.set("Поспать");
        } else if (work.get() > 40) {
            recommendation.set("Уволиться");
        } else {
            recommendation.set("Купить пиццу");
        }
    }

    public DoubleProperty sleepProperty() {
        return sleep;
    }

    public DoubleProperty coffeeProperty() {
        return coffee;
    }

    public DoubleProperty communicationProperty() {
        return communication;
    }

    public DoubleProperty workProperty() {
        return work;
    }

    public DoubleProperty weekendsProperty() {
        return weekends;
    }

    public ReadOnlyDoubleProperty happinessIndexProperty() {
        return happinessIndex;
    }

    public ReadOnlyStringProperty recommendationProperty() {
        return recommendation;
    }

    public ReadOnlyObjectProperty<Boolean> hasDataProperty() {
        return hasData;
    }

    public double getSleep() {
        return sleep.get();
    }

    public double getCoffee() {
        return coffee.get();
    }

    public double getCommunication() {
        return communication.get();
    }

    public double getWork() {
        return work.get();
    }

    public double getWeekends() {
        return weekends.get();
    }

    public double getHappinessIndex() {
        return happinessIndex.get();
    }

    public String getRecommendation() {
        return recommendation.get();
    }

    public boolean hasData() {
        return hasData.get();
    }
}
