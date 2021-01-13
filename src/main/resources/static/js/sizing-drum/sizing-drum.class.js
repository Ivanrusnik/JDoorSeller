let indentGlass = 20;

class SizingDrum {

    static init(){
        $(".select_size").on("click", SizingDrum.click);
    }
    static click(){

    }

    static sizeLimMin(elemId) {

        let nameField = SizingDrum.getFieldNameFromInputId(elemId);

        if (SizingDrum.checkGlassName(nameField)){
            return SizingDrum.sizeLimMinGlass(nameField);
        }

        var tab = RestrictionOfSelectionFields[nameField];

        var tabMin = tab[0].startRestriction;

        for (var i = 0; i < tab.length; i++) {
            if (tab[i].pairOfValues == 0) {
                if (tabMin > tab[i].startRestriction) {
                    tabMin = tab[i].startRestriction;
                }
                } else {
                    tabMin = tab[i].startRestriction;
            }
        }
        //alert(tabMin);
        return tabMin;
    }

    static sizeLimMax(elemId) {

        let nameField = SizingDrum.getFieldNameFromInputId(elemId);

        if (SizingDrum.checkGlassName(nameField)){
            return SizingDrum.sizeLimMaxGlass(nameField);
        }

        var tab = RestrictionOfSelectionFields[nameField];

        var tabMax = tab[0].startRestriction;

        for (var i = 0; i < tab.length; i++) {
            if (tab[i].pairOfValues == 0) {
                if (tabMax < tab[i].startRestriction) {
                    tabMax = tab[i].startRestriction;
                }
            }else {
                    tabMax = tab[i].stopRestriction;
               }
            }

        //alert(tabMax);
        return tabMax;
    }

    static sizeLimMinGlass(nameField){
        if (nameField == "glassWidth"){
            return 300;
        }
        if (nameField ==  "glassHeight"){
            return 600;
        }
        if (nameField ==  "leftGlassPosition"){
            return 0;
        }
        if (nameField ==  "bottomGlassPosition"){
            return 0;
        }
    }

    static sizeLimMaxGlass(nameField){
        if (nameField == "glassWidth"){
            return door.widthDoor - door.doorGlass.leftGlassPosition - indentGlass;
        }
        if (nameField ==  "glassHeight"){
            return door.heightDoor - door.doorGlass.bottomGlassPosition- indentGlass;
        }
        if (nameField ==  "leftGlassPosition"){
            return door.widthDoor - door.doorGlass.glassWidth - indentGlass;
        }
        if (nameField ==  "bottomGlassPosition"){
            return door.heightDoor - door.doorGlass.glassHeight- indentGlass;
        }
    }

    static checkGlassName(fieldName){
        if (fieldName == "glassWidth" ||
            fieldName ==  "glassHeight" ||
            fieldName ==  "leftGlassPosition" ||
            fieldName ==  "bottomGlassPosition"){
            return true;
        } else false;
    }

    static setSize() {
        if ($("#select_set").hasClass("notAvailable")) {
            return;
        }

        var number = SizingDrum.prepareTheNumber(
            $(".counter_line.numberL.line").text(),
            $(".counter_line.numberR.line").text()
        );

        var fieldName =  SizingDrum.getFieldNameFromInputId($("#nameSelectForm").attr("data"));

        if (SizingDrum.checkGlassName(fieldName)){
            Door.setGlass(fieldName,number);
            Door.draw(door, 1);
        }else {
            Door.set(fieldName,number);
            Door.draw(door, 1);
        }

        currentItem = fieldName;
        RepresentationManager.showFieldValue(number);

        closeSelect();
        selectSizeOpen = false;
    }

    static getFieldNameFromInputId(elemId){

        return elemId.replace('input_', '');
    }

    static prepareTheNumber(number1, number2) {
        if (number2.length == 1) {
            number2 = "0" + number2;
        }
        return "" + number1 + number2;
    }
}
