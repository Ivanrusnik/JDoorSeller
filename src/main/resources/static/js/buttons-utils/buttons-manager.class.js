class ButtonsManager {
    constructor(buttonsClass1, valueToLock1, buttonsClass2, valueToLock2) {
        this.buttonsClass1 = buttonsClass1;
        this.valueToLock1 = valueToLock1;
        this.buttonsClass2 = buttonsClass2;
        this.valueToLock2 = valueToLock2;
    }

    turnOff(valueForComparison) {
        if (this.valueToLock1 != valueForComparison) {
            $('.' + this.buttonsClass1).each(function (index) {
                $(this).attr('disabled', '');
            });
        }
    }

    turnOn (valueForComparison){
        if (this.valueToLock2 == valueForComparison){
            $('.' + this.buttonsClass2).each(function (index) {
                $(this).removeAttr("disabled");
            });
        } else {
            this.turnOff(valueForComparison);
        }
    }

}