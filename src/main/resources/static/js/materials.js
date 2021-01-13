
jQuery("document").ready(function () {
  var materialsList;

  var currentLine = 0;

  grtListDoorClassToSelect();

  $("#doorclassselect").change(function () {
    fillInDoorType(getDoorClassbyId($("#doorclassselect").val()));
  });

  $("#doortypeselect").change(function () {
    var doorTypeId = $("#doortypeselect").val();

    $.ajax({
      url: "specificationbyid",
      data: { typeId: doorTypeId },
      dataType: "json",
      success: function (data) {
        specification = data;
        availableValues = data.availableValues;
        lineSpecifications = data.lineSpecifications;

        fillTabLine();
        displayComponentVisibility();
      },
      error: function (data) {
        alert("!ERROR: данные шаблона получить не удалось:");
      },
    });
  });

  LineEditor.init();

  $("tbody").on("dblclick", function () {
    displayComponentVisibility();
  });

  $("tbody").on("click", "tr", function () {
    currentLine = $(this).children(".position").text();
    oneEnableAllDisable(this);
  });



  $("#addLine").click(function () {
    if ($(this).hasClass("disabled")) {
      return;
    }

    addLine("", "newName", 0, "");
  });

  $("#deletLine").click(function () {
    if ($(this).hasClass("disabled")) {
      return;
    }

    $("#line" + currentLine).remove();

    displayComponentVisibility();
  });

  $("#saveSpec").click(function () {
    if ($(this).hasClass("disabled")) {
      return;
    }

    var size = materialsList.length;
    materialsList.splice(0, size);

    var lineArr = $(".line");
    for (var i = 0; i < lineArr.length; i++) {
      var indexLine = $(lineArr[i]).children(".position").text();
      var nameMaterial = $("#name" + indexLine).html();

      lineSpecifications.push(
        newInstansLineSpecification(
          $("#id" + indexLine).html(),
          0,
          nameMaterial,
          $("#value" + indexLine).html(),
          $("#formula" + indexLine).html()
        )
      );
    }

    var templateJSON = JSON.stringify(materialsList);

    $.ajax({
      type: "POST",
      url: "materials",
      contentType: "application/json",
      data: templateJSON,
      dataType: "json",
      success: function (data) {
        alert("request it OK");
      },
      error: function (data) {
        alert("error: request");
      },
    });
  });



  function grtListDoorClassToSelect() {
    $.ajax({
      url: "materialsList",
      dataType: "json",
      success: function (data) {
        materialsList = data;
        fillTabLine();
      },
      error: function (data) {
        alert("!ERROR: данные о классах получить не удалось:");
      },
    });
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

  function getNextPosition() {
    var posElem = $(".position");
    if (posElem != null) {
      return posElem.length + 1;
    }
    return 0 + 1;
  }

  function newInstansLineSpecification(id, materialId, name, value, formula) {
    var lim = new (function () {
      this.id = id;
      this.doorType = specification.doorType;
      this.materialId = materialId;
      this.name = name;
      this.value = parseFloat(value);
      this.formula = formula;
    })();
    return lim;
  }

  function fillTabLine() {
    if (materialsList == null) {
      return;
    }

    var sizelineSpec = materialsList.length;
    for (var i = 0; i < sizelineSpec; i++) {
      addLine(
        materialsList[i].id,
        materialsList[i].name,
        materialsList[i].idManufacturerProgram,
        materialsList[i].price
      );
    }
  }

  function addLine(id, newName, idManufacturerProgram, price) {
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
        '<td class="vary_field text_input" id="name' +
        Position +
        '">' +
        newName +
        "</td>" +
        '<td class="vary_field text_input" id="value' +
        Position +
        '">' +
        idManufacturerProgram +
        "</td>" +
        '<td class="vary_field text_input" id="formula' +
        Position +
        '" >' +
        price +
        "</td>" +
        "</tr>"
    );

    if (id == "") {
      var elementPosition = $("#line" + Position).offset().top;
      $("body,html").animate({ scrollTop: elementPosition }, 500);
    }
  }

  function displayComponentVisibility() {
    if (materialsList != null) {
      $("#addLine").removeClass("disabled");
      $("#deletLine").removeClass("disabled");
    }
    if (LineEditor.changed) {
      $("#saveSpec").removeClass("disabled");
    }
  }

  function oneEnableAllDisable(item) {
    var elems = $('tr[pickOut="on"]');
    var elemsTotal = elems.length;

    for (var i = 0; i < elemsTotal; ++i) {
      $(elems[i]).attr("pickOut", "off");
    }
    $(item).attr("pickOut", "on");
  }
});
