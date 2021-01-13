class RepresentationManager {

    static showAllFieldsValues(door) {
        for (let key in door) {
            currentItem = key;
            RepresentationManager.showFieldValue(door[key]);
        }
        Door.draw(door, 1);
    }

    static showFieldValue(val) {
        let buildVal = Container2fields.buildValue(val);
        Container2fields.setValueToField(buildVal);
        Container3fields.setSizeValueToButton(currentItem);
    }
}

class Container2fields {
    static buildValue(value) {
        if (!failureToSetValue) {
            var showValue = "";

            //widthDoor
            if (currentItem == "widthDoor") {
                if (door.activeDoorLeafWidth != 0) {
                    showValue =
                        "" + door.widthDoor + " [" + door.activeDoorLeafWidth + "]";
                } else if (door.widthDoor && door.widthDoor != 0) {
                    showValue = "" + door.widthDoor;
                }
            }
            //heightDoor
            else if (currentItem == "heightDoor") {
                if (door.doorFanlightHeight != 0) {
                    showValue =
                        "" + door.heightDoor + " [" + door.doorFanlightHeight + "]";
                } else if (door.heightDoor && door.heightDoor != 0) {
                    showValue = "" + door.heightDoor;
                }
            }
            //sideDoorOpen
            else if (currentItem == "sideDoorOpen") {
                if (door.innerOpen && door.innerOpen != 0) {
                    showValue = "" + door.sideDoorOpen + " [внутренняя # " + door.innerOpen + "]";
                } else if (door.sideDoorOpen && door.sideDoorOpen != 0) {
                    showValue = "" + door.sideDoorOpen;
                }
            }
            //doorClass
            else if (currentItem == "doorType") {
                showValue = Container2fields.getTheNameOfTheDoorType(value);
                currentItem = "doorClass";
            }
            //doorGlass
            else if (currentItem == "doorGlass") {
                showValue = Container2fields.getNameGlass(value);
            }
            //furnitureKit
            else if (currentItem == "furnitureKit") {
                currentItem = "topLockkit";
                Container2fields.setValueToField(Container2fields.getFurniture(value, "topLock"));

                currentItem = "lowerLockkit";
                Container2fields.setValueToField(Container2fields.getFurniture(value, "lowerLock"));

                currentItem = "handle";
                showValue = Container2fields.getFurniture(value, "handle");
            }
            //shieldKit
            else if (currentItem == "shieldKit") {
                if (door.shieldKit != null) {
                    if (door.shieldKit.shieldColor != null) {
                        showValue = door.shieldKit.shieldColor.name;
                    }
                    if (door.shieldKit.shieldDesign != null) {
                        showValue = showValue + " / " + door.shieldKit.shieldDesign.name;
                    }
                }
            } else if (currentItem == "comment") {
                if (value != null && value.length > 20) {
                    showValue = value.slice(0, 20) + "..";
                }
            }
            //doorstep//DoorTrim
            else if (
                currentItem == "doorstep" ||
                currentItem == "stainlessSteelDoorstep"
            ) {
                Container2fields.fillCheckbox(currentItem, value);
            } else if (
                currentItem == "topDoorTrim" ||
                currentItem == "leftDoorTrim" ||
                currentItem == "rightDoorTrim"
            ) {
                Trim.turnOn(currentItem, value);
            } else if (
                currentItem == "firstSealingLine" ||
                currentItem == "secondSealingLine" ||
                currentItem == "thirdSealingLine"
            ) {
                Container2fields.fillCheckbox(currentItem + "_" + value, 1);
            } else {
                if (value != 0 || value != "0") {
                    showValue = value;
                }
            }

            return showValue;
        }
    }

    static setValueToField(value) {
        $(".vertical_menu_button_rigtht#" + currentItem + "Show strong").html(
            value
        );
    }

    static getTheNameOfTheDoorType(value) {
        if (value instanceof Object) {
            return value.name;
        } else {
            for (var i = 0; i < door.availableDoorClass.length; ++i) {
                var type = door.availableDoorClass[i].doorTypes;
                for (var j = 0; j < type.length; ++j) {
                    if (type[j].id === value) {
                        return door.availableDoorClass[i].name;
                    }
                }
            }
        }
        return "";
    }

    static getNameGlass(value) {
        if (value !== null) {
            if (
                typeof value == "object" &&
                value.glassWidth > 0 &&
                value.glassHeight > 0
            ) {
                return "" + value.glassWidth + " X " + value.glassHeight;
            }
        }
        return "";
    }

    static getFurniture(value, name) {
        if (value != null && value[name] !== null) {
            return value[name].name;
        }
        return "";
    }

    static fillCheckbox(name, value) {
        if (value == 1) {
            $("#" + name + "_checkbox").prop("checked", true);
        } else {
            $("#" + name + "_checkbox").prop("checked", false);
        }
    }
}

class Container3fields {
    static setSizeValueToButton(fieldName) {
        let value;
        if (fieldName == "glassWidth" || fieldName ==  "glassHeight" || fieldName ==  "leftGlassPosition" || fieldName ==  "bottomGlassPosition") {
            value = door.doorGlass[fieldName];
        } else {
            value = door[fieldName];
        }
        let item = $('#input_' + fieldName);
        let buttonText = $(item).attr('name');
        $(item).text(buttonText + ' ' + value);
        $(item).attr('data', value);
        $(item).attr('value', value);
    }
}