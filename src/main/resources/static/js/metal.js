jQuery("document").ready(function () {
  var JavaObject;

  //new instans
  getJavaObject();

  $("#idManufacturerProgram").change(function () {
    setField("idManufacturerProgram", $("#idManufacturerProgram").val());
  });

  $("#name").change(function () {
    setField("name", $("#name").val());
  });

  $("#nameDisplayed").change(function () {
    setField("nameDisplayed", $("#nameDisplayed").val());
  });

  $("#indexHeft").change(function () {
    setField("indexHeft", $("#indexHeft").val());
  });

  $("#isUsed").change(function () {
    if ($(this).is(":checked")) {
      setField("isUsed", 1);
    } else {
      setField("isUsed", 0);
    }
  });

  $("#comment").change(function () {
    setField("comment", $("#comment").val());
  });

  $("#price").change(function () {
    setField("price", $("#price").val());
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
        alert(data.status);
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
    location.pathname = "metal";
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

  function fillByOject() {
    if (JavaObject != null) {
      $("#idMetal").val(JavaObject.id);
      $("#idManufacturerProgram").val(JavaObject.idManufacturerProgram);
      $("#name").val(JavaObject.name);
      $("#nameDisplayed").val(JavaObject.nameDisplayed);
      $("#indexHeft").val(JavaObject.indexHeft);
      setCheckBox("#isUsed", JavaObject.isUsed);
      $("#price").val(JavaObject.price);
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
});
