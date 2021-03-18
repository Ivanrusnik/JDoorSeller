jQuery("document").ready(function () {

    var JavaObject;
    var typeImageList;
    var typeDoorColorList;
    let colorListPictures;
    let imageMaskPathList;
    let shieldDesign;
    let allImageForLimit;
    let javaLimitList = [];

    //new instans
    getTypeList();
    getJavaObject();

    $("#idManufacturerProgram").change(function () {
        setField("idManufacturerProgram", $("#idManufacturerProgram").val());
    });

    $("#name").change(function () {
        setField("name", $("#name").val());
    });

    $("#typeOfImage").change(function () {
        const type = $("#typeOfImage").val();

        setField("typeOfImage", type);

        if (type != "") {
            showSettings(JavaObject.typeOfImage);
            getImageFileListFromServer();
            getAllImagesFromServer();
            getShieldGlass();
            if (type == "DOOR_COLOR") {
                getTypeListColor();
            } else if (type == "SHIELD_DESIGN") {
                getTypeListShield();
                addLine(".limitLine");
            }
        } else {
            fillInTypes("#picturePath", "");
            showPicture("#picturePath_img", "");
        }

    });

    $("#typeOfDoorColor").change(function () {

        const type = $("#typeOfImage").val();
        if (type == "DOOR_COLOR") {
            setField("typeOfDoorColor", $("#typeOfDoorColor").val());
        } else if (type == "SHIELD_DESIGN") {
            setField("typeOfShieldDesign", $("#typeOfDoorColor").val());
        }
    });

    $("#smooth").change(function () {
        if ($(this).is(":checked")) {
            setField("smooth", 1);
        } else {
            setField("smooth", 0);
        }
    });

    $("#containsDesign").change(function () {
        if ($(this).is(":checked")) {
            setField("containsDesign", 1);
        } else {
            setField("containsDesign", 0);
        }
    });

    $("#containsGlass").change(function () {
        if ($(this).is(":checked")) {
            setField("containsGlass", 1);
        } else {
            setField("containsGlass", 0);
        }
    });

    $("#picturePath").change(function () {
        if ($("#picturePath").val() != "") {
            setField("picturePath", getPictureColor($("#picturePath").val(), colorListPictures).path);
            showPicture("#picturePath_img", getPictureColor($("#picturePath").val(), colorListPictures).path);
        } else {
            setField("picturePath", "");
            showPicture("#picturePath_img", "");
        }
    });

    $("#picturePathDesign").change(function () {
        if ($("#picturePathDesign").val() != "") {
            setField("containsDesign", getPictureColor($("#picturePathDesign").val(), shieldDesign).id);
            showPicture("#picturePathDesign_img", getPictureColor($("#picturePathDesign").val(), shieldDesign).picturePath);
        } else {
            setField("containsDesign", 0);
            showPicture("#picturePathDesign_img", "");
        }
    });

    $("#outWoodPanel").change(function () {
        if ($(this).is(":checked")) {
            setField("containsWoodPanel", 1);
        } else {
            setField("containsWoodPanel", 0);
            setField("maskPath", "");
        }
        showMask();
    });

    $("#outWoodMaskPath").change(function () {
        if ($("#outWoodMaskPath").val() != "") {
            setField("maskPath", getPictureColor($("#outWoodMaskPath").val(), imageMaskPathList).path);
        } else {
            setField("maskPath", "");
        }
        showPicture("#mask_png", JavaObject.maskPath);
    });

    $("#containsOtherColor").change(function () {
        if ($(this).is(":checked")) {
            setField("containsOtherColor", 1);
        } else {
            setField("containsOtherColor", 0);
            setField("maskPath", "");
        }
        showMask();
    });

    $("#limitDiv").change(".limitLine", function () {
        saveToServerList(".limitLine");
        addLine(".limitLine");
    });

    function getPictureColor(idPicture, picturesList) {
        for (let i = 0; i < picturesList.length; i++) {
            if (picturesList[i].id == idPicture) {
                return picturesList[i];
            }
        }
    }

    function getPictureColorPath(pathPicture) {
        if (colorListPictures) {
            for (let i = 0; i < colorListPictures.length; i++) {
                if (colorListPictures[i].path == pathPicture) {
                    return colorListPictures[i];
                }
            }
        }
        return {id: 0};
    }

    function getPictureMaskPath(pathPicture) {
        const defaultResponce = {id: 0};
        if (pathPicture == null) {
            return defaultResponce;
        }
        for (let i = 0; i < imageMaskPathList.length; i++) {
            if (imageMaskPathList[i].path == pathPicture) {
                return imageMaskPathList[i];
            }
        }
        return defaultResponce;
    }

    function getImageFileListFromServer() {
        return $.ajax({
            url: location.origin + "/color/image-file-list/" + JavaObject.typeOfImage,
            dataType: "json",
            async: false,
            success: function (data) {
                colorListPictures = data;
                fillInSelector("#picturePath", data);
                return data;
            },
            error: function (data) {
                alert("!ERROR: файлы изображений получить не удалось:");
            },
        });
    }

    function getImageMaskListFromServer() {
        return $.ajax({
            url: location.origin + "/color/image-file-list/" + JavaObject.typeOfImage + "/masks",
            dataType: "json",
            async: false,
            success: function (data) {
                imageMaskPathList = data;
                fillInSelector("#outWoodMaskPath", imageMaskPathList);
                return data;
            },
            error: function (data) {
                alert("!ERROR: масок изображений получить не удалось:");
            },
        });
    }

    function getShieldDesignFromServer() {
        $.ajax({
            url: location.origin + "/color/shieldDesign",
            dataType: "json",
            success: function (data) {
                shieldDesign = data;
                fillInSelector("#picturePathDesign", shieldDesign);
                if (JavaObject.containsDesign != 0) {
                    showPicture("#picturePathDesign_img", getPictureShieldDesign(JavaObject.containsDesign).picturePath);
                }
            },
            error: function (data) {
                alert("!ERROR: изображений получить не удалось:");
            },
        });
    }

    $("#pricePaintingMeterOfSpace").change(function () {
        setField(
            "pricePaintingMeterOfSpace",
            $("#pricePaintingMeterOfSpace").val()
        );
    });

    $("#save").on("click", function () {

        let res = sendJavaObjectToServer();
        let id = res.responseJSON.model.id;
        res = sendLimitToServer(id);

        if (JavaObject.id == 0) {
            toPage(id);
        } else {
            toList();
        }
    });

    $("#close").on("click", function () {
        toList();
    });

    $("#delete").on("click", function () {
        $.ajax({
            url: "/colors/" + getIdFromUrl(),
            method: "DELETE",
            dataType: "json",
            success: function (data) {
                alert(data.status);
                toList();
            },
            error: function (data) {
                alert("!ERROR: елемнет удалить не удалось:");
            },
        });
    });

    function toList() {
        location.pathname = "/pages/colors";
    }

    function toPage(id) {
        location.pathname = "/pages/colors/" + id;
    }

    function getJavaObject() {
        $.ajax({
            url: "/colors/" + getIdFromUrl(),
            dataType: "json",
            success: function (data) {
                JavaObject = data;
                fillByObject();
            },
            error: function (data) {
                alert("!ERROR: main image получить не удалось:");
            },
        });
    }

    function getIdFromUrl() {
        var url = location.href;
        var id = url.substring(url.lastIndexOf("/") + 1);
        return id;
    }

    function showSettings(type) {

        hideAllSettings();

        if (type == "SHIELD_DESIGN") {
            $("#containsGlassDiv").show();
            $("#additionalTypeDiv").show();
            $("#containsOtherColorDiv").show();
            $("#limitDiv").show();
            $("#limitChapter").show();
        } else if (type == "SHIELD_GLASS") {
            $("#containsDesignGlass").show();
        } else if (type == "DOOR_COLOR") {
            $("#additionalTypeDiv").show();
        } else if (type == 'DOOR_DESIGN') {
            $("#outWoodPanelDiv").show();
        }
    }

    function hideAllSettings() {
        $("#containsGlassDiv").hide();
        $("#containsDesignGlass").hide();
        $("#smoothSwitchDiv").hide();
        $("#additionalTypeDiv").hide();
        $("#containsDesignSwitchDiv").hide();
        $("#outWoodPanelDiv").hide();
        $("#containsOtherColorDiv").hide();
        $("#limitDiv").hide();
        $("#limitChapter").hide();
    }

    function showMask() {
        if ($("#outWoodPanel").is(":checked") || $("#containsOtherColor").is(":checked")) {
            $('#outWoodMaskPathDiv').show();
            $('#mask_png').show();
        } else {
            $('#outWoodMaskPathDiv').hide();
            $('#mask_png').hide();
        }

    }

    function getPictureShieldDesign(idItem) {
        for (let i = 0; i < shieldDesign.length; i++) {
            if (shieldDesign[i].id == idItem) {
                return shieldDesign[i];
            }
        }
    }

    function fillByObject() {
        if (JavaObject != null) {

            if (JavaObject.typeOfImage != null) {
                let responce = getImageFileListFromServer();
                responce = getImageMaskListFromServer();
                getShieldGlass();
            }
            showSettings(JavaObject.typeOfImage);

            $("#id").val(JavaObject.id);
            $("#idManufacturerProgram").val(JavaObject.idManufacturerProgram);
            $("#name").val(JavaObject.name);

            setCheckBox("#smooth", JavaObject.smooth);
            setCheckBox("#containsGlass", JavaObject.containsGlass);

            setValueInSelect("#picturePath", getPictureColorPath(JavaObject.picturePath).id);
            showPicture("#picturePath_img", JavaObject.picturePath);

            $("#pricePaintingMeterOfSpace").val(JavaObject.pricePaintingMeterOfSpace);
            setValueInSelect("#typeOfImage", JavaObject.typeOfImage);

            if (JavaObject.typeOfImage) {
                const type = $("#typeOfImage").val();
                if (type == "DOOR_COLOR") {
                    getTypeListColor();
                    setValueInSelect("#typeOfDoorColor", JavaObject.typeOfDoorColor);
                } else if (type == "SHIELD_DESIGN") {
                    getTypeListShield();
                    setValueInSelect("#typeOfDoorColor", JavaObject.typeOfShieldDesign);
                    const res = getAllImagesFromServer();
                    getLimitsFromServer();
                }
            }

            setValueInSelect("#outWoodMaskPath", getPictureMaskPath(JavaObject.maskPath).id);
            showPicture("#mask_png", JavaObject.maskPath);

            if (JavaObject.typeOfImage == "SHIELD_GLASS") {
            } else {
                setCheckBox("#containsDesign", JavaObject.containsDesign);
            }

            setCheckBox("#outWoodPanel", JavaObject.containsWoodPanel);
            showPicture("#mask_png", JavaObject.maskPath);

            setCheckBox("#containsOtherColor", JavaObject.containsOtherColor);

            showMask();
        }
    }

    function getShieldGlass() {
        if (JavaObject.typeOfImage == "SHIELD_GLASS") {
            getShieldDesignFromServer();
        }
    }

    function setField(fieldName, value) {
        JavaObject[fieldName] = value;
    }

    function setCheckBox(name, value) {
        if (value == 1) {
            $(name).prop("checked", true);
        }
    }

    function getTypeList() {
        $.ajax({
            url: location.origin + "/image/types",
            dataType: "json",
            success: function (data) {
                typeImageList = data;
                fillInTypes("#typeOfImage", typeImageList);
            },
            error: function (data) {
                alert("!ERROR: типы изображений получить не удалось:");
            },
        });
    }

    function getTypeListColor() {
        $.ajax({
            url: location.origin + "/color/types/color",
            dataType: "json",
            success: function (data) {
                typeDoorColorList = data;
                fillInTypesName("#typeOfDoorColor", typeDoorColorList);
                setValueInSelect("#typeOfDoorColor", JavaObject.typeOfDoorColor);
            },
            error: function (data) {
                alert("!ERROR: типы дверных цветов получить не удалось:");
            },
        });
    }

    function getTypeListShield() {
        $.ajax({
            url: location.origin + "/colors/shield-designs/types/buttons",
            dataType: "json",
            success: function (data) {
                typeDoorColorList = data;
                fillInTypesName("#typeOfDoorColor", typeDoorColorList);
                setValueInSelect("#typeOfDoorColor", JavaObject.typeOfShieldDesign);
            },
            error: function (data) {
                alert("!ERROR: типы дверных цветов получить не удалось:");
            },
        });
    }

    function fillInSelector(selectName, types) {
        if (types != null) {
            $(selectName).empty();

            $(selectName).append($("<option></option>"));

            for (var i = 0; i < types.length; ++i) {
                $(selectName).append(
                    $("<option value=" + types[i].id + ">" + types[i].name + "</option>")
                );
            }
            if (selectName == "#picturePathDesign") {
                if (JavaObject.containsDesign != 0) {
                    setValueInSelect("#picturePathDesign", JavaObject.containsDesign);
                }
            }

        }
    }

    function fillInTypes(selectNmae, types) {
        if (types != null) {
            $(selectNmae).empty();

            $(selectNmae).append($("<option></option>"));

            for (var i = 0; i < types.length; ++i) {
                $(selectNmae).append(
                    $("<option value=" + types[i] + ">" + types[i] + "</option>")
                );
            }
        }
    }

    function fillInTypesName(selectNmae, types) {
        if (types != null) {
            $(selectNmae).empty();

            $(selectNmae).append($("<option></option>"));

            for (var i = 0; i < types.length; ++i) {
                $(selectNmae).append(
                    $("<option value=" + types[i].type + ">" + types[i].name + "</option>")
                );
            }
        }
    }

    function setValueInSelect(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function getAllImagesFromServer() {
        return $.ajax({
            url: location.origin + "/colors",
            dataType: "json",
            async: false,
            success: function (data) {
                allImageForLimit = data;
                return data;
            },
            error: function (data) {
                alert("!ERROR: список цветов для ограничений получить не удалось:");
            },
        });
    }

    function saveToServerList(selector) {
        //delete all
        const size = javaLimitList.length;
        javaLimitList.splice(0, size);

        const elemList = $(selector);
        for (let i = 0; i < elemList.length; ++i) {
            if ($(elemList[i]).val()) {
                const id = $(elemList[i]).val();
                javaLimitList.push({id: id});
            }
        }
    }

    function fillAllSelectLimitByJavaList() {
        javaLimitList.forEach(function (item, i, arr) {
            const newLineSelector = createLineLimit();
            fillSelectLimit(newLineSelector);
            setValueInSelectInt(newLineSelector, item.id);
        });

        addLine(".limitLine");
    }

    function fillSelectLimit(selectName) {

        if (allImageForLimit != null) {

            $(selectName).empty();

            $(selectName).append($("<option></option>"));

            for (var i = 0; i < allImageForLimit.length; ++i) {
                $(selectName).append(
                    $("<option value=" + allImageForLimit[i].id + ">" + allImageForLimit[i].name + "</option>")
                );
            }
        }
    }

    function addLine(selector) {
        if (isAllLineFill(selector)) {
            const newLineSelector = createLineLimit();
            fillSelectLimit(newLineSelector);
        }
    }

    function isAllLineFill(selector) {
        const listElem = $(selector)
            .filter(function (index) {
                return $(this).val() == "";
            })

        return listElem.length == 0;
    }

    function createLineLimit() {
        const index = $(".limitLine").length + 1;
        const id = "limit" + index;

        $("<select>")
            .attr("class", "form-control limitLine")
            .attr("id", id)
            .appendTo("#limitColDiv");

        return "#" + id;
    }

    function sendLimitToServer(id) {
        let list = JSON.stringify(javaLimitList);

        return $.ajax({
            url: "/colors/" + id + "/limitations",
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: list,
            async: false,
            success: function (data) {
                return data;
            },
            error: function (data) {
                alert("!ERROR: ограничения для дизайна записать не удалось :");
            },
        });
    }

    function sendJavaObjectToServer() {
        let furniture = JSON.stringify(JavaObject);

        return $.ajax({
            url: "/colors",
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: furniture,
            async: false,
            success: function (data) {
                return data;
            },
            error: function (data) {
                alert("!ERROR: елемнет записать не удалось:");
            },
        });
    }

    function setValueInSelectInt(jqSelect, value) {
        let opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function getLimitsFromServer() {
        const id = JavaObject.id;

        if (id && id != 0) {
            $.ajax({
                url: location.origin + "/colors/" + id + "/limitations",
                dataType: "json",
                success: function (data) {
                    javaLimitList = data;
                    fillAllSelectLimitByJavaList();
                },
                error: function (data) {
                    alert("!ERROR: ограничения получить не удалось:");
                },
            });
        }

    }
});

function showPicture(divSelect, value) {
    $(divSelect).attr("src", "../../" + value);
}

