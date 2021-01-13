let validateProblemBlok = "";

class Validate {

    static default(template) {
        validateProblemBlok = "";
        if (validateDefault("metal") |
            validateDefault('widthDoor') |
            validateDefault('heightDoor') |
            validateDefault('colors') |
            validateDefault('deepnessDoor') |
            validateDefault('thicknessDoorLeaf') |
            validateDefault('leftDoorTrim') |
            validateDefault('leftDoorTrimSize') |
            validateDefault('rightDoorTrim') |
            validateDefault('rightDoorTrimSize') |
            validateDefault('topDoorTrim') |
            validateDefault('topDoorTrimSize')) {
            alert(validateProblemBlok);
            return true;
        } else {
            return false;
        }
    }

    static colorContainDesign(template) {
        let valueList = template["shieldColor"];
        for (let i = 0; i < valueList.length; i++) {
            if (valueList[i].defaultValue > 0) {
                if (valueList[i].secondItem == "1") {
                    if (isDefault("shieldDesign")) {
                        validateProblemBlok += "shieldDesign" + ' - уже содержит дизайн: ' +
                            'выбирете цвет накладки без дизайна либо уберите дизайн по умолчанию\n';
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

function isDefault(fieldName) {
    let valueList = template[fieldName];
    for (let i = 0; i < valueList.length; i++) {
        if (valueList[i].defaultValue > 0) {
            return true;
        }
    }
    return false;
}

function validateDefault(fieldName) {
    if (!isDefault(fieldName)){
        validateProblemBlok += fieldName + ' - не установлен по умолчанию\n';
        return true;
    }
    return false;
}


