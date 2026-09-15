package HappinessCalculator {

    part def HappinessModel {
        attribute sleep : Real;
        attribute coffee : Real;
        attribute communication : Real;
        attribute work : Real;
        attribute weekends : Real;
        attribute happinessIndex : Real;
        attribute recommendation : String;
    }

    part def HappinessController {
    }

    part def MainView {
    }

    part def InputView {
    }

    part happinessModel : HappinessModel;
    part happinessController : HappinessController;
    part mainView : MainView;
    part inputView : InputView;
}
