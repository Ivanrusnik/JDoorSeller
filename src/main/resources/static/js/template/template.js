let doorClassList;
let template;
let restriction;

jQuery("document").ready(function () {

    //new instans
    grtListDoorClassToSelect();

    //new Template instans
    function getDoorTemplate(id) {
        $.ajax({
            url: "item/" + id,
            dataType: "json",
            success: function (data) {
                template = data.template;
                restriction = data.restriction;

                installFromTemplatAfterSelectingType();
                fillInAlLSelectAfterSelectingType();
            },
            error: function (data) {
                alert("!ERROR: данные шаблона получить не удалось:");
            },
        });
    }

    function clearFromBlock(selector, chekbox) {
        $(selector).empty();
        $(chekbox).prop("checked", false);

        let elem = $(selector);

        for (let i = 1; i < elem.length; i++) {
            $(elem[i]).parent().parent().remove();
        }
    }

    function clearFromInput(selector) {
        $(selector).val("");
    }

    function clearFromTemplateAfterSelectingType() {
        $(".designSelect").empty();

        clearFromBlock(".metalSelect", ".metalLineCheckbox");

        clearFromInput("#widthDoorMin");
        clearFromInput("#widthDoorMax");
        clearFromInput("#widthDoorStep");
        clearFromInput("#widthDoorDefault");
        clearFromInput("#widthDoorLeafMin");
        clearFromInput("#widthDoorLeafMax");
        clearFromInput("#widthDoorLeafStep");
        clearFromInput("#widthDoorLeafDefault");
        clearFromInput("#heightDoorMin");
        clearFromInput("#heightDoorMax");
        clearFromInput("#heightDoorStep");
        clearFromInput("#heightDoorDefault");
        clearFromInput("#costOfChangeWidth");
        clearFromInput("#sizeOfTorificationWidth");
        clearFromInput("#costSizeOfTorificationWidth");
        clearFromInput("#costOfChangeHeight");
        clearFromInput("#sizeOfTorificationHeight");
        clearFromInput("#costSizeOfTorificationHeight");

        clearFromBlock(".deepnessDoorInput", ".deepnessDoorLineCheckbox");
        clearFromInput("#deepnessDoorInput0");

        clearFromBlock(".thicknessDoorLeafInput", ".thicknessDoorLeafLineCheckbox");
        clearFromInput("#thicknessDoorLeafInput0");

        clearFromBlock(".colorsSelect", ".colorsLineCheckbox");
        clearFromBlock(".shieldColorSelect", ".shieldColorLineCheckbox");
        clearFromBlock(".shieldDesignSelect", ".shieldDesignLineCheckbox");
        clearFromBlock(".topLockSelect", ".topLockLineCheckbox");
        clearFromBlock(".lowerLockSelect", ".lowerLockLineCheckbox");
        clearFromBlock(".lockCylinderSelect", ".lockCylinderLineCheckbox");
        clearFromBlock(".handleSelect", ".handleLineCheckbox");
        clearFromBlock(".topInLockDecorSelect", ".topInLockDecorLineCheckbox");
        clearFromBlock(".topOutLockDecorSelect", ".topOutLockDecorLineCheckbox");
        clearFromBlock(".lowerInLockDecorSelect", ".lowerInLockDecorLineCheckbox");
        clearFromBlock(".lowerOutLockDecorSelect", ".lowerOutLockDecorLineCheckbox");
        clearFromBlock(".leftDoorTrimSelect", ".leftDoorTrimLineCheckbox");
        clearFromBlock(".rightDoorTrimSelect", ".rightDoorTrimLineCheckbox");
        clearFromBlock(".topDoorTrimSelect", ".topDoorTrimLineCheckbox");
        clearFromBlock(".doorstepSelect", ".doorstepLineCheckbox");
        clearFromBlock(".stainlessSteelDoorstepSelect", ".stainlessSteelDoorstepLineCheckbox");
        clearFromBlock(".firstSealingLineSelect", ".firstSealingLineLineCheckbox");
        clearFromBlock(".secondSealingLineSelect", ".secondSealingLineLineCheckbox");
        clearFromBlock(".thirdSealingLineSelect", ".thirdSealingLineLineCheckbox");
        clearFromBlock(".closerSelect", ".closerLineCheckbox");
        clearFromBlock(".endDoorLockSelect", ".endDoorLockLineCheckbox");
        clearFromBlock(".typeDoorGlassSelect", ".typeDoorGlassLineCheckbox");
        clearFromBlock(".toningSelect", ".toningLineCheckbox");
        clearFromBlock(".armorSelect", ".armorLineCheckbox");
    }

    function installFromTemplatAfterSelectingType() {
        installFromTemplateSize("widthDoor", true);
        installFromTemplateSize("heightDoor", true);

        installFromTemplateSize("widthDoorLeaf", true);
        installFromTemplateSize("heightDoorFanlight", true);

        installFromTemplateSize("deepnessDoor", false);
        installFromTemplateSize("thicknessDoorLeaf", false);

        installFromTemplateMetal();
        installFromTemplateColor(template.colors, "colors");
        installFromTemplateDesign(template.design, "design");

        installFromTemplateSelect("doorstep");
        installFromTemplateSelect("stainlessSteelDoorstep");

        installFromTemplateSealingLine("firstSealingLine");
        installFromTemplateSealingLine("secondSealingLine");
        installFromTemplateSealingLine("thirdSealingLine");

        installFromTemplateSelect("leftDoorTrim");
        installFromTemplateSelect("rightDoorTrim");
        installFromTemplateSelect("topDoorTrim");

        installFromTemplateSize("leftDoorTrimSize", true);
        installFromTemplateSize("rightDoorTrimSize", true);
        installFromTemplateSize("topDoorTrimSize", true);

        installFromTemplateFurnitur("topLock");
        installFromTemplateFurnitur("lowerLock");
        installFromTemplateFurnitur("handle");
        installFromTemplateFurnitur("toplockCylinder");
        installFromTemplateFurnitur("lockCylinder");
        installFromTemplateFurnitur("topInLockDecor");
        installFromTemplateFurnitur("topOutLockDecor");
        installFromTemplateFurnitur("lowerInLockDecor");
        installFromTemplateFurnitur("lowerOutLockDecor");

        installFromTemplateFurnitur("closer");
        installFromTemplateFurnitur("endDoorLock");

        installFromTemplateFurnitur("typeDoorGlass");
        installFromTemplateFurnitur("toning");
        installFromTemplateFurnitur("armor");

        installFromTemplateFurnitur("shieldColor");
        installFromTemplateFurnitur("shieldDesign");

        SizeCostBlock.setValueToDom();

    }

    function fillInAlLSelectAfterSelectingType() {
        fillInMetal();
        fillInColor("colors", restriction.colors);
        fillInDesign("design", restriction.design);

        fillInSelector(".doorstepSelect", "doorstep");
        fillInSelector(".stainlessSteelDoorstepSelect", "stainlessSteelDoorstep");

        fillInSealingLine("firstSealingLine");
        fillInSealingLine("secondSealingLine");
        fillInSealingLine("thirdSealingLine");

        fillInSelector(".topDoorTrimSelect", "topDoorTrim");
        fillInSelector(".leftDoorTrimSelect", "leftDoorTrim");
        fillInSelector(".rightDoorTrimSelect", "rightDoorTrim");

        fillInFurniture("topLock");
        fillInFurniture("lowerLock");
        fillInFurniture("lockCylinder");

        fillInFurniture("handle");

        fillInFurniture("topInLockDecor");
        fillInFurniture("topOutLockDecor");
        fillInFurniture("lowerInLockDecor");
        fillInFurniture("lowerOutLockDecor");
        fillInFurniture("closer");
        fillInFurniture("endDoorLock");

        fillInFurniture("typeDoorGlass");
        fillInFurniture("toning");
        fillInFurniture("armor");

        fillInFurniture("shieldColor");
        fillInFurniture("shieldDesign");
    }

    $("#doorclassselect").change(function () {
        let ClassId = getDoorClassbyId($("#doorclassselect").val());
        fillOptionsInSelector(ClassId, "#doortypeselect");
        fillOptionsInSelector(ClassId, "#fillFromTemplate");
    });

    $("#costOfChangeWidth").change(function () {
        $('#costOfChangeHeight').val($('#costOfChangeWidth').val());
    });

    $("#doortypeselect").change(function () {
        getDoorTemplate(getIdFromUrl());
    });

    $("#metalDiv").change(".metalSelect", function () {
        saveInJavaObjectforRestrictionValue("metal");
        if (allFieldsAreFilled(".metalSelect")) {
            addMetalField();
        }
        fillInMetal();
    });
    $("#metalDiv").on("click", ".metalLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("metal");
            $(this).prop("checked", true);
            saveInJavaObjectforRestrictionValue("metal");
        } else {
            saveInJavaObjectforRestrictionValue("metal");
        }
    });

    // main size

    $("#widthDoorDiv").change(".widthDoorInput", function () {
        saveInJavaObjectSize("widthDoor");
        if (allFieldsAreFilled(".widthDoorInput")) {
            //addInputField('widthDoorInput', 'widthDoorDiv');

            addField("widthDoor", "input", "Input");
        }
    });
    $("#widthDoorDiv").on("click", ".widthDoorLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("widthDoor");
            $(this).prop("checked", true);
            saveInJavaObjectSize("widthDoor");
        } else {
            saveInJavaObjectSize("widthDoor");
        }
    });
    $("#widthSizMaxMinDiv").change(function () {
        saveInJavaObjectSize("widthDoor");
    });

    $("#heightDoorDiv").change(".heightDoorInput", function () {
        saveInJavaObjectSize("heightDoor");
        if (allFieldsAreFilled(".heightDoorInput")) {
            addInputField("heightDoorInput", "heightDoorDiv");
        }
    });
    $("#heightDoorDiv").on("click", ".heightDoorLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("heightDoor");
            $(this).prop("checked", true);
            saveInJavaObjectSize("heightDoor");
        } else {
            saveInJavaObjectSize("heightDoor");
        }
    });
    $("#heightSizMaxMinDiv").change(function () {
        saveInJavaObjectSize("heightDoor");
    });

    SizeCostBlock.init();

    // subordinateSize

    $("#widthLeafSizMaxMinDiv").change(function () {
        saveInJavaObjectSize("widthDoorLeaf");
    });
    $("#heightFanlightSizMaxMinDiv").change(function () {
        saveInJavaObjectSize("heightDoorFanlight");
    });

    $("#deepnessDoorDiv").change(".deepnessDoorInput", function () {
        saveInJavaObjectListValues("deepnessDoor");
        if (allFieldsAreFilled(".deepnessDoorInput")) {
            //addInputField('deepnessDoorInput', 'deepnessDoorDiv');
            addField("deepnessDoor", "input", "Input");
        }
    });
    $("#deepnessDoorDiv").on("click", ".deepnessDoorLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("deepnessDoor");
            $(this).prop("checked", true);
            saveInJavaObjectListValues("deepnessDoor");
        } else {
            saveInJavaObjectListValues("deepnessDoor");
        }
    });
    $("#thicknessDoorLeafDiv").change(".thicknessDoorLeafInput", function () {
        saveInJavaObjectListValues("thicknessDoorLeaf");
        if (allFieldsAreFilled(".thicknessDoorLeafInput")) {
            //addInputField('thicknessDoorLeafInput', 'thicknessDoorLeafDiv');
            addField("thicknessDoorLeaf", "input", "Input");
        }
    });
    $("#thicknessDoorLeafDiv").on(
        "click",
        ".thicknessDoorLeafLineCheckbox",
        function () {
            if ($(this).is(":checked")) {
                switchOffAll("thicknessDoorLeaf");
                $(this).prop("checked", true);
                saveInJavaObjectListValues("thicknessDoorLeaf");
            } else {
                saveInJavaObjectListValues("thicknessDoorLeaf");
            }
        }
    );

    $("#colorsDiv").change(".colorsSelect", function () {
        if (checkForAllSelect("colors")) {
            deleteFields("colors");
            installFromTemplateColor(restriction.colors, "colors");
        } else {
            saveInJavaObjectColorAndFurnitur("colors");
            if (allFieldsAreFilled(".colorsSelect")) {
                //addSelectField('colors');
                addField("colors", "select", "Select");
            }
            fillInColor("colors", restriction.colors);
        }
    });

    $("#colorsDiv").on("click", ".colorsLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("colors");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("colors");
        } else {
            saveInJavaObjectColorAndFurnitur("colors");
        }
    });

    $("#DesignDiv").change(".designSelect", function () {
        saveInJavaObjectColorAndFurnitur("design");
        fillInDesign("design", restriction.design);
    });

    $("#shieldColorDiv").change(".shieldColorSelect", function () {
        if (checkForAllSelect("shieldColor")) {
            deleteFields("shieldColor");
            installFromTemplateColor(restriction.shieldColor, "shieldColor");
        } else {
            saveInJavaObjectColorAndFurnitur("shieldColor");
            if (allFieldsAreFilled(".shieldColorSelect")) {
                //addSelectField('colors');
                addField("shieldColor", "select", "Select");
            }
            fillInFurniture("shieldColor");
        }
    });
    $("#shieldColorDiv").on("click", ".shieldColorLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("shieldColor");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("shieldColor");
        } else {
            saveInJavaObjectColorAndFurnitur("shieldColor");
        }
    });

    $("#shieldDesignDiv").change(".shieldDesignSelect", function () {
        if (checkForAllSelect("shieldDesign")) {
            deleteFields("shieldDesign");
            installFromTemplateColor(restriction.shieldDesign, "shieldDesign");
        } else {
            saveInJavaObjectColorAndFurnitur("shieldDesign");
            if (allFieldsAreFilled(".shieldDesignSelect")) {
                //addSelectField('colors');
                addField("shieldDesign", "select", "Select");
            }
            fillInFurniture("shieldDesign");
        }
    });
    $("#shieldDesignDiv").on("click", ".shieldDesignLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("shieldDesign");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("shieldDesign");
        } else {
            saveInJavaObjectColorAndFurnitur("shieldDesign");
        }
    });

    $("#doorstepDiv").change(".doorstepSelect", function () {
        saveInJavaObjectforRestrictionValue("doorstep");
        if (allFieldsAreFilled(".doorstepSelect")) {
            addField("doorstep", "select", "Select");
        }
        fillInSelector(".doorstepSelect", "doorstep");
    });
    $("#doorstepDiv").on("click", ".doorstepLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("doorstep");
            $(this).prop("checked", true);
            saveInJavaObjectforRestrictionValue("doorstep");
        } else {
            saveInJavaObjectforRestrictionValue("doorstep");
        }
    });

    $("#stainlessSteelDoorstepDiv").change(
        ".stainlessSteelDoorstepSelect",
        function () {
            saveInJavaObjectforRestrictionValue("stainlessSteelDoorstep");
            if (allFieldsAreFilled(".stainlessSteelDoorstepSelect")) {
                addField("stainlessSteelDoorstep", "select", "Select");
            }
            fillInSelector(".stainlessSteelDoorstepSelect", "stainlessSteelDoorstep");
        }
    );
    $("#stainlessSteelDoorstepDiv").on(
        "click",
        ".stainlessSteelDoorstepLineCheckbox",
        function () {
            if ($(this).is(":checked")) {
                switchOffAll("stainlessSteelDoorstep");
                $(this).prop("checked", true);
                saveInJavaObjectforRestrictionValue("stainlessSteelDoorstep");
            } else {
                saveInJavaObjectforRestrictionValue("stainlessSteelDoorstep");
            }
        }
    );

    $("#firstSealingLineDiv").change(".firstSealingLineSelect", function () {
        addNewFieldAndfillInforSealingLine("firstSealingLine");
    });
    $("#firstSealingLineDiv").on(
        "click",
        ".firstSealingLineLineCheckbox",
        function () {
            if ($(this).is(":checked")) {
                switchOffAll("firstSealingLine");
                $(this).prop("checked", true);
                saveInJavaObjectforRestrictionValue("firstSealingLine");
            } else {
                saveInJavaObjectforRestrictionValue("firstSealingLine");
            }
        }
    );

    $("#secondSealingLineDiv").change(".secondSealingLineSelect", function () {
        addNewFieldAndfillInforSealingLine("secondSealingLine");
    });
    $("#secondSealingLineDiv").on(
        "click",
        ".secondSealingLineLineCheckbox",
        function () {
            if ($(this).is(":checked")) {
                switchOffAll("secondSealingLine");
                $(this).prop("checked", true);
                saveInJavaObjectforRestrictionValue("secondSealingLine");
            } else {
                saveInJavaObjectforRestrictionValue("secondSealingLine");
            }
        }
    );

    $("#thirdSealingLineDiv").change(".thirdSealingLineSelect", function () {
        addNewFieldAndfillInforSealingLine("thirdSealingLine");
    });
    $("#thirdSealingLineDiv").on(
        "click",
        ".thirdSealingLineLineCheckbox",
        function () {
            if ($(this).is(":checked")) {
                switchOffAll("thirdSealingLine");
                $(this).prop("checked", true);
                saveInJavaObjectforRestrictionValue("thirdSealingLine");
            } else {
                saveInJavaObjectforRestrictionValue("thirdSealingLine");
            }
        }
    );

    $("#topDoorTrimSizeMaxMinDiv").change(function () {
        saveInJavaObjectSize("topDoorTrimSize");
    });
    $("#topDoorTrimDiv").change(".topDoorTrimSelect", function () {
        saveInJavaObjectforRestrictionValue("topDoorTrim");
        if (allFieldsAreFilled(".topDoorTrimSelect")) {
            addField("topDoorTrim", "select", "Select");
        }
        fillInSelector(".topDoorTrimSelect", "topDoorTrim");
    });
    $("#topDoorTrimDiv").on("click", ".topDoorTrimLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("topDoorTrim");
            $(this).prop("checked", true);
            saveInJavaObjectforRestrictionValue("topDoorTrim");
        } else {
            saveInJavaObjectforRestrictionValue("topDoorTrim");
        }
    });

    $("#leftDoorTrimSizeMaxMinDiv").change(function () {
        saveInJavaObjectSize("leftDoorTrimSize");
    });
    $("#leftDoorTrimDiv").change(".leftDoorTrimSelect", function () {
        saveInJavaObjectforRestrictionValue("leftDoorTrim");
        if (allFieldsAreFilled(".leftDoorTrimSelect")) {
            addField("leftDoorTrim", "select", "Select");
        }
        fillInSelector(".leftDoorTrimSelect", "leftDoorTrim");
    });
    $("#leftDoorTrimDiv").on("click", ".leftDoorTrimCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("leftDoorTrim");
            $(this).prop("checked", true);
            saveInJavaObjectforRestrictionValue("leftDoorTrim");
        } else {
            saveInJavaObjectforRestrictionValue("leftDoorTrim");
        }
    });

    $("#rightDoorTrimSizeMaxMinDiv").change(function () {
        saveInJavaObjectSize("rightDoorTrimSize");
    });
    $("#rightDoorTrimDiv").change(".rightDoorTrimSelect", function () {
        saveInJavaObjectforRestrictionValue("rightDoorTrim");
        if (allFieldsAreFilled(".rightDoorTrimSelect")) {
            addField("rightDoorTrim", "select", "Select");
        }
        fillInSelector(".rightDoorTrimSelect", "rightDoorTrim");
    });
    $("#rightDoorTrimDiv").on("click", ".rightDoorTrimLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("rightDoorTrim");
            $(this).prop("checked", true);
            saveInJavaObjectforRestrictionValue("rightDoorTrim");
        } else {
            saveInJavaObjectforRestrictionValue("rightDoorTrim");
        }
    });

    //furnitur

    $("#topLockDiv").change(".topLockSelect", function () {
        addNewFieldAndfillInforFurnitur("topLock"); //nameJavaObject
    });
    $("#topLockDiv").on("click", ".topLockLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("topLock");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("topLock");
        } else {
            saveInJavaObjectColorAndFurnitur("topLock");
        }
    });
    $("#lowerLockDiv").change(".lowerLockSelect", function () {
        addNewFieldAndfillInforFurnitur("lowerLock"); //nameJavaObject
    });
    $("#lowerLockDiv").on("click", ".lowerLockLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("lowerLock");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("lowerLock");
        } else {
            saveInJavaObjectColorAndFurnitur("lowerLock");
        }
    });

    $("#handleDiv").change(".handleSelect", function () {
        addNewFieldAndfillInforFurnitur("handle"); //nameJavaObject
    });
    $("#handleDiv").on("click", ".handleLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("handle");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("handle");
        } else {
            saveInJavaObjectColorAndFurnitur("handle");
        }
    });

    $("#lockCylinderDiv").change(".lockCylinderSelect", function () {
        addNewFieldAndfillInforFurnitur("lockCylinder"); //nameJavaObject
    });
    $("#lockCylinderDiv").on("click", ".lockCylinderLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("lockCylinder");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("lockCylinder");
        } else {
            saveInJavaObjectColorAndFurnitur("lockCylinder");
        }
    });

    $("#topInLockDecorDiv").change(".topInLockDecorSelect", function () {
        addNewFieldAndfillInforFurnitur("topInLockDecor"); //nameJavaObject
    });
    $("#topInLockDecorDiv").on(
        "click",
        ".topInLockDecorLineCheckbox",
        function () {
            if ($(this).is(":checked")) {
                switchOffAll("topInLockDecor");
                $(this).prop("checked", true);
                saveInJavaObjectColorAndFurnitur("topInLockDecor");
            } else {
                saveInJavaObjectColorAndFurnitur("topInLockDecor");
            }
        }
    );
    $("#topOutLockDecorDiv").change(".topOutLockDecorSelect", function () {
        addNewFieldAndfillInforFurnitur("topOutLockDecor"); //nameJavaObject
    });
    $("#topOutLockDecorDiv").on(
        "click",
        ".topOutLockDecorLineCheckbox",
        function () {
            if ($(this).is(":checked")) {
                switchOffAll("topOutLockDecor");
                $(this).prop("checked", true);
                saveInJavaObjectColorAndFurnitur("topOutLockDecor");
            } else {
                saveInJavaObjectColorAndFurnitur("topOutLockDecor");
            }
        }
    );
    $("#lowerInLockDecorDiv").change(".lowerInLockDecorSelect", function () {
        addNewFieldAndfillInforFurnitur("lowerInLockDecor"); //nameJavaObject
    });
    $("#lowerInLockDecorDiv").on(
        "click",
        ".lowerInLockDecorLineCheckbox",
        function () {
            if ($(this).is(":checked")) {
                switchOffAll("lowerInLockDecor");
                $(this).prop("checked", true);
                saveInJavaObjectColorAndFurnitur("lowerInLockDecor");
            } else {
                saveInJavaObjectColorAndFurnitur("lowerInLockDecor");
            }
        }
    );
    $("#lowerOutLockDecorDiv").change(".lowerOutLockDecorSelect", function () {
        addNewFieldAndfillInforFurnitur("lowerOutLockDecor");
    });
    $("#lowerOutLockDecorDiv").on(
        "click",
        ".lowerOutLockDecorLineCheckbox",
        function () {
            if ($(this).is(":checked")) {
                switchOffAll("lowerOutLockDecor");
                $(this).prop("checked", true);
                saveInJavaObjectColorAndFurnitur("lowerOutLockDecor");
            } else {
                saveInJavaObjectColorAndFurnitur("lowerOutLockDecor");
            }
        }
    );

    //additionally furnitur

    $("#closerDiv").change(".closerSelect", function () {
        addNewFieldAndfillInforFurnitur("closer"); //nameJavaObject
    });
    $("#closerDiv").on("click", ".closerLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("closer");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("closer");
        } else {
            saveInJavaObjectColorAndFurnitur("closer");
        }
    });
    $("#endDoorLockDiv").change(".endDoorLockSelect", function () {
        addNewFieldAndfillInforFurnitur("endDoorLock"); //nameJavaObject
    });
    $("#endDoorLockDiv").on("click", ".endDoorLockLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("endDoorLock");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("endDoorLock");
        } else {
            saveInJavaObjectColorAndFurnitur("endDoorLock");
        }
    });

    //glass

    $("#typeDoorGlassDiv").change(".typeDoorGlassSelect", function () {
        addNewFieldAndfillInforFurnitur("typeDoorGlass"); //nameJavaObject
    });
    $("#typeDoorGlassDiv").on("click", ".typeDoorGlassLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("typeDoorGlass");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("typeDoorGlass");
        } else {
            saveInJavaObjectColorAndFurnitur("typeDoorGlass");
        }
    });
    $("#toningDiv").change(".toningSelect", function () {
        addNewFieldAndfillInforFurnitur("toning"); //nameJavaObject
    });
    $("#toningDiv").on("click", ".toningLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("toning");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("toning");
        } else {
            saveInJavaObjectColorAndFurnitur("toning");
        }
    });
    $("#armorDiv").change(".armorSelect", function () {
        addNewFieldAndfillInforFurnitur("armor"); //nameJavaObject
    });
    $("#armorDiv").on("click", ".armorLineCheckbox", function () {
        if ($(this).is(":checked")) {
            switchOffAll("armor");
            $(this).prop("checked", true);
            saveInJavaObjectColorAndFurnitur("armor");
        } else {
            saveInJavaObjectColorAndFurnitur("armor");
        }
    });

    $("#saveTemplate").on("click", function () {

        if (Validate.default(template)) {
            return;
        } else if (Validate.colorContainDesign(template)) {
            return;
        }

        let typeId = $("#doortypeselect").val();
        if (template.doorTypeid == 0 || template.doorTypeid != typeId) {
            template.doorTypeid = typeId;
        }
        var templateJSON = JSON.stringify(template);


        $.ajax({
            type: "POST",
            url: "item",
            contentType: "application/json",
            data: templateJSON,
            success: function (data) {
                toList();
            },
            error: function (data) {
                alert("error: объект сохранить не удалось!");
            },
        });
    });

    $("#close").on("click", function () {
        toList();
    });

    $("#fillFromTemplate").change(function () {
        fillFromTemplate();
    });

    $("#widthDoorCheckbox").on("click", function () {
        switchSize("widthDoor");
    });
    $("#heightDoorCheckbox").on("click", function () {
        switchSize("heightDoor");
    });
    $("#heightDoorFanlightCheckbox").on("click", function () {
        if ($(this).is(":checked")) {
            $("#heightDoorFanlightPeriod").removeClass("ghost");
        } else {
            $("#heightDoorFanlightPeriod").addClass("ghost");
            clearPeriod("heightDoorFanlight");
        }
    });

    function switchSize(nameJavaObject) {
        if ($("#" + nameJavaObject + "Checkbox").is(":checked")) {
            $("#" + nameJavaObject + "Period").addClass("ghost");
            $("#" + nameJavaObject + "Div").removeClass("ghost");
            clearPeriod(nameJavaObject);
        } else {
            $("#" + nameJavaObject + "Period").removeClass("ghost");
            $("#" + nameJavaObject + "Div").addClass("ghost");
            clearSize(nameJavaObject);
        }
    }

    function grtListDoorClassToSelect() {
        $.ajax({
            url: location.origin + "/class/list",
            dataType: "json",
            success: function (data) {
                doorClassList = data;
                fillInDoorClass(doorClassList);
                setCurrentClassById();
            },
            error: function (data) {
                alert("!ERROR: данные о классах получить не удалось:");
            },
        });
    }

    function fillInDoorClass(listClass) {
        $("#doorclassselect").empty();

        $("#doorclassselect").append($("<option></option>"));

        for (var i = 0; i < listClass.length; ++i) {
            $("#doorclassselect").append(
                $(
                    "<option value=" +
                    listClass[i].id +
                    ">" +
                    listClass[i].name +
                    "</option>"
                )
            );
        }
    }

    function fillOptionsInSelector(doorClass, selector) {
        $(selector).empty();

        $(selector).append($("<option></option>"));

        for (var i = 0; i < doorClass.doorTypes.length; ++i) {
            $(selector).append(
                $(
                    "<option value=" +
                    doorClass.doorTypes[i].id +
                    ">" +
                    doorClass.doorTypes[i].name +
                    "</option>"
                )
            );
        }
    }

    function getDoorClassbyId(id) {
        if (id == null && id == 0) {
            alert("error id is null!");
        }

        for (var i = 0; i < doorClassList.length; ++i) {
            if (doorClassList[i].id == id) {
                return doorClassList[i];
            }
        }
        alert("error doorClass no found!");
        return 0;
    }

    function installPairOfValues(nameJavaObject, min, max, defaultVal) {
        $("#" + nameJavaObject + "Min").val(min);
        $("#" + nameJavaObject + "Max").val(max);
        $("#" + nameJavaObject + "Default").val(defaultVal);
    }

    function installOneValue(nameJavaObject, val, defaultValue) {
        var selector = "." + nameJavaObject + "Input";
        if (allFieldsAreFilled(selector)) {
            //addSizeField(nameJavaObject);
            addField(nameJavaObject, "input", "Input");
        }
        elem = getNotCompletedFields(selector);
        $(elem).val(val);
        setSwitchDefaultVal(elem, nameJavaObject, defaultValue);

        if (allFieldsAreFilled(selector)) {
            //addSizeField(nameJavaObject);
            addField(nameJavaObject, "input", "Input");
        }
    }

    function installFromTemplateSize(nameJavaObject, twoInputOptions) {
        var table = template[nameJavaObject];
        var length = table.length;
        var check = false;

        for (var i = 0; i < length; ++i) {
            if (table[i].pairOfValues == 1) {
                installPairOfValues(
                    nameJavaObject,
                    table[i].startRestriction,
                    table[i].stopRestriction,
                    table[i].defaultValue
                );
            } else {
                check = true;
                installOneValue(
                    nameJavaObject,
                    table[i].startRestriction,
                    table[i].defaultValue
                );
            }
        }
        if (twoInputOptions && check) {
            $("#" + nameJavaObject + "Checkbox").prop("checked", true);
            switchSize(nameJavaObject);
        }
    }

    function installFromTemplateMetal() {
        var table = template.metal;
        var length = table.length;
        var selector = ".metalSelect";
        var elem = null;

        for (var i = 0; i < length; ++i) {
            if (allFieldsAreFilled(selector)) {
                addMetalField();
            }

            elem = getNotCompletedFields(selector);
            fillInFieldFromLimit("#" + $(elem).attr("id"), restriction.metal);
            setValueInSelect("#" + $(elem).attr("id"), table[i].firstItem);
            setSwitchDefaultVal(elem, "metal", table[i].defaultValue);

            if (allFieldsAreFilled(selector)) {
                addMetalField();
            }
        }
    }

    function installFromTemplateColor(table, javaName) {
        var length = table.length;
        var selector = "." + javaName + "Select";
        var elem = null;

        for (var i = 0; i < length; ++i) {
            if (allFieldsAreFilled(selector)) {
                //addSelectField('colors');
                addField("colors", "select", "Select");
            }

            elem = getNotCompletedFields(selector);
            fillInFieldFromColor(javaName, "#" + $(elem).attr("id"), restriction.colors);
            setValueInSelectInt("#" + $(elem).attr("id"), table[i].itemId);
            setSwitchDefaultVal(elem, javaName, table[i].defaultValue);
            setCostVal(elem, javaName, table[i].cost);

            if (allFieldsAreFilled(selector)) {
                //addSelectField('colors');
                addField(javaName, "select", "Select");
            }
        }
    }

    function installFromTemplateDesign(table, javaName) {
        var length = table.length;
        var selector = "." + javaName + "Select";
        var elem = null;

        for (var i = 0; i < length; ++i) {
            elem = getNotCompletedFields(selector);
            fillInFieldFromDesign("#" + $(elem).attr("id"), restriction.design);
            setValueInSelectInt("#" + $(elem).attr("id"), table[i].itemId);
        }
    }

    function installFromTemplateSelect(nameJavaObject) {
        var table = template[nameJavaObject];
        var length = table.length;
        var selector = "." + nameJavaObject + "Select";
        var elem = null;

        for (var i = 0; i < length; ++i) {
            if (allFieldsAreFilled(selector)) {
                addField(nameJavaObject, "select", "Select");
            }
            elem = getNotCompletedFields(selector);
            fillInFieldFromLimiBoolToInt(
                "#" + $(elem).attr("id"),
                restriction[nameJavaObject]
            );
            setValueInSelectInt("#" + $(elem).attr("id"), table[i].startRestriction);
            setSwitchDefaultVal(elem, nameJavaObject, table[i].defaultValue);

            if (allFieldsAreFilled(selector)) {
                addField(nameJavaObject, "select", "Select");
            }
        }
    }

    function installFromTemplateSealingLine(nameJavaObject) {
        var table = template[nameJavaObject];
        var length = table.length;
        var selector = "." + nameJavaObject + "Select";
        var elem = null;

        for (var i = 0; i < length; ++i) {
            if (allFieldsAreFilled(selector)) {
                addField(nameJavaObject, "select", "Select");
            }

            elem = getNotCompletedFields(selector);
            fillInFieldFromLimiSealingLine(
                "#" + $(elem).attr("id"),
                restriction[nameJavaObject]
            );
            setValueInSelectInt("#" + $(elem).attr("id"), table[i].startRestriction);
            setSwitchDefaultVal(elem, nameJavaObject, table[i].defaultValue);

            if (allFieldsAreFilled(selector)) {
                addField(nameJavaObject, "select", "Select");
            }
        }
    }

    function installFromTemplateFurnitur(nameJavaObject) {
        var table = template[nameJavaObject];

        if (table != null) {
            var length = table.length;
            var selector = "." + nameJavaObject + "Select";
            var elem = null;

            for (var i = 0; i < length; ++i) {
                if (allFieldsAreFilled(selector)) {
                    addField(nameJavaObject, "select", "Select");
                }

                elem = getNotCompletedFields(selector);
                fillInFieldFromLimiForFurnitur(
                    "#" + $(elem).attr("id"),
                    restriction[nameJavaObject]
                );
                setValueInSelectInt("#" + $(elem).attr("id"), table[i].itemId);
                setSwitchDefaultVal(elem, nameJavaObject, table[i].defaultValue);
                setCostVal(elem, nameJavaObject, table[i].cost);

                if (allFieldsAreFilled(selector)) {
                    addField(nameJavaObject, "select", "Select");
                }
            }
        }
    }

    function fillInMetal() {
        var metals = $(".metalSelect");

        for (var i = 0; i < metals.length; ++i) {
            if (!$(metals[i]).val()) {
                fillInFieldFromLimit("#" + $(metals[i]).attr("id"), restriction.metal);
            }
        }
    }

    function fillInColor(javaName, tabl) {
        var elems = $("." + javaName + "Select");

        for (var i = 0; i < elems.length; i++) {
            if (!$(elems[i]).val()) {
                fillInFieldFromColor(javaName, "#" + $(elems[i]).attr("id"), tabl);
            } else {
                let valueElem = $(elems[i]).val();
                fillInFieldFromColor(javaName, "#" + $(elems[i]).attr("id"), tabl);
                setValueInSelectInt("#" + $(elems[i]).attr("id"), valueElem);
            }
        }
    }

    function fillInDesign(javaName, tabl) {
        var elems = $("." + javaName + "Select");

        for (var i = 0; i < elems.length; ++i) {
            if (!$(elems[i]).val()) {
                fillInFieldFromDesign("#" + $(elems[i]).attr("id"), tabl);
            }
        }
    }

    function fillInSelector(selector, nameTable) {
        var elem = $(selector);

        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                fillInFieldFromLimiBoolToInt(
                    "#" + $(elem[i]).attr("id"),
                    restriction[nameTable]
                );
            }
        }
    }

    function fillInSealingLine(nameJavaObject) {
        var elem = $("." + nameJavaObject + "Select");

        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                fillInFieldFromLimiSealingLine(
                    "#" + $(elem[i]).attr("id"),
                    restriction[nameJavaObject]
                );
            }
        }
    }

    function fillInFurniture(nameJavaObject) {
        var elem = $("." + nameJavaObject + "Select");
        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                fillInFieldFromLimiForFurnitur(
                    "#" + $(elem[i]).attr("id"),
                    restriction[nameJavaObject]
                );
            }
        }
    }

    function fillInFieldFromLimit(selector, table) {
        $(selector).empty();

        $(selector).append($("<option ></option>"));

        for (var i = 0; i < table.length; ++i) {
            $(selector).append(
                $(
                    "<option value=" +
                    table[i].firstItem +
                    ">" +
                    table[i].firstItem +
                    "</option>"
                )
            );
        }
    }

    function fillInFieldFromLimiBoolToInt(selector, table) {
        $(selector).empty();

        $(selector).append($("<option ></option>"));

        for (var i = 0; i < table.length; ++i) {
            $(selector).append(
                $(
                    "<option value=" +
                    table[i].startRestriction +
                    ">" +
                    translateToBoolean(table[i].startRestriction) +
                    "</option>"
                )
            );
        }
    }

    function fillInFieldFromLimiSealingLine(selector, table) {
        $(selector).empty();

        $(selector).append($("<option ></option>"));

        for (var i = 0; i < table.length; ++i) {
            $(selector).append(
                $(
                    "<option value=" +
                    table[i].startRestriction +
                    ">" +
                    table[i].firstItem +
                    "</option>"
                )
            );
        }
    }

    function fillInFieldFromLimiForFurnitur(selector, table) {
        $(selector).empty();

        if (table != null) {
            $(selector).append($("<option ></option>"));
            if (selector == "colors") {
                $(selector).append($("<option value=all>select all</option>"));
            }

            for (var i = 0; i < table.length; ++i) {
                $(selector).append(
                    $(
                        "<option value=" +
                        table[i].itemId +
                        ">" +
                        table[i].firstItem +
                        "</option>"
                    )
                );
            }
        }
    }


    function searchDoubleValue(javaName, valueTable) {
        var elems = $("." + javaName + "Select");
        let doubleHave = true;
        for (var i = 0; i < elems.length; i++) {
            if ($(elems[i]).val() == valueTable.itemId) {
                doubleHave = false;
            }
        }
        return doubleHave;

    }

    function fillInFieldFromColor(javaName, selector, table) {

        $(selector).empty();

        $(selector).append($("<option ></option>"));

        $(selector).append($("<option value=all>select all</option>"));

        for (var i = 0; i < table.length; ++i) {

            if (searchDoubleValue(javaName, table[i])) {

                $(selector).append(
                    $(
                        "<option value=" +
                        table[i].itemId +
                        ">" +
                        table[i].firstItem +
                        "</option>"
                    )
                );


            }
        }
    }

    function fillInFieldFromDesign(selector, table) {
        $(selector).empty();

        $(selector).append($("<option ></option>"));

        for (var i = 0; i < table.length; ++i) {
            $(selector).append(
                $(
                    "<option value=" +
                    table[i].itemId +
                    ">" +
                    table[i].firstItem +
                    "</option>"
                )
            );
        }
    }

    function addMetalField() {
        var data = lastElemNumber(".metalSelect") + 1;

        $("<div>")
            .attr("class", "row")
            .attr("id", "metalLineDiv" + data)
            .appendTo("#metalDiv");

        $("<div>")
            .attr("class", "col-md-6 mb-3")
            .attr("id", "metalLineSelect" + data)
            .appendTo("#metalLineDiv" + data);

        $("<select>")
            .attr("class", "form-control metalSelect")
            .attr("id", "metalSelect" + data)
            .attr("data", data)
            .appendTo("#metalLineSelect" + data);

        $("<div>")
            .attr("class", "custom-control custom-switch")
            .attr("id", "metalLineSwitch" + data)
            .appendTo("#metalLineDiv" + data);

        $("<input>")
            .attr("class", "custom-control-input metalLineCheckbox")
            .attr("id", "metalLineCheckbox" + data)
            .attr("type", "checkbox")
            .attr("data", data)
            .appendTo("#metalLineSwitch" + data);

        $("<label>")
            .attr("class", "custom-control-label")
            .attr("for", "metalLineCheckbox" + data)
            .text("default")
            .appendTo("#metalLineSwitch" + data);

        return "#metalSelect" + data;
    }

    function addField(nameJavaObject, activField, typField) {
        var data = lastElemNumber("." + nameJavaObject + typField) + 1;

        $("<div>")
            .attr("class", "row")
            .attr("id", "" + nameJavaObject + "LineDiv" + data)
            .appendTo("#" + nameJavaObject + "Div");

        $("<div>")
            .attr("class", "col-md-6 mb-3")
            .attr("id", "" + nameJavaObject + "LineSelect" + data)
            .appendTo("#" + nameJavaObject + "LineDiv" + data);

        $("<" + activField + ">")
            .attr("class", "form-control " + nameJavaObject + typField)
            .attr("id", "" + nameJavaObject + typField + data)
            .attr("data", data)
            .appendTo("#" + nameJavaObject + "LineSelect" + data);

        $("<div>")
            .attr("class", "col-2")
            .attr("id", "" + nameJavaObject + "CostLineInput" + data)
            .appendTo("#" + nameJavaObject + "LineDiv" + data);

        $("<input>")
            .attr("class", "form-control")
            .attr("type", "number")
            .attr("placeholder", "стоимость,руб")
            .attr("id", "" + nameJavaObject + "CostInput" + data)
            .appendTo("#" + nameJavaObject + "CostLineInput" + data);


        $("<div>")
            .attr("class", "custom-control custom-switch")
            .attr("id", "" + nameJavaObject + "LineSwitch" + data)
            .appendTo("#" + nameJavaObject + "LineDiv" + data);

        $("<input>")
            .attr("class", "custom-control-input " + nameJavaObject + "LineCheckbox")
            .attr("id", "" + nameJavaObject + "LineCheckbox" + data)
            .attr("type", "checkbox")
            .attr("data", data)
            .appendTo("#" + nameJavaObject + "LineSwitch" + data);

        $("<label>")
            .attr("class", "custom-control-label")
            .attr("for", "" + nameJavaObject + "LineCheckbox" + data)
            .text("default")
            .appendTo("#" + nameJavaObject + "LineSwitch" + data);

        return "#" + nameJavaObject + "Select" + data;
    }

    function lastElemNumber(selector) {
        var elem = $(selector);
        var number = 0;

        for (var i = 0; i < elem.length; ++i) {
            var elemValue = Number.parseInt($(elem[i]).attr("data"));
            if (number < elemValue) {
                number = elemValue;
            }
        }
        return number;
    }

    function allFieldsAreFilled(selector) {
        var elem = $(selector);
        var allFilled = true;

        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                allFilled = false;
            }
        }
        return allFilled;
    }

    function getNotCompletedFields(selector) {
        var elem = $(selector);

        for (var i = 0; i < elem.length; ++i) {
            if (!$(elem[i]).val()) {
                return elem[i];
            }
        }
        return null;
    }

    function addInputField(name, div) {
        var data = lastElemNumber("." + name) + 1;
        $("<input>")
            .attr("class", "form-control " + name)
            .attr("id", "" + name + data)
            .attr("data", data)
            .appendTo("#" + div);

        return "#" + name + data;
    }

    function translateToBoolean(int) {
        if (int == 0) {
            return "нет";
        } else if (int == 1) {
            return "да";
        }
        alert("error: translateToBoolean: value is not valid!");
    }

    function addNewFieldAndfillInforFurnitur(nameJavaObject) {
        saveInJavaObjectColorAndFurnitur(nameJavaObject);
        if (allFieldsAreFilled("." + nameJavaObject + "Select")) {
            addField(nameJavaObject, "select", "Select");
        }
        fillInFurniture(nameJavaObject);
    }

    function addNewFieldAndfillInforSealingLine(nameJavaObject) {
        saveInJavaObjectforRestrictionValue(nameJavaObject);
        if (allFieldsAreFilled("." + nameJavaObject + "Select")) {
            //addSelectField(nameJavaObject);
            addField(nameJavaObject, "select", "Select");
        }
        fillInSealingLine(nameJavaObject);
    }

    //refill template

    function saveInJavaObjectforRestrictionValue(nameFieldJavaObject) {
        //delete all
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);

        var selector = "." + nameFieldJavaObject + "Select";
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                var defaultVal = getDefVal(elem[i], nameFieldJavaObject);
                template[nameFieldJavaObject].push(
                    findInRestriction($(elem[i]).val(), nameFieldJavaObject, defaultVal)
                );
            }
        }
    }

    function getDefVal(elem, nameFieldJavaObject) {
        var dataVal = $(elem).attr("data");

        if (
            $("#" + nameFieldJavaObject + "LineCheckbox" + dataVal).is(":checked")
        ) {
            return 1;
        } else {
            return 0;
        }

        return 0;
    }

    function getCostVal(elem, nameFieldJavaObject) {
        let dataVal = $(elem).attr("data");
        return $("#" + nameFieldJavaObject + "CostInput" + dataVal).val();
    }


    function findInRestriction(val, nameFieldJavaObject, defaultVal) {
        var tab = restriction[nameFieldJavaObject];
        for (var i = 0; i < tab.length; ++i) {
            if (tab[i].startRestriction == val) {
                var lim = tab[i];
                lim.defaultValue = defaultVal;
                return lim;
            }
        }
    }

    function findInFirstItem(val, nameFieldJavaObject, defaultVal) {
        var tab = restriction[nameFieldJavaObject];
        for (var i = 0; i < tab.length; ++i) {
            if (tab[i].firstItem == val) {
                var lim = tab[i];
                lim.defaultValue = defaultVal;
                return lim;
            }
        }
    }


    function saveInJavaObjectSize(nameFieldJavaObject) {

        //for pairOfValues = 0
        var pairOfValues = !$("#widthDoorCheckbox").is(':checked');
        if (!pairOfValues) {
            saveInJavaObjectListValues(nameFieldJavaObject);
        }

        //for pairOfValues = 1
        if (
            pairOfValues && (
                $("#" + nameFieldJavaObject + "Min").val() ||
                $("#" + nameFieldJavaObject + "Max").val() ||
                $("#" + nameFieldJavaObject + "Step").val() ||
                $("#" + nameFieldJavaObject + "Default").val()
            )
        ) {
            clearFromTemplate(nameFieldJavaObject);
            template[nameFieldJavaObject].push(
                newInstansLim(
                    $("#" + nameFieldJavaObject + "Min").val(),
                    $("#" + nameFieldJavaObject + "Max").val(),
                    $("#" + nameFieldJavaObject + "Step").val(),
                    1,
                    $("#" + nameFieldJavaObject + "Default").val()
                )
            );
        }
    }

    function clearFromTemplate(nameFieldJavaObject) {
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);
    }

    function saveInJavaObjectListValues(nameFieldJavaObject) {
        clearFromTemplate(nameFieldJavaObject);
        var selector = "." + nameFieldJavaObject + "Input";
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                var defaultVal = getDefVal(elem[i], nameFieldJavaObject);
                template[nameFieldJavaObject].push(
                    newInstansLim($(elem[i]).val(), 0, 0, 0, defaultVal)
                );
            }
        }
    }

    function newInstansLim(min, max, step, pairVal, defaultVal) {
        var lim = new (function () {
            this.startRestriction = min;
            this.stopRestriction = max;
            this.step = step;
            this.pairOfValues = pairVal;
            this.defaultValue = defaultVal;
        })();
        return lim;
    }

    function saveInJavaObjectColorAndFurnitur(nameFieldJavaObject) {
        //delete all
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);

        var selector = "." + nameFieldJavaObject + "Select";
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                let defaultVal = getDefVal(elem[i], nameFieldJavaObject);
                let costVal = getCostVal(elem[i], nameFieldJavaObject);
                template[nameFieldJavaObject].push(
                    findInRestrByItemId($(elem[i]).val(), nameFieldJavaObject, defaultVal, costVal)
                );
            }
        }
    }

    function findInRestrByItemId(val, nameFieldJavaObject, defaultVal, costVal = 0) {
        var tab = restriction[nameFieldJavaObject];
        for (var i = 0; i < tab.length; ++i) {
            if (tab[i].itemId == val) {
                var lim = tab[i];
                lim.defaultValue = defaultVal;
                lim.cost = costVal;
                return lim;
            }
        }
    }

    function saveInJavaObjectSelect(nameFieldJavaObject) {
        //delete all
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);

        var selector = "." + nameFieldJavaObject + "Select";
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                template[nameFieldJavaObject].push(newInstansBool($(elem[i]).val()));
            }
        }
    }

    function newInstansBool(val) {
        var lim = new (function () {
            this.startRestriction = val;
            this.stopRestriction = 0;
            this.step = 0;
            this.pairOfValues = 0;
        })();
        return lim;
    }

    function saveInJavaObjectFurnitur(nameFieldJavaObject) {
        //delete all
        var size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);

        var selector = "." + nameFieldJavaObject + "Select";
        var elem = $(selector);
        for (var i = 0; i < elem.length; ++i) {
            if ($(elem[i]).val()) {
                template[nameFieldJavaObject].push(
                    findInRestrForFurnitur($(elem[i]).val(), nameFieldJavaObject)
                );
            }
        }
    }

    function findInRestrForFurnitur(val, nameFieldJavaObject) {
        var tab = restriction[nameFieldJavaObject];
        for (var i = 0; i < tab.length; ++i) {
            if (tab[i].id == val) {
                return tab[i];
            }
        }
    }

    function setValueInSelect(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).text().toLowerCase() == value.toLowerCase()) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function setValueInSelectInt(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function clearPeriod(nameJavaObject) {
        $("#" + nameJavaObject + "Min").val("");
        $("#" + nameJavaObject + "Max").val("");
        $("#" + nameJavaObject + "Step").val("");
    }

    function clearSize(nameJavaObject) {
        var elems = $("." + nameJavaObject + "Input");
        for (var i = 0; i < elems.length; ++i) {
            if ($(elems[i]).attr("data") == "0") {
                $(elems[i]).empty();
            } else {
                $(elems[i]).remove();
                $("#" + nameJavaObject + "LineDiv" + i).remove();
            }
        }
    }

    function switchOffAll(nameJavaObject) {
        var elems = $("." + nameJavaObject + "LineCheckbox");
        for (var i = 0; i < elems.length; ++i) {
            $(elems[i]).prop("checked", false);
        }
    }

    function setSwitchDefaultVal(elem, nameJavaObject, defaultValue) {
        var dataVal = $(elem).attr("data");
        var defSwitch = $("#" + nameJavaObject + "LineCheckbox" + dataVal);

        if (defaultValue == 1) {
            $(defSwitch).prop("checked", true);
        } else {
            $(defSwitch).prop("checked", false);
        }
    }

    function setCostVal(elem, nameJavaObject, defaultValue) {
        var dataVal = $(elem).attr("data");
        $("#" + nameJavaObject + "CostInput" + dataVal).val(defaultValue);
    }

    function setCurrentClassById() {
        var classId = $("#classId").text();
        if (classId != "0") {
            setValueInSelectInt("#doorclassselect", classId);

            let ClassId = getDoorClassbyId($("#doorclassselect").val());
            fillOptionsInSelector(ClassId, "#doortypeselect");
            fillOptionsInSelector(ClassId, "#fillFromTemplate");

            var typeId = $("#typeId").text();
            if (typeId != "0") {
                setValueInSelectInt("#doortypeselect", typeId);
            }

            getDoorTemplate(typeId);
        }
    }

    function toList() {
        location.pathname = "templates";
    }

    function getIdFromUrl() {
        var url = location.href;
        var id = url.substring(url.lastIndexOf("/") + 1);
        return id;
    }

    function checkForAllSelect(nameObject) {
        var elems = $("." + nameObject + "Select");
        for (var i = 0; i < elems.length; ++i) {
            if ($(elems[i]).val() == "all") {
                return true;
            }
        }
        return false;
    }

    function deleteFields(nameObject) {
        var elems = $("." + nameObject + "Select");

        for (var i = 0; i < elems.length; ++i) {
            var data = $(elems[i]).attr("data");
            if (data > 0) {
                $("#" + nameObject + "LineDiv" + data).remove();
            } else {
                $("#" + nameObject + "LineDiv" + data).empty();
            }
        }
    }

    function fillFromTemplate() {
        let copyTypeId = $("#fillFromTemplate").val();
        clearFromTemplateAfterSelectingType();
        getDoorTemplate(copyTypeId);
    }
});
