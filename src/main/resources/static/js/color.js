jQuery("document").ready(function () {
    var JavaObject;
    var typeImageList;
    var typeDoorColorList;
    let colorListPictures;
    let shieldDesign;

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
        setField("typeOfImage", $("#typeOfImage").val());
        if ($("#typeOfImage").val() != "") {
            showContainsDesignGlass();
            showContainsGlass();
            getImageListFromServer();
            getShieldGlass();
        } else {
            fillInTypes("#picturePath", "");
            showPicture("#picturePath_img", "");
        }

    });

    $("#typeOfDoorColor").change(function () {
        setField("typeOfDoorColor", $("#typeOfDoorColor").val());
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
        }else {
            setField("picturePath", "");
            showPicture("#picturePath_img", "");
        }
    });

    $("#picturePathDesign").change(function () {
        if ($("#picturePathDesign").val() != "") {
        setField("containsDesign", getPictureColor($("#picturePathDesign").val(), shieldDesign).id);
        showPicture("#picturePathDesign_img", getPictureColor($("#picturePathDesign").val(), shieldDesign).picturePath);
    }else {
            setField("containsDesign", 0);
            showPicture("#picturePathDesign_img", "");
        }
    });

    function getPictureColor(idPicture, picturesList) {
        for (let i = 0; i < picturesList.length; i++) {
            if (picturesList[i].id == idPicture) {
                return picturesList[i];
            }
        }
    }

    function getPictureColorPath(pathPicture) {
        for (let i = 0; i < colorListPictures.length; i++) {
            if (colorListPictures[i].path == pathPicture) {
                return colorListPictures[i];
            }
        }
    }

    function getImageListFromServer() {
        $.ajax({
            url: location.origin + "/color/image-file-list/" + JavaObject.typeOfImage,
            dataType: "json",
            success: function (data) {
                colorListPictures = data;
                pathImageList = data;
                fillInTypesPictures("#picturePath", pathImageList);
            },
            error: function (data) {
                alert("!ERROR: изображений получить не удалось:");
            },
        });
    }

    function getShieldDesignFromServer() {
        $.ajax({
            url: location.origin + "/color/shieldDesign",
            dataType: "json",
            success: function (data) {
                shieldDesign = data;
                fillInTypesPictures("#picturePathDesign", shieldDesign);
                if (JavaObject.containsDesign != 0 ) {
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
        var furniture = JSON.stringify(JavaObject);

        $.ajax({
            url: "item",
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: furniture,
            success: function (data) {
                //alert(data.status);
                toList();
            },
            error: function (data) {
                alert("!ERROR: елемнет записать не удалось:");
            },
        });
    });

    $("#close").on("click", function () {
        toList();
    });

    $("#delete").on("click", function () {
        $.ajax({
            url: "" + getIdFromUrl(),
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
        location.pathname = "color";
    }

    function getJavaObject() {
        $.ajax({
            url: "item/" + getIdFromUrl(),
            dataType: "json",
            success: function (data) {
                JavaObject = data;
                fillByOject();
            },
            error: function (data) {
                alert("!ERROR: елемнет фурнитуры получить не удалось:");
            },
        });
    }

    function getIdFromUrl() {
        var url = location.href;
        var id = url.substring(url.lastIndexOf("/") + 1);
        return id;
    }

    function showContainsGlass() {
        if(JavaObject.typeOfImage == "SHIELD_DESIGN"){
            //$("#containsGlassDiv").hidden = false;
            $("#containsGlassDiv").show();
        } else {
            $("#containsGlassDiv").hide();
            //$("#containsGlassDiv").hidden = true;
        }
    }

    function showContainsDesignGlass() {
        if (JavaObject.typeOfImage == "SHIELD_GLASS") {
            $("#containsDesignGlass").show();
        } else {
            $("#containsDesignGlass").hide();
        }
    }

    function getPictureShieldDesign(idItem) {
        for (let i = 0; i < shieldDesign.length; i++) {
            if (shieldDesign[i].id == idItem) {
                return shieldDesign[i];
            }
        }
    }


    function fillByOject() {
        if (JavaObject != null) {
            showContainsDesignGlass();
            showContainsGlass();
            if (JavaObject.typeOfImage != null) {
                getImageListFromServer();
                getShieldGlass();
            }

            $("#id").val(JavaObject.id);
            $("#idManufacturerProgram").val(JavaObject.idManufacturerProgram);
            $("#name").val(JavaObject.name);
            setCheckBox("#smooth", JavaObject.smooth);
            setCheckBox("#containsGlass", JavaObject.containsGlass);
            showPicture("#picturePath_img", JavaObject.picturePath);

            $("#pricePaintingMeterOfSpace").val(JavaObject.pricePaintingMeterOfSpace);
            setValueInSelect("#typeOfImage", JavaObject.typeOfImage);

            setValueInSelect("#typeOfDoorColor", JavaObject.typeOfDoorColor);

            if (JavaObject.typeOfImage == "SHIELD_GLASS"){
            } else {
                setCheckBox("#containsDesign", JavaObject.containsDesign);
            }

        }
    }


    function getShieldGlass() {
        if (JavaObject.typeOfImage == "SHIELD_GLASS"){
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
        $.ajax({
            url: location.origin + "/color/door-color/types",
            dataType: "json",
            success: function (data) {
                typeDoorColorList = data;
                fillInTypes("#typeOfDoorColor", typeDoorColorList);
            },
            error: function (data) {
                alert("!ERROR: типы дверных цветов получить не удалось:");
            },
        });
    }

    function fillInTypesPictures(selectNmae, types) {
        if (types != null) {
            $(selectNmae).empty();

            $(selectNmae).append($("<option></option>"));

            for (var i = 0; i < types.length; ++i) {
                $(selectNmae).append(
                    $("<option value=" + types[i].id + ">" + types[i].name + "</option>")
                );
            }

            if (selectNmae == "#picturePath"){
                if (JavaObject.picturePath != null) {
                    setValueInSelect("#picturePath", getPictureColorPath(JavaObject.picturePath).id);
                }
            }
            if (selectNmae == "#picturePathDesign"){
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

    function setValueInSelect(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }
});

function showPicture(divSelect, value) {
    $(divSelect).attr("src", "../" + value);
}

