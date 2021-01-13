jQuery("document").ready(function () {
  var furnitureJavaObject;
  var types;

  //new instans
  getFurnitureJavaObject();

  $("#idManufacturFurniture").change(function () {
    setField("idManufacturFurniture", $("#idManufacturFurniture").val());
  });

  $("#nameFurniture").change(function () {
    setField("name", $("#nameFurniture").val());
  });

  $("#typeOfFurniture").change(function () {
    setField("typeOfFurniture", $("#typeOfFurniture").val());
  });

  $("#itCylinderLock").on("click", function () {
    if ($(this).is(":checked")) {
      setField("itCylinderLock", 1);
    } else {
      setField("itCylinderLock", 0);
    }
  });

  $("#comment").change(function () {
    setField("comment", $("#comment").val());
  });

  $("#picturePathFirst").change(function () {
    setField("picturePathFirst", $("#picturePathFirst").val());
  });

  $("#sketchPathFirst").change(function () {
    setField("sketchPathFirst", $("#sketchPathFirst").val());
  });

  $("#price").change(function () {
    setField("price", $("#price").val());
  });

  $("#priceComit").change(function () {
    setField("priceComit", $("#priceComit").val());
  });

  $("#save").on("click", function () {
    var furniture = JSON.stringify(furnitureJavaObject);

    $.ajax({
      url: "item",
      method: "PUT",
      dataType: "json",
      contentType: "application/json",
      data: furniture,
      success: function (data) {
        //alert(data.status);
        toFurnirure();
      },
      error: function (data) {
        alert("!ERROR: елемнет записать не удалось:");
      },
    });
  });

  $("#close").on("click", function () {
    toFurnirure();
  });

  $("#delete").on("click", function () {
    $.ajax({
      url: "" + getIdFromUrl(),
      method: "DELETE",
      dataType: "json",
      success: function (data) {
        alert(data.status);
        toFurnirure();
      },
      error: function (data) {
        alert("!ERROR: елемнет удалить не удалось:");
      },
    });
  });

  function toFurnirure() {
    location.pathname = "furniture";
  }

  function getFurnitureJavaObject() {
    $.ajax({
      url: "types",
      dataType: "json",
      success: function (data) {
        types = data;
        fillInTypes();
      },
      error: function (data) {
        alert("!ERROR: типы фурнитуры получить не удалось:");
      },
    });

    $.ajax({
      url: "item/" + getIdFromUrl(),
      dataType: "json",
      success: function (data) {
        furnitureJavaObject = data;
        fillInFurniture();
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

  function fillInFurniture() {
    if (furnitureJavaObject != null) {
      $("#idFurniture").val(furnitureJavaObject.id);
      $("#idManufacturFurniture").val(
        furnitureJavaObject.idManufacturerProgram
      );
      $("#nameFurniture").val(furnitureJavaObject.name);
      setValueInSelect("#typeOfFurniture", furnitureJavaObject.typeOfFurniture);
      setCheckBox("#itCylinderLock", furnitureJavaObject.itCylinderLock);
      $("#comment").val(furnitureJavaObject.comment);
      $("#picturePathFirst").val(furnitureJavaObject.picturePathFirst);
      $("#sketchPathFirst").val(furnitureJavaObject.sketchPathFirst);
      $("#price").val(furnitureJavaObject.price);
      $("#priceComit").val(furnitureJavaObject.priceComit);
    }
  }

  function fillInTypes() {
    if (types != null) {
      $("#typeOfFurniture").empty();

      $("#typeOfFurniture").append($("<option></option>"));

      for (var i = 0; i < types.length; ++i) {
        $("#typeOfFurniture").append(
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

  function setField(fieldName, value) {
    furnitureJavaObject[fieldName] = value;
  }

  function setCheckBox(name, value) {
    if (value == 1) {
      $(name).prop("checked", true);
    }
  }
});
