let door;
let listColorsEntity;
let failureToSetValue = false;
let currentItem = "";
let RestrictionOfSelectionFields;
let selectSizeOpen = false;
let average_widthDoorVal = 0;
let maxSize_widthDoorVal = 0;
let availableFurnitureList;


jQuery("document").ready(function () {

    var goTo;
    var currentItemForDisplay = "";
    var currentItemDaughterForDisplay = "";
    var currentItemForDisplayId = "";

    var doorLeaf = 1;
    var fanlight = 0;

    var classList;

    var colors;


    var id = $("#id").text();
    var orderId = $("#orderId").text();
    var typeId = 0;

    var sizeMin = 0;
    var sizeMax = 0;


    var firstPress = true;

    var historyList = new Array();
    var currentHisPoint = 0;

    var audio = $("#mySoundClip")[0];

    Door.getColorInstans();

    if (id > 0) {
        getNewDoorInstans(false);
    } else {
        getClassList();
    }

    function FillOutForm(door, updateClassDiv) {
        RepresentationManager.showAllFieldsValues(door);
        if (updateClassDiv) {
            ClassCardBlock.display(door.availableDoorClass);
            currentItem = "doorClass";
            hideShowField(true);
        }
        displayPrice();
    }

    //--------------------------------------
    //select
    //--------------------------------------

    $(".select_door_class").on("click", ".class_card", function () {
        TypeCardBlock.display($(this).attr("data"), classList);

        currentItem = "doorType";
        currentItemForDisplay = $(this).html();
        currentItemDaughterForDisplay = "";
        currentItemForDisplayId = currentItem;
        failureToSetValue = false;

        hideShowField(true);
    });

    $(".select_door_type").on("click", ".typeLine", function () {
        doorLeaf = $(this).attr("data-LeafDoorLeaf");
        typeId = $(this).attr("data");

        pickOut(this);
        getNewDoorInstans(false);

    });

    $(".vertical_menu_button").on("click", function () {
        if ($("#name" + $(this).attr("id") + "").attr("available") === "yes") {
            processItemSelection(this);
        }
    });

    $(".navigation_panel_div").on("click", ".navigation_panel", function () {
        processItemSelection(this);
    });

    $(".color_pages").on("click", ".pag", function () {
        if ($(this).attr("data") == ">") {
            var val = Number.parseInt($(".color_pages").attr("data"));
            displayColor(val + 1);
        } else {
            displayColor($(this).attr("data"));
        }
    });

    $(".div_images_Color").on("click", function () {
        setDoorField($(this).attr("Item"), $(this).attr("data"));
        RepresentationManager.showFieldValue($(this).attr("data"));
        Door.draw(door, 1);
        pickOut(this);
    });

    $(".div_images_DoorGlass").on("click", function () {
        setDoorGlassImg($(this).attr("Item"), $(this).attr("data"));
        RepresentationManager.showFieldValue($(this).children("span").html());
        if ($("#typeDoorGlassShow").text() == "" || $("#typeDoorGlassShow").text() == "нет"){
            resetDoorGlassField();
        }
        Door.draw(door, 1);
        pickOut(this);
        if (goTo != "") {
            currentItem = goTo;
            hideShowField(true);
        }

    });

    $(".div_images_furniture").on("click", function () {
        setDoorFurniture(
            $(this).attr("Item"),
            $(this).attr("data"),
            door.furnitureKit
        );
        RepresentationManager.showAllFieldsValues(door);
        pickOut(this);
        if (goTo != "") {
            currentItem = goTo;
            hideShowField(true);
        }
    });

    $(".div_images_Image").on("click", function () {

        let nameJavaField = $(this).attr("Item");
        let idJavaObject = $(this).attr("data");
        let javaObject = findObject(nameJavaField, idJavaObject);

        setDoorFurnitureByObject(javaObject, nameJavaField, door.shieldKit);

        if (nameJavaField == "shieldColor"){
            clearRelatedFieldsForImage(javaObject);
        }

        if (nameJavaField == "shieldDesign"){
            clearGlass(javaObject);
        }

        RepresentationManager.showAllFieldsValues(door);
        pickOut(this);
        if (goTo != "") {
            currentItem = goTo;
            hideShowField(true);
        }
    });

    $("#commentTextarea").change(function () {
        setDoorField("comment", $(this).val());
        RepresentationManager.showAllFieldsValues(door);
    });

    SideOpen.init();
    InnerOpen.init();

    $(".ios-toggle").on("click", function () {
        var isRepRun = true;

        if (currentItem == "metal") {
            oneEnableAllDisable("metal", this);
            setDoorField($(this).attr("Item"), $(this).attr("data"));
        } else if (currentItem == "heightDoor") {
            if ($(this).is(":checked")) {
                fanlight = 1;
                $('[fanlight="1"]').attr("show", "is_alive_lement");
                $('[doorLeaf="' + doorLeaf + '"][fanlight="1"]').attr(
                    "show",
                    "ghost_lement"
                );
            } else {
                fanlight = 0;
                setDoorField("doorFanlightHeight", 0);
                $('[fanlight="1"]').attr("show", "ghost_lement");
            }
        } else if (currentItem == "deepnessDoor") {
            if ($(this).is(":checked")) {
                oneEnableAllDisable("deepnessDoor", this);
                setDoorField($(this).attr("Item"), $(this).attr("data"));
            }
        } else if (currentItem == "thicknessDoorLeaf") {
            if ($(this).is(":checked")) {
                oneEnableAllDisable("thicknessDoorLeaf", this);
                setDoorField($(this).attr("Item"), $(this).attr("data"));
            }
        } else if (currentItem == "sideDoorOpen") {
            if ($(this).is(":checked")) {
                oneEnableAllDisable($(this).attr("Item"), this);
                isRepRun = true;
            }
        } else if (currentItem == "additionalDoorSettings") {
            isRepRun = false;
        } else if (currentItem == "doorGlass") {
            // if ($(this).is(":checked")) {
            //     $(".input_doorGlass_div").attr("show", "is_alive_lement");
            //     setDoorField("isDoorGlass", 1);
            //     isRepRun = false;
            // } else {
            //     $(".input_doorGlass_div").attr("show", "ghost_lement");
            //     setDoorField("isDoorGlass", 0);
            // }
        } else if (currentItem == "additionally") {
            if ($(this).attr("available") == "yes") {
                if ($(this).attr("item") == "nightLock") {
                    if ($(this).is(":checked")) {
                        setDoorFurniture($(this).attr("Item"), $(this).attr("data"));
                    } else {
                        setDoorFurniture($(this).attr("Item"), 0);
                    }
                } else if ($(this).attr("item") == "peephole") {
                    if ($(this).is(":checked")) {
                        setDoorFurniture($(this).attr("Item"), $(this).attr("data"));
                    } else {
                        setDoorFurniture($(this).attr("Item"), 0);
                    }
                } else if ($(this).attr("item") == "amplifierCloser") {
                    if ($(this).is(":checked")) {
                        setDoorFurniture($(this).attr("Item"), $(this).attr("data"));
                    } else {
                        setDoorFurniture($(this).attr("Item"), 0);
                    }
                }
            } else {
                if ($(this).is(":checked")) {
                    $(this).prop("checked", false);
                } else {
                    $(this).prop("checked", true);
                }
            }
        }

        if (isRepRun) {
            RepresentationManager.showFieldValue($(this).attr("data"));
            Door.draw(door, 1);
        }
    });

    $("#buttonCalculateCostShow").on("click", function () {
        PriceComponent.deleteRow();
        let cost = door.costList.costList;
        if (cost.length == 0) return;
        let costOutput = "";
        let idRow = "";
        let idColName = "";
        for (let i = 0; i < cost.length; i++) {
            idRow = PriceComponent.addLineRow("containerToast", "row lineForDelete", "row" + i);
            idColName = PriceComponent.addLineColumn(idRow, "col-9", "colName" + i);
            costOutput = cost[i].name;
            $("#" + idColName).text(costOutput);
            idColName = PriceComponent.addLineColumn(idRow, "col", "colCost" + i);
            costOutput = cost[i].cost;
            $("#" + idColName).text(costOutput);

        }
        idRow = PriceComponent.addLineRow("containerToast", "row lineForDelete totalColorLine", "rowTotalCost");
        idColName = PriceComponent.addLineColumn(idRow, "col-9", "colNameTotalCost");
        costOutput = "TotalCost";
        $("#" + idColName).text(costOutput);
        idColName = PriceComponent.addLineColumn(idRow, "col", "colCostTotalCost");
        costOutput = door.costList.totalCost;
        $("#" + idColName).text(costOutput);

        $('.toast').toast('show');
    });

    function calculateCostHide() {
        $('.toast').toast('hide');
        PriceComponent.deleteRow();
    }

    $("#buttonCalculate").on("click", function () {
        if (checkThCompletedFields()) {
            var strJSON = JSON.stringify(door);

            $.ajax({
                type: "POST",
                url: "data",
                contentType: "application/json",
                data: strJSON,
                dataType: "json",
                success: function (data) {
                    //alert('price is: ' + data.price);
                    door = data;
                    displayPrice();
                    calculateCostHide();
                },
                error: function (data) {
                    alert("error:" + data);
                },
            });
        }
        audio.play();
    });

    $("#buttonSaveDoor").on("click", function () {
        if (checkThCompletedFields()) {
            var strJSON = JSON.stringify(door);

            $.ajax({
                type: "POST",
                url: "saveDoor",
                contentType: "application/json",
                data: strJSON,
                dataType: "json",
                success: function (data) {
                    door.id = data.id;
                },
                error: function (data) {
                    alert("error:" + data);
                },
            });
        }
    });

    $("#SaveAndСlose").on("click", function () {
        if (checkThCompletedFields()) {
            var strJSON = JSON.stringify(door);

            $.ajax({
                type: "POST",
                url: "saveDoor",
                contentType: "application/json",
                data: strJSON,
                dataType: "json",
                success: function (data) {
                    toOrder();
                },
                error: function (data) {
                    alert("error:" + data);
                },
            });
        }
    });

    $("#toСlose").on("click", function () {
        toOrder();
    });

    $(".to_calculate").hover(
        function () {
            $(".priceghost").attr("show", "is_alive_lement");
        },
        function () {
            $(".priceghost").attr("show", "ghost_lement");
        }
    );

    //select namber
    $("#select_set").on("click", function () {
        SizingDrum.setSize();
    });

    $("#select_cancel").on("click", function () {
        closeSelect();
    });

    $(".select_size").on("click", function () {
        if ($(this).attr("available") == "no") {
            return;
        }

        sizeMin = SizingDrum.sizeLimMin($(this).attr("id"));
        sizeMax = SizingDrum.sizeLimMax($(this).attr("id"));

        maxSize_widthDoorVal = RestrictionOfSelectionFields["widthDoor"].length - 1;
        average_widthDoorVal = average_Size_widthDoor(maxSize_widthDoorVal);

        select_introduction(
            $(this).attr("data"),
            $(this).attr("name"),
            $(this).attr("id"),
            sizeMin,
            sizeMax
        );

        selectSizeOpen = true;
        firstPress = true;
    });

    $("#addL").on("click", function () {

        average_widthDoorVal += 1;
        add("L", 5, null, sizeMin, sizeMax);

    });

    $("#reduceL").on("click", function () {
        average_widthDoorVal -= 1;

        reduce("L", 5, sizeMin, sizeMax);

    });

    $("#addR").on("click", function () {
        add("R", 5, null, sizeMin, sizeMax);
    });

    $("#reduceR").on("click", function () {
        reduce("R", 5, sizeMin, sizeMax);
    });

    $("#backHis").on("click", function () {
        backHistoryList();
    });
    $("#nextHis").on("click", function () {
        nextHistoryList();
    });

    $(document).keyup(function (e) {
        if (!selectSizeOpen) {
            return;
        }
        handleKeystroke(e);
        checkForLimits();
    });


    $(".toolbarPage").on("click", ".toolbarPageButton", function () {
        if (currentItem == "doorColor") {
            displayColor("doorColor", door.template.colors, parseInt($(this).attr('data')));
        } else if (currentItem == "shieldColor" || currentItem == "shieldDesign") {
            displayImage(currentItem, availableFurnitureList[currentItem], parseInt($(this).attr('data')));
        } else if (currentItem == "topLock" ||
            currentItem == "lowerLock" ||
            currentItem == "handle" ||
            currentItem == "lowerLockCylinder" ||
            currentItem == "topLockCylinder" ||
            currentItem == "topInLockDecor" ||
            currentItem == "topOutLockDecor" ||
            currentItem == "lowerInLockDecor" ||
            currentItem == "lowerOutLockDecor" ||
            currentItem == "closer" ||
            currentItem == "typeDoorGlass" ||
            currentItem == "toning" ||
            currentItem == "armor"
        ) {
            displayListOfItems(currentItem, availableFurnitureList[currentItem], parseInt($(this).attr('data')), '');
        }

    });

    //--------------------------------------
    //setter
    //--------------------------------------

    function setDoorField(fieldName, value) {
        door[fieldName] = value;
        failureToSetValue = false;
    }

    function setDoorGlassImg(fieldName, value) {
        var furn = findObject(fieldName, value);
        door.doorGlass[fieldName] = furn;
    }

    function setDoorGlassField(fieldName, value) {
        door.doorGlass[fieldName] = value;
    }

    function resetDoorGlassField() {
        Door.setGlass("glassWidth",0);
        Door.setGlass("glassHeight",0);
        Door.setGlass("leftGlassPosition",0);
        Door.setGlass("bottomGlassPosition",0);
        setDoorGlassField("toning", null);
        setDoorGlassField("armor", null);
        $(".vertical_menu_button_rigtht#" + "toning" + "Show strong").html("");
        $(".vertical_menu_button_rigtht#" + "armor" + "Show strong").html("");
        $("#doorGlassShow").text("");
        door.doorGlass.id = 0;
        door.doorGlass.space = 0;
    }

    function setDoorFurniture(fieldName, value, doorKit) {
        setDoorFurnitureByObject(findObject(fieldName, value), fieldName, doorKit)
    }

    function setDoorFurnitureByObject(object, fieldName, doorKit) {
        doorKit[fieldName] = object;
    }

    function findObject(name, value) {
        if (name == "lowerLockCylinder" || name == "topLockCylinder") {
            name = "lockCylinder";
        }

        var sizeRest = availableFurnitureList[name].length;
        var tab = availableFurnitureList[name];
        for (var i = 0; i < sizeRest; ++i) {
            if (tab[i].id == value) {
                return tab[i];
            }
        }
        return null;
    }

    function getFirstAttr(attr) {
        if (attr.indexOf(' ') > 0) {
            return attr.substring(0, attr.indexOf(' '));
        } else {
            return attr;
        }
    }

    function pickOut(item) {
        var attr = $(item).attr("class");
        let attrFirst = getFirstAttr(attr);

        var elems = $("." + attrFirst);
        var elemsTotal = elems.length;
        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr("check", "no");
        }

        $(item).attr("check", "checkbox");
    }

    function oneEnableAllDisable(Item, thisItem) {
        var elems = $('.ios-toggle[Item="' + Item + '"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            if ($(thisItem).attr("id") == $(elems[i]).attr("id")) {
            } else {
                $(elems[i]).prop("checked", false);
            }
        }
    }

    function allDisable(className) {
        //all is ghost_lement
        var elems = $("." + className + "");
        for (var i = 0; i < elems.length; ++i) {
            $(elems[i]).attr("show", "ghost_lement");
        }
    }

    function addNavigation() {
        $(".navigation_panel").remove();

        $("<a>")
            .attr("class", "navigation_panel")
            .attr("href", "#")
            .attr("id", currentItemForDisplayId)
            .html(currentItemForDisplay)
            .appendTo(".navigation_panel_div");
        $("<span>")
            .attr("class", "navigation_panel")
            .html("->")
            .appendTo(".navigation_panel_div");

        if (currentItemDaughterForDisplay != "") {
            $("<a>")
                .attr("class", "navigation_panel")
                .attr("href", "#")
                .html(currentItemDaughterForDisplay)
                .appendTo(".navigation_panel_div");
        }
    }

    //--------------------------------------
    //periodic installation
    //--------------------------------------

    function toOrder() {
        location.href = "order?orderId=" + orderId;
    }

    function displayPrice() {
        $("#price").text("Цена: " + door.priceWithMarkup);
        //door.costList.totalCost;
        $(".decryption").remove();

        if (door.costList !== null) {
            var tab = door.costList.list;
            var size = tab.length;
            for (var i = 0; i < size; ++i) {
                $("<sran>")
                    .attr("class", "decryption")
                    .text("" + tab[i].name + " - " + tab[i].cost)
                    .appendTo("#calculateResultDiv");
                $("<br>").attr("class", "decryption").appendTo("#calculateResultDiv");
            }
        }
    }

    function displayMetal(data) {
        allDisable("metal_checkbox");

        for (var i = 0; i < data.metal.length; ++i) {
            $("#metal" + i).attr("show", "is_alive_lement");
            $("#nememetal" + i).text(data.metal[i].firstItem);
            $("#checkboxmetal" + i).attr("data", data.metal[i].firstItem);
            if (data.metal[i].defaultValue == 1) {
                $("#checkboxmetal" + i).prop("checked", true);
                setDoorField(
                    $("#checkboxmetal" + i).attr("Item"),
                    $("#checkboxmetal" + i).attr("data")
                );
                currentItem = "metal";
                RepresentationManager.showFieldValue($("#checkboxmetal" + i).attr("data"));
            }
        }
        $("#namemetal").attr("available", "yes");
    }

    function displayWidthDoorAndHeightDoor(data) {
        if (data.widthDoor.length > 0) {
            $("#namewidthDoor").attr("available", "yes");
        }

        if (data.heightDoor.length > 0) {
            $("#nameheightDoor").attr("available", "yes");
        }
    }

    function displayheightDoorFanlight(data) {
        if (data.heightDoorFanlight.length > 0) {
            if (
                data.heightDoorFanlight.tartRestriction == 0 &&
                data.heightDoorFanlight.stopRestriction == 0
            ) {
                $("#fanlightCheckboxDiv").addClass("ghost");
            }
        } else {
            $("#fanlightCheckboxDiv").addClass("ghost");
        }
    }

    function displayDeepnessDoorAndThicknessDoorLeaf(data) {
        allDisable("deepnessDoor_checkbox");
        writeInCheckbox("deepnessDoor", data.deepnessDoor);
        if (data.deepnessDoor.length > 1) {
            $("#namedeepnessDoor").attr("available", "yes");
        }

        allDisable("thicknessDoorLeaf_checkbox");
        writeInCheckbox("thicknessDoorLeaf", data.thicknessDoorLeaf);
        if (data.thicknessDoorLeaf.length > 1) {
            $("#namethicknessDoorLeaf").attr("available", "yes");
        }
    }

    function writeInCheckbox(nameItem, tab) {
        for (var i = 0; i < tab.length; ++i) {
            $("#" + nameItem + i).attr("show", "is_alive_lement");
            $("#neme" + nameItem + i).text(tab[i].startRestriction);
            $("#checkbox" + nameItem + i).attr("data", tab[i].startRestriction);
            if (tab[i].defaultValue == 1) {
                $("#checkbox" + nameItem + i).prop("checked", true);
                setDoorField(nameItem, tab[i].startRestriction);
                currentItem = nameItem;
                RepresentationManager.showFieldValue(tab[i].startRestriction);
                Door.draw(door, 1);
            }
        }
    }

    function processItemSelection(item) {
        currentItem = $(item).attr("id");

        if ($("#" + currentItem).attr("available") == "no") {
            return;
        }
        currentItemForDisplay = $(item).html();
        currentItemDaughterForDisplay = "";
        currentItemForDisplayId = currentItem;
        failureToSetValue = false;

        hideShowField(true);
        addNavigation();
    }

    function displayColor(nameJava, tab, bias) {

        let offsetTab = PaginationPage.generate(tab, bias, 'toolbarPage');

        for (var i = 0; i < offsetTab.amountElements; ++i) {
            if (i + offsetTab.biasInt < offsetTab.tabSize) {
                $("#images" + nameJava + "Div" + i).attr("show", "is_alive_lement");
                $("#images" + nameJava + "Div" + i).attr(
                    "data",
                    tab[i + offsetTab.biasInt].firstItem
                );
                for (let a = 0; a < Door.listColorsEntity.length; a++) {
                    if (Door.listColorsEntity[a].id == tab[i + offsetTab.biasInt].itemId) {
                        $("#images" + nameJava + "Img" + i).attr(
                            "src",
                            Door.listColorsEntity[a].picturePath
                        );
                    }
                }

                $("#images" + nameJava + "Span" + i).text(
                    tab[i + offsetTab.biasInt].firstItem
                );
            } else {
                $("#images" + nameJava + "Div" + i).attr("show", "ghost_lement");
                $("#images" + nameJava + "Div" + i).attr("data", "");
                $("#images" + nameJava + "Img" + i).attr("src", "");
                $("#images" + nameJava + "Span" + i).text("");
            }
        }
    }

    function displayImage(nameJava, tab, bias) {

        let offsetTab = PaginationPage.generate(tab, bias, 'toolbarPage');

        for (var i = 0; i < offsetTab.amountElements; ++i) {
            if (i + offsetTab.biasInt < offsetTab.tabSize) {
                $("#images" + nameJava + "Div" + i).attr("show", "is_alive_lement");
                $("#images" + nameJava + "Div" + i).attr("data", tab[i + offsetTab.biasInt].id);
                $("#images" + nameJava + "Img" + i).attr(
                    "src",
                    tab[i + offsetTab.biasInt].picturePath
                );
                $("#images" + nameJava + "Span" + i).text(tab[i + offsetTab.biasInt].name);
            } else {
                $("#images" + nameJava + "Div" + i).attr("show", "ghost_lement");
                $("#images" + nameJava + "Div" + i).attr("data", "");
                $("#images" + nameJava + "Img" + i).attr("src", "");
                $("#images" + nameJava + "Span" + i).text("");
            }
        }
    }

    function displayadditionalDoorSettings(data) {
        displayElement("doorstep", data);
        displayElement("stainlessSteelDoorstep", data);

        Trim.init(data);

        displayElementSealingLine("firstSealingLine", data);
        displayElementSealingLine("secondSealingLine", data);
        displayElementSealingLine("thirdSealingLine", data);
    }

    function displayElement(name, data) {
        var tabSize = data[name].length;
        if (tabSize > 1) {
            $("#name" + name).attr("available", "yes");
            $("#" + name + "_checkbox").attr("available", "yes");
        }
    }

    function displayElementSealingLine(name, data) {
        var tab = data[name];
        var tabSize = tab.length;

        if (tabSize > 1) {
            for (var i = 0; i < tabSize; i++) {
                var lineName = name + "_" + tab[i].startRestriction;

                $("#name" + lineName).attr("available", "yes");
                $("#" + lineName + "_checkbox").attr("available", "yes");
            }
        }
    }

    function displayListOfItems(nameTab, tab, bias, postfixName) {
        if (tab != null) {
            let offsetTab = PaginationPage.generate(tab, bias, 'toolbarPage');
            for (var i = 0; i < offsetTab.amountElements; ++i) {
                var sel = "#" + nameTab;
                if (i + offsetTab.biasInt < offsetTab.tabSize) {
                    $(sel + "Div" + i).attr("show", "is_alive_lement");
                    $(sel + "Div" + i).attr("data", tab[i + offsetTab.biasInt].id);
                    $(sel + "Img" + i).attr("src", tab[i + offsetTab.biasInt].picturePathFirst);
                    $(sel + "Span" + i).text(tab[i + offsetTab.biasInt].name);
                } else {
                    $(sel + "Div" + i).attr("show", "ghost_lement");
                    $(sel + "Div" + i).attr("data", "");
                    $(sel + "Img" + i).attr("src", "");
                    $(sel + "Span" + i).text("");
                }
            }
        }
    }

    function displayGlass() {
        //if (door.isDoorGlass == 1) {
            //$("#checkboxdoorGlass0").prop("checked", true);
            $(".input_doorGlass_div").attr("show", "is_alive_lement");

            if (door.doorGlass.typeDoorGlass !== null) {
                currentItem = "typeDoorGlass";
                RepresentationManager.showFieldValue(door.doorGlass.typeDoorGlass.name);
            }
            if (door.doorGlass.toning !== null) {
                currentItem = "toning";
                RepresentationManager.showFieldValue(door.doorGlass.toning.name);
            }
            if (door.doorGlass.armor !== null) {
                currentItem = "armor";
                RepresentationManager.showFieldValue(door.doorGlass.armor.name);
            }

            currentItem = "glassWidth";
            RepresentationManager.showFieldValue(door.doorGlass.glassWidth);
            currentItem = "glassHeight";
            RepresentationManager.showFieldValue(door.doorGlass.glassHeight);
            currentItem = "leftGlassPosition";
            RepresentationManager.showFieldValue(door.doorGlass.leftGlassPosition);
            currentItem = "bottomGlassPosition";
            RepresentationManager.showFieldValue(door.doorGlass.bottomGlassPosition);
       // }
    }

    function checkThCompletedFields() {
        if (door.widthDoor == 0 && door.heightDoor == 0) {
            alert("please select door dimensions!");
            return false;
        }
        if (door.sideDoorOpen == null) {
            alert("please select side door open!");
            return false;
        }
        if (door.doorColor == null) {
            alert("please select door color!");
            return false;
        }
        if (
            doorLeaf == 2 &&
            (door.activeDoorLeafWidth == null || door.activeDoorLeafWidth == 0)
        ) {
            alert("please select active door leaf width!");
            return false;
        }
        return true;
    }

    function sizeLimMin(elemId) {
        if (elemId == "inputWidthDoor") {
            var tab = RestrictionOfSelectionFields["widthDoor"];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].startRestriction;
                }
            }
            return 0;
        } else if (elemId == "inputHeightDoor") {
            var tab = RestrictionOfSelectionFields["heightDoor"];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].startRestriction;
                }
            }
            return 0;
        } else if (elemId == "inputActivDoorLeafWidth") {
            var tab = RestrictionOfSelectionFields["widthDoorLeaf"];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].startRestriction;
                }
            }
        } else if (elemId == "inputHeightFanlight") {
            var tab = RestrictionOfSelectionFields["heightDoorFanlight"];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].startRestriction;
                }
            }
        }
        return 0;
    }

    function sizeLimMax(elemId) {
        if (elemId == "inputWidthDoor") {
            var tab = RestrictionOfSelectionFields["widthDoor"];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].stopRestriction;
                }
            }
            return 5000;
        } else if (elemId == "inputHeightDoor") {
            var tab = RestrictionOfSelectionFields["heightDoor"];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].stopRestriction;
                }
            }
            return 5000;
        } else if (elemId == "inputActivDoorLeafWidth") {
            var tab = RestrictionOfSelectionFields["widthDoorLeaf"];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].stopRestriction;
                }
            }
            return 800;
        } else if (elemId == "inputHeightFanlight") {
            var tab = RestrictionOfSelectionFields["heightDoorFanlight"];
            for (var i = 0; i < tab.length; i++) {
                if (tab[i].pairOfValues == 1) {
                    return tab[i].stopRestriction;
                }
            }
            return 800;
        }
        return 0;
    }

    function getNewDoorInstans(updateClassDiv) {
        $.ajax({
            url: "door",
            data: {id: id, orderId: orderId, typid: typeId},
            dataType: "json",
            success: function (data) {
                door = data;
                id = door.id;
                FillOutForm(door, updateClassDiv);
                getFurnitureAvailableFields();
            },
            error: function (data) {
                alert("error: getting the door failed !");
            },
        });
    }

    function getFurnitureAvailableFields() {
        if (typeId === 0) {
            typeId = door.doorType.id;
        }

        $.ajax({
            url: "furniture/available-fields/" + typeId,
            dataType: "json",
            success: function (data) {
                availableFurnitureList = data;
                fillInTheFieldsToTheTemplate(door.template);
            },
            error: function (data) {
                alert("error: getting furniture available-fields failed !" + data);
            },
        });
    }

    function getClassList() {
        $.ajax({
            url: "class/list",
            dataType: "json",
            success: function (data) {
                classList = data;
                ClassCardBlock.display(classList);
                hideShowField(true, "doorClass");
            },
            error: function (data) {
                alert("error:" + data);
            },
        });
    }

    function glassShowIfIsExist(data) {
        if (data.typeDoorGlass.length > 0) {
            $("#doorGlass").attr("show", "is_alive_lement");
        } else {
            $("#doorGlass").attr("show", "ghost_lement");
        }
    }

    function fillInTheFieldsToTheTemplate(data) {
        glassShowIfIsExist(data);
        const makeAvailable = new AvailableManager(data, availableFurnitureList);
        makeAvailable.makeFieldsAvailable();

        if (data != null) {
            displayMetal(data);
            displayWidthDoorAndHeightDoor(data);
            displayheightDoorFanlight(data);
            displayDeepnessDoorAndThicknessDoorLeaf(data);

            colors = data.colors;
            displayColor("doorColor", data.colors, 0);
            displayImage("shieldColor", availableFurnitureList.shieldColor, 0);
            displayImage("shieldDesign", availableFurnitureList.shieldDesign, 0);
            displayImage("shieldGlass", addToShieldGlassList(), 0);

            displayadditionalDoorSettings(data);
            displayListOfItems("topLock", availableFurnitureList.topLock, 0, "kit");
            displayListOfItems(
                "lowerLock",
                availableFurnitureList.lowerLock,
                0,
                "kit"
            );

            displayListOfItems("handle", availableFurnitureList.handle, 0, "");

            displayListOfItems(
                "lowerLockCylinder",
                availableFurnitureList.lockCylinder,
                0,
                ""
            );
            displayListOfItems(
                "topLockCylinder",
                availableFurnitureList.lockCylinder,
                0,
                ""
            );

            displayListOfItems(
                "topInLockDecor",
                availableFurnitureList.topInLockDecor,
                0,
                ""
            );
            displayListOfItems(
                "topOutLockDecor",
                availableFurnitureList.topOutLockDecor,
                0,
                ""
            );
            displayListOfItems(
                "lowerInLockDecor",
                availableFurnitureList.lowerInLockDecor,
                0,
                ""
            );
            displayListOfItems(
                "lowerOutLockDecor",
                availableFurnitureList.lowerOutLockDecor,
                0,
                ""
            );

            displayListOfItems("closer", availableFurnitureList.closer, 0, "");
            displayListOfItems(
                "endDoorLock",
                availableFurnitureList.endDoorLock,
                0,
                ""
            );
            displayListOfItems(
                "typeDoorGlass",
                availableFurnitureList.typeDoorGlass,
                0,
                ""
            );
            displayListOfItems("toning", availableFurnitureList.toning, 0, "");
            displayListOfItems("armor", availableFurnitureList.armor, 0, "");

            RestrictionOfSelectionFields = data;
        }
    }

    function hideShowField(addHistory, newCurrentItem = null) {
        if (newCurrentItem != null) {
            currentItem = newCurrentItem;
        }

        goTo = "";

        if (addHistory) {
            addToTheHistoryList(currentItem);
        }

        if (currentItem == "doorClass") {
            $(".select_door_class").attr("show", "is_alive_lement");
        } else {
            $(".select_door_class").attr("show", "ghost_lement");
        }

        if (currentItem == "doorType") {
            $(".select_door_type").attr("show", "is_alive_lement");
        } else {
            $(".select_door_type").attr("show", "ghost_lement");
        }

        if (currentItem == "metal") {
            $(".select_metal").attr("show", "is_alive_lement");
        } else {
            $(".select_metal").attr("show", "ghost_lement");
        }

        if (currentItem == "widthDoor") {
            $(".select_widthDoor").attr("show", "is_alive_lement");
            if (door != null && door.widthDoor != null && door.widthDoor != 0) {
                $("#inputWidthDoor").attr("value", door.widthDoor);
            }
            if (doorLeaf == 1) {
                $('[DoorLeaf="1"]').attr("show", "is_alive_lement");
                $('[DoorLeaf="2"]').attr("show", "ghost_lement");
            } else {
                $('[DoorLeaf="1"]').attr("show", "ghost_lement");
                $('[DoorLeaf="2"]').attr("show", "is_alive_lement");
            }
        } else {
            $(".select_widthDoor").attr("show", "ghost_lement");
        }

        if (currentItem == "heightDoor") {
            $(".select_heightDoor").attr("show", "is_alive_lement");
            if (door != null && door.heightDoor != null && door.heightDoor != 0) {
                $("#inputHeightDoor").attr("value", door.heightDoor);
            }

            if (doorLeaf == 1) {
                $('[DoorLeaf="1"]').attr("show", "is_alive_lement");
                $('[DoorLeaf="2"]').attr("show", "ghost_lement");
            } else {
                $('[DoorLeaf="1"]').attr("show", "ghost_lement");
                $('[DoorLeaf="2"]').attr("show", "is_alive_lement");
            }

            if (fanlight == 1) {
                $('[fanlight="1"]').attr("show", "is_alive_lement");
                $('[doorLeaf="' + doorLeaf + '"][fanlight="1"]').attr(
                    "show",
                    "ghost_lement"
                );
            } else {
                $('[fanlight="1"]').attr("show", "ghost_lement");
            }
        } else {
            $(".select_heightDoor").attr("show", "ghost_lement");
        }

        if (currentItem == "deepnessDoor") {
            $(".select_deepnessDoor").attr("show", "is_alive_lement");
        } else {
            $(".select_deepnessDoor").attr("show", "ghost_lement");
        }

        if (currentItem == "thicknessDoorLeaf") {
            $(".select_thicknessDoorLeaf").attr("show", "is_alive_lement");
        } else {
            $(".select_thicknessDoorLeaf").attr("show", "ghost_lement");
        }

        if (currentItem == "sideDoorOpen") {
            $(".select_sideDoorOpen").attr("show", "is_alive_lement");
            InnerOpen.updateView();
            SideOpen.updateView();
        } else {
            $(".select_sideDoorOpen").attr("show", "ghost_lement");
        }

        if (currentItem == "additionalDoorSettings") {
            $(".select_additionalDoorSettings").attr("show", "is_alive_lement");
        } else {
            $(".select_additionalDoorSettings").attr("show", "ghost_lement");
        }

        if (currentItem == "doorColor") {
            setСurrentColor();
            $(".select_doorColor").attr("show", "is_alive_lement");
            displayColor("doorColor", door.template.colors, 0);
            PaginationPage.show();
        } else {
            $(".select_doorColor").attr("show", "ghost_lement");
        }

        if (currentItem == "doorGlass") {
            $(".select_doorGlass").attr("show", "is_alive_lement");
            displayGlass();
            setAvailableChildrenAttr();
        } else {
            $(".select_doorGlass").attr("show", "ghost_lement");
        }

        if (currentItem == "typeDoorGlass") {
            $(".select_typeDoorGlass").attr("show", "is_alive_lement");
            goTo = "doorGlass";

            currentItemForDisplay = $("#namedoorGlass").html();
            //currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = "doorGlass";
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
        } else {
            $(".select_typeDoorGlass").attr("show", "ghost_lement");
        }

        if (currentItem == "toning") {
            if ($("#toning").attr("available") == "no") {
                return;
            }

            $(".select_toning").attr("show", "is_alive_lement");
            goTo = "doorGlass";

            currentItemForDisplay = $("#namedoorGlass").html();
            //currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = "doorGlass";
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
        } else {
            $(".select_toning").attr("show", "ghost_lement");
        }

        if (currentItem == "armor") {
            if ($("#armor").attr("available") == "no") {
                return;
            }
            $(".select_armor").attr("show", "is_alive_lement");
            goTo = "doorGlass";

            currentItemForDisplay = $("#namedoorGlass").html();
            //currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = "doorGlass";
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
        } else {
            $(".select_armor").attr("show", "ghost_lement");
        }

        if (currentItem == "topLockkit") {
            fillChildBlockFurniture("topLockkit", "topLock", "top");
            $(".select_topLockkit").attr("show", "is_alive_lement");
        } else {
            $(".select_topLockkit").attr("show", "ghost_lement");
        }

        if (currentItem == "topLock") {
            setСurrentItem("topLock");
            $(".select_topLock").attr("show", "is_alive_lement");
            goTo = "topLockkit";
            currentItemForDisplay = $("#nametopLockkit").html();
            currentItemForDisplayId = "topLockkit";
            displayListOfItems("topLock", availableFurnitureList.topLock, 0, "kit");
            PaginationPage.show();
        } else {
            $(".select_topLock").attr("show", "ghost_lement");
        }

        if (currentItem == "topInLockDecor") {
            $(".select_topInLockDecor").attr("show", "is_alive_lement");
            goTo = "topLockkit";
            currentItemForDisplay = $("#nametopLockkit").html();
            currentItemForDisplayId = "topLockkit";
            displayListOfItems("topInLockDecor", availableFurnitureList.topInLockDecor, 0, '');
            PaginationPage.show();
        } else {
            $(".select_topInLockDecor").attr("show", "ghost_lement");
        }

        if (currentItem == "topOutLockDecor") {
            $(".select_topOutLockDecor").attr("show", "is_alive_lement");
            goTo = "topLockkit";
            currentItemForDisplay = $("#nametopLockkit").html();
            currentItemForDisplayId = "topLockkit";
            displayListOfItems("topOutLockDecor", availableFurnitureList.topOutLockDecor, 0, '');
            PaginationPage.show();
        } else {
            $(".select_topOutLockDecor").attr("show", "ghost_lement");
        }

        if (currentItem == "lowerLockkit") {
            fillChildBlockFurniture("lowerLockkit", "lowerLock", "lower");
            $(".select_lowerLockkit").attr("show", "is_alive_lement");
        } else {
            $(".select_lowerLockkit").attr("show", "ghost_lement");
        }

        if (currentItem == "lowerLock") {
            setСurrentItem("lowerLock");
            $(".select_lowerLock").attr("show", "is_alive_lement");
            goTo = "lowerLockkit";
            currentItemForDisplay = $("#namelowerLockkit").html();
            currentItemForDisplayId = "lowerLockkit";
            displayListOfItems("lowerLock", availableFurnitureList.lowerLock, 0, '');
            PaginationPage.show();
        } else {
            $(".select_lowerLock").attr("show", "ghost_lement");
        }

        if (currentItem == "lowerInLockDecor") {
            $(".select_lowerInLockDecor").attr("show", "is_alive_lement");
            goTo = "lowerLockkit";
            currentItemForDisplay = $("#namelowerLockkit").html();
            currentItemForDisplayId = "lowerLockkit";
            displayListOfItems("lowerInLockDecor", availableFurnitureList.lowerInLockDecor, 0, '');
            PaginationPage.show();
        } else {
            $(".select_lowerInLockDecor").attr("show", "ghost_lement");
        }

        if (currentItem == "lowerOutLockDecor") {
            $(".select_lowerOutLockDecor").attr("show", "is_alive_lement");
            goTo = "lowerLockkit";
            currentItemForDisplay = $("#namelowerLockkit").html();
            currentItemForDisplayId = "lowerLockkit";
            displayListOfItems("lowerOutLockDecor", availableFurnitureList.lowerOutLockDecor, 0, '');
            PaginationPage.show();
        } else {
            $(".select_lowerOutLockDecor").attr("show", "ghost_lement");
        }

        if (currentItem == "handle") {
            setСurrentItem("handle");
            $(".select_handle").attr("show", "is_alive_lement");
            displayListOfItems("handle", availableFurnitureList.handle, 0, '');
            PaginationPage.show();
        } else {
            $(".select_handle").attr("show", "ghost_lement");
        }

        if (currentItem == "additionally") {
            $(".select_additionally").attr("show", "is_alive_lement");
        } else {
            $(".select_additionally").attr("show", "ghost_lement");
        }

        if (currentItem == "closer") {
            $(".select_closer").attr("show", "is_alive_lement");
            currentItemForDisplay = $("#nameadditionally").html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = "additionally";
            displayListOfItems("closer", availableFurnitureList.closer, 0, '');
            PaginationPage.show();
        } else {
            $(".select_closer").attr("show", "ghost_lement");
        }

        if (currentItem == "endDoorLock") {
            $(".select_endDoorLock").attr("show", "is_alive_lement");
            currentItemForDisplay = $("#nameadditionally").html();
            currentItemDaughterForDisplay = $(item).html();
            currentItemForDisplayId = "additionally";
        } else {
            $(".select_endDoorLock").attr("show", "ghost_lement");
        }

        if (currentItem == "topLockCylinder") {
            $(".select_topLockCylinder").attr("show", "is_alive_lement");
            goTo = "topLockkit";
            currentItemForDisplay = $("#nametopLockkit").html();
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
        } else {
            $(".select_topLockCylinder").attr("show", "ghost_lement");
        }

        if (currentItem == "lowerLockCylinder") {
            $(".select_lowerLockCylinder").attr("show", "is_alive_lement");
            goTo = "lowerLockkit";
            currentItemForDisplay = $("#namelowerLockkit").html();
            displayListOfItems(currentItem, availableFurnitureList[currentItem], 0, '');
            PaginationPage.show();
        } else {
            $(".select_lowerLockCylinder").attr("show", "ghost_lement");
        }

        //shield

        if (currentItem == "shieldKit") {
            fillChildBlockShield("shieldColor");
            fillChildBlockShield("shieldDesign");
            fillChildBlockShield("shieldGlass");
            displayChildFields();
            showShieldGlass();

            $(".select_shieldKit").attr("show", "is_alive_lement");
        } else {
            $(".select_shieldKit").attr("show", "ghost_lement");
        }

        if (currentItem == "shieldColor") {
            $(".select_shieldColor").attr("show", "is_alive_lement");
            goTo = "shieldKit";
            currentItemForDisplay = $("#nameshieldKit").html();

            currentItemForDisplayId = "shieldKit";
            displayImage("shieldColor", availableFurnitureList.shieldColor, 0);
            PaginationPage.show();
        } else {
            $(".select_shieldColor").attr("show", "ghost_lement");
        }

        if (currentItem == "shieldDesign") {
            $(".select_shieldDesign").attr("show", "is_alive_lement");
            goTo = "shieldKit";
            currentItemForDisplay = $("#nameshieldKit").html();

            currentItemForDisplayId = "shieldKit";
            displayImage("shieldDesign", availableFurnitureList.shieldDesign, 0);
            PaginationPage.show();
        } else {
            $(".select_shieldDesign").attr("show", "ghost_lement");
        }

        if (currentItem == "shieldGlass") {
            $(".select_shieldGlass").attr("show", "is_alive_lement");
            goTo = "shieldKit";
            currentItemForDisplay = $("#nameshieldKit").html();

            currentItemForDisplayId = "shieldKit";

            displayImage("shieldGlass", addToShieldGlassList(), 0);


            PaginationPage.show();
        } else {
            $(".select_shieldGlass").attr("show", "ghost_lement");
        }

        if (currentItem == "comment") {
            $(".select_comment").attr("show", "is_alive_lement");
        } else {
            $(".select_comment").attr("show", "ghost_lement");
        }

        if (!hasAParination(currentItem)) {
            PaginationPage.hide();
        }
    }

    function setAvailableChildrenAttr() {
        if ($("#typeDoorGlassShow").text() == "" || $("#typeDoorGlassShow").text() == "нет") {
            $("#toning").attr("available", "no");
            $("#armor").attr("available", "no");
            $("#input_glassWidth").attr("available", "no");
            $("#input_glassHeight").attr("available", "no");
            $("#input_leftGlassPosition").attr("available", "no");
            $("#input_bottomGlassPosition").attr("available", "no");
        } else {
            $("#toning").attr("available", "yes");
            $("#armor").attr("available", "yes");
            $("#input_glassWidth").attr("available", "yes");
            $("#input_glassHeight").attr("available", "yes");
            $("#input_leftGlassPosition").attr("available", "yes");
            $("#input_bottomGlassPosition").attr("available", "yes");
        }

    }

    function hasAParination() {
        if ("shieldColor" == currentItem ||
            "shieldDesign" == currentItem ||
            "shieldGlass" == currentItem ||
            "doorColor" == currentItem ||
            'topLock' == currentItem ||
            "lowerLock" == currentItem ||
            'closer' == currentItem ||
            'topLockCylinder' == currentItem ||
            'lowerLockCylinder' == currentItem ||
            'handle' == currentItem ||
            'lowerOutLockDecor' == currentItem ||
            'lowerInLockDecor' == currentItem ||
            'topOutLockDecor' == currentItem ||
            'topInLockDecor' == currentItem ||
            'typeDoorGlass' == currentItem ||
            'toning' == currentItem ||
            'armor' == currentItem ||
            'doorGlass' == currentItem
        ) {
            return true;
        }
        return false;
    }

    function addToShieldGlassList() {
        let glassList = [];
        for (let i = 0; i < availableFurnitureList.shieldGlass.length; i++){
            if (availableFurnitureList.shieldGlass[i].containsDesign == door.shieldKit.shieldDesign.id){
                glassList.push(availableFurnitureList.shieldGlass[i]);
            }
        }
        return glassList;
    }

    function addToTheHistoryList(val) {
        historyList[currentHisPoint] = val;
        currentHisPoint++;
    }

    function backHistoryList() {
        if (historyList == null) {
            return;
        }
        var sizeHis = historyList.length;
        var index = currentHisPoint - 2;
        if (sizeHis > 0 && index < sizeHis && !(index < 0)) {
            currentItem = historyList[index];
            hideShowField(false);
            currentHisPoint--;
        }
    }

    function nextHistoryList() {
        if (historyList == null) {
            return;
        }
        var sizeHis = historyList.length;
        var index = currentHisPoint;
        if (sizeHis > 0 && index < sizeHis && !(index < 0)) {
            currentItem = historyList[index];
            hideShowField(false);
            currentHisPoint++;
        }
    }

    function addNumberToSize(umber) {
        var currentNamber = $(".line").text();
        if (firstPress) {
            currentNamber = "";
        }
        $(".line").text(currentNamber + umber);
        firstPress = false;
    }

    function deleteLastNumberInSize() {
        var currentNamber = $(".line").text();
        var length = currentNamber.length;
        var newNamber = currentNamber.slice(0, length - 1);
        $(".line").text(newNamber);
        firstPress = false;
    }

    function handleKeystroke(e) {
        if (e.which == 48) {
            addNumberToSize(0);
        } else if (e.which == 49) {
            addNumberToSize(1);
        } else if (e.which == 50) {
            addNumberToSize(2);
        } else if (e.which == 51) {
            addNumberToSize(3);
        } else if (e.which == 52) {
            addNumberToSize(4);
        } else if (e.which == 53) {
            addNumberToSize(5);
        } else if (e.which == 54) {
            addNumberToSize(6);
        } else if (e.which == 55) {
            addNumberToSize(7);
        } else if (e.which == 56) {
            addNumberToSize(8);
        } else if (e.which == 57) {
            addNumberToSize(9);
        } else if (e.which == 8) {
            deleteLastNumberInSize(); //backspace
        }
    }

    function checkForLimits() {
        var namber = parseInt($(".line").text());
        if (namber < sizeMin || namber > sizeMax) {
            $(".line").addClass("redColor");
            $("#select_set").addClass("notAvailable");
        } else {
            $(".line").removeClass("redColor");
            $("#select_set").removeClass("notAvailable");
        }
    }

    function fillChildBlockFurniture(grupName, name, position) {
        //set Child field
        var val = $("#" + grupName + "Show").html();
        $("#" + name + "Show").html(val);

        //show
        var javaFurniture = door.furnitureKit[name];
        var cylinder = door.furnitureKit[name + "Cylinder"];
        var inLockDecor = door.furnitureKit[position + "InLockDecor"];
        var outLockDecor = door.furnitureKit[position + "OutLockDecor"];
        var inLockDecor = door.furnitureKit[position + "InLockDecor"];
        var outLockDecor = door.furnitureKit[position + "OutLockDecor"];

        if (javaFurniture != null) {
            //Cylinder
            showCylinderLock(name, javaFurniture.itCylinderLock, cylinder);
            //Decoration
            availableLockDecoration(position, "In", inLockDecor, true);
            availableLockDecoration(position, "Out", outLockDecor, true);
        } else {
            showCylinderLock(name, false);
            availableLockDecoration(position, "In", inLockDecor, false);
            availableLockDecoration(position, "Out", outLockDecor, false);
        }
    }

    function fillChildBlockShield(name) {
        var jObject = door.shieldKit[name];
        if (jObject != null) {
            $("#" + name + "Show").html(jObject.name);
        } else {
            $("#" + name + "Show").html("");
        }
    }

    function showCylinderLock(name, itCylinderLock, cylinder) {
        if (itCylinderLock) {
            $("#" + name + "Cylinder").removeClass("ghost");
        } else {
            $("#" + name + "Cylinder").addClass("ghost");
        }
        if (cylinder instanceof Object) {
            $("#" + name + "CylinderShow").text(cylinder.name);
        } else {
            $("#" + name + "CylinderShow").text("");
        }
    }

    function showDecorationLock(position, side, decor) {
        if (decor instanceof Object) {
            $("#" + position + side + "LockDecorShow").text(decor.name);
        } else {
            $("#" + position + side + "LockDecorShow").text("");
        }
    }

    function availableLockDecoration(position, side, decor, show) {
        var value = "no";
        if (show) {
            value = "yes";
            showDecorationLock(position, side, decor);
        }
        $("#name" + position + side + "LockDecor").attr("available", value);
        $("#name" + position + side + "LockDecor").attr("available", value);
    }

    function setСurrentItem(name) {
        var elem = $(".div_images_furniture[Item=" + name + "]");
        var furnitureItem = door.furnitureKit[name];
        var id = 0;
        if (furnitureItem != null) {
            id = furnitureItem.id;
        }
        elem
            .filter(function (index) {
                return $(this).attr("data") == id;
            })
            .attr("check", "checkbox");
    }

    function setСurrentColor() {
        var elem = $(".div_images_Color");
        var furnitureItem = door.doorColor;
        var id = 0;
        if (furnitureItem != null) {
            id = furnitureItem;
        }
        elem
            .filter(function (index) {
                return $(this).attr("data") == id;
            })

            .attr("check", "checkbox");
    }

    function clearRelatedFieldsForImage(javaObject) {
        if (javaObject == null || (javaObject.typeOfImage == "SHIELD_COLOR" && javaObject.containsDesign == 1)) {
            setDoorFurnitureByObject(null, "shieldDesign", door.shieldKit)
        }
    }

    function displayChildFields() {
        let shieldColor = door.shieldKit.shieldColor;
        if (shieldColor == null || (shieldColor != null && shieldColor.containsDesign == 1)) {
            $("#shieldDesign").attr("show", "ghost_lement");
        } else {
            $("#shieldDesign").attr("show", "is_alive_lement");
        }
    }

    function showShieldGlass() {
        let shieldDesign = door.shieldKit.shieldDesign;
        if (shieldDesign == null || (shieldDesign != null && shieldDesign.containsGlass == 0)) {
            $("#shieldGlass").attr("show", "ghost_lement");
            setDoorFurnitureByObject(null, "shieldGlass", door.shieldKit);
        } else {
            $("#shieldGlass").attr("show", "is_alive_lement");
        }
    }

    function clearGlass(javaObject) {
        if (javaObject.containsDesign == 0){
            setDoorFurnitureByObject(null, "shieldGlass", door.shieldKit);
        }
    }

});
