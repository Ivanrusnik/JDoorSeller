jQuery("document").ready(function () {
    let doorClassList;
    let specificationEtity = {};
    let doorClassAllList = [];
    var lineSpecifications;
    let addLineBoolean = false;
    let itemId;
    let selectedLineId;
    var currentLine = 0;

    getListDoorClassToSelect();
    setSpecificationId("Спецификация");
    checkNewOrNot();

    $("#name").change(function () {
        setField("name", $("#name").val());
    });

    $("#modelOfDoor").change(function () {
        let doorClassId = $("#modelOfDoor").val();
        setField("doorType", searchDoorType(doorClassId));
    });

    $("#doorclassselect").change(function () {
        fillInDoorType(getDoorClassbyId($("#doorclassselect").val()));
    });

    $("#close").on("click", function () {
        toList();
    });

    $("#save").on("click", function () {
        saveSpecoficationEntity();
    });

    $("#delete").on("click", function () {
        $.ajax({
            url: location.origin + "/specifications/" + getIdFromUrl(),
            method: "DELETE",
            dataType: "json",
            success: function (data) {
                toList();
            },
            error: function (data) {
                alert("!ERROR: елемнет удалить не удалось:");
            },
        });
    });

    function deleteLineSpecification() {
        $.ajax({
            url: location.origin + "/specification/line/" + selectedLineId,
            method: "DELETE",
            success: function (data) {
            },
            error: function (data) {
                alert("!ERROR: елемнет удалить не удалось:");
            },
        });
    }

    function saveSpecoficationEntity() {
        if (lineSpecifications != null) {
            specificationEtity.lineSpecifications = lineSpecifications;
        }else {
            specificationEtity.lineSpecifications = [];
        }
        let specification = JSON.stringify(specificationEtity);

        $.ajax({
            url: location.origin + "/specifications",
            method: "PUT",
            contentType: "application/json",
            data: specification,
            success: function (data) {
                //alert(data);
                if (addLineBoolean == true){
                    itemId = data.id;
                    toFormAddScpecLine(itemId);
                } else {
                    toList();
                }

            },
            error: function (data) {
                alert("!ERROR: елемнет записать не удалось:");
            },
        });
    }

    function checkNewOrNot() {
        if (getIdFromUrl() != 0) {
            getSpecificationEtity();
        }
    }

    function setField(fieldName, value) {
        specificationEtity[fieldName] = value;
    }

    function toList() {
        location.pathname = "pages/specifications";
    }

    function searchDoorType(doorClassId) {
        for (let i = 0; i < doorClassAllList.length; i++) {
            if (doorClassAllList[i].id == doorClassId) {
                return doorClassAllList[i];
            }
        }
    }

    function setSpecificationId(name) {
        $("#specificationIdName").text(name + " №: " + getIdFromUrl());
    }

    function getIdFromUrl() {
        var url = location.href;
        var id = url.substring(url.lastIndexOf("/") + 1);
        return id;
    }

    function fillInSpecificationEntity() {
        $("#name").val(specificationEtity.name);
        setValueInSelect("#modelOfDoor", specificationEtity.doorType.id);
        fillTabLine();

    }

    function setValueInSelect(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function getSpecificationEtity() {
        $.ajax({
            url: location.origin + "/specifications/" + getIdFromUrl(),
            dataType: "json",
            success: function (data) {
                specificationEtity = data;
                lineSpecifications = data.lineSpecifications;
                fillInSpecificationEntity();
            },
            error: function (data) {
                alert("!ERROR: данные о классах получить не удалось:" + data);
            },
        });
    }

    function getListDoorClassToSelect() {
        $.ajax({
            url: "/class/list",
            success: function (data) {
                doorClassList = data;
                fillInDoorClass(doorClassList);
            },
            error: function (data) {
                alert("!ERROR: данные о классах получить не удалось:" + data);
            },
        });
    }

    function fillInDoorClass(listClass) {
        $("#modelOfDoor").empty();

        $("#modelOfDoor").append($("<option></option>"));
        for (var i = 0; i < listClass.length; ++i) {
            for (var a = 0; a < listClass[i].doorTypes.length; ++a) {
                doorClassAllList.push(listClass[i].doorTypes[a]);
                $("#modelOfDoor").append(
                    $(
                        "<option value=" +
                        listClass[i].doorTypes[a].id +
                        ">" + "(" + listClass[i].name + ") " +
                        listClass[i].doorTypes[a].name +
                        "</option>"
                    )
                );
            }
        }
    }

    function fillTabLine() {
        if (lineSpecifications == null) {
            return;
        }

        var sizelineSpec = lineSpecifications.length;
        for (var i = 0; i < sizelineSpec; i++) {
            addLine(
                lineSpecifications[i].id,
                lineSpecifications[i].name,
                lineSpecifications[i].value,
                lineSpecifications[i].formula,
                lineSpecifications[i].independentName,
                lineSpecifications[i].releaseOperation,
                lineSpecifications[i].writeOffOperation
            );
        }
    }

    function addLine(
        id,
        newName,
        newValue,
        newformula,
        independentName,
        releaseOperation,
        writeOffOperation
    ) {
        var Position = getNextPosition();

        $(".Table > tbody").append(
            '<tr class="line newLine" id="line' +
            Position +
            '">' +
            '<td class="position">' +
            Position +
            "</td>" +
            '<td class="id" id="id' +
            Position +
            '">' +
            id +
            "</td>" +
            '<td class="vary_field text_select" id="name' +
            Position +
            '">' +
            newName +
            "</td>" +
            '<td class="vary_field text_input" id="value' +
            Position +
            '">' +
            newValue +
            "</td>" +
            '<td class="vary_field text_input" id="formula' +
            Position +
            '" >' +
            newformula +
            "</td>" +
            '<td class="vary_field text_input" id="independentName' +
            Position +
            '" >' +
            independentName +
            "</td>" +
            '<td class="vary_field text_input" id="releaseOperation' +
            Position +
            '" >' +
            releaseOperation +
            "</td>" +
            '<td class="vary_field text_input" id="writeOffOperation' +
            Position +
            '" >' +
            writeOffOperation +
            "</td>" +
            "</tr>"
        );

        if (id == "") {
            var elementPosition = $("#line" + Position).offset().top;
            $("body,html").animate({scrollTop: elementPosition}, 500);
        }
    }

    function getNextPosition() {
        var posElem = $(".position");
        if (posElem != null) {
            return posElem.length + 1;
        }
        return 0 + 1;
    }

    function displayComponentVisibility() {
        $("#deletLine").removeClass("disabled");
    }

    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#edit").blur();
        }
    });

    function toFormAddScpecLine(item) {
        location.href =  location.origin + "/pages/specification/" + item + "/line/" + "0";
    }

    $("#addLine").click(function () {
        addLineBoolean = true;
        if (getIdFromUrl() == 0){
            saveSpecoficationEntity();
        }else {
            toFormAddScpecLine(getIdFromUrl());
        }
    });

    $("#deletLine").click(function () {
        if ($(this).hasClass("disabled")) {
            return;
        }
        if (selectedLineId != ""){
            deleteLineSpecification();
        }
        $("#line" + currentLine).remove();
    });

    $("tbody").on("click", "tr", function () {
        currentLine = $(this).children(".position").text();
        selectedLineId = $(this).children(".id").text();
        $("#deletLine").removeClass("disabled");
        oneEnableAllDisable(this);
        displayComponentVisibility();
    });

    function oneEnableAllDisable(item) {
        var elems = $('tr[pickOut="on"]');
        var elemsTotal = elems.length;

        for (var i = 0; i < elemsTotal; ++i) {
            $(elems[i]).attr("pickOut", "off");
        }
        $(item).attr("pickOut", "on");
    }

    $("tbody").on("dblclick", "tr", function () {
        getItem($(this).children(".id").text());
    });

    function getItem(id) {
        location.href =  location.origin + "/pages/specification/" + getIdFromUrl() + "/line/" + id;
    }

});