jQuery("document").ready(function () {
  var curreiId = 0;
  var types;

  getFilterList();

  $("tr").on("dblclick", function () {
    getFurniture($(this).children(".id").text());
  });

  $("tr").on("click", function () {
    curreiId = $(this).children(".id").text();
    oneEnableAllDisable(this);
  });

  $("#typeOfFurniture").change(function () {
    filterOut();
  });

  $("#buttonDeleteOrder").on("click", function () {
    if (curreiId != 0) {
      deletOrder();
    } else {
      alert("!выбери заказ");
    }
  });

  $("#addfurniture").on("click", function () {
    location.pathname = "/furniture/0";
  });

  $("#deletLine").on("click", function () {
    if (curreiId !== 0) {
      $.ajax({
        url: "furniture/" + curreiId,
        method: "DELETE",
        dataType: "json",
        success: function (data) {
          if (data.status == null){
            alert("эта фурнитура используеться");
          }else {
            location.pathname = "/furniture";
          }
        },
        error: function (data) {
          alert("!ERROR: елемнет удалить не удалось:");
        },
      });
    } else {
      alert("select lines");
    }
  });

  function getFilterByTypeFurnituresfromUrl() {
    let searchParams = new URLSearchParams(location.search);
    if(searchParams.has('typeOfFurniture')){
      let filtrValue = searchParams.get('typeOfFurniture');
      PageSelector.setValueInSelect("#typeOfFurniture", filtrValue);

      filterOut();
    }
  }

  function getFilterByTypeFurniture() {
    return "?typeOfFurniture=" + $("#typeOfFurniture").val();
  }

  function getFurniture(orderId) {
    location.href = "furniture/" + orderId + getFilterByTypeFurniture();
  }

  function deletOrder() {
    $.ajax({
      type: "DELETE",
      url: "order?orderId=" + curreiId,
      dataType: "json",
      success: function (data) {
        alert("delete completed" + data);
        location.href = "orders";
      },
      error: function (data) {
        alert("delete error:" + data);
      },
    });
  }

  function oneEnableAllDisable(item) {
    var elems = $('tr[pickOut="on"]');
    var elemsTotal = elems.length;

    for (var i = 0; i < elemsTotal; ++i) {
      $(elems[i]).attr("pickOut", "off");
    }
    $(item).attr("pickOut", "on");
  }

  function getFilterList() {
    $.ajax({
      url: "furniture/types",
      dataType: "json",
      success: function (data) {
        types = data;
        fillInTypes();
        getFilterByTypeFurnituresfromUrl()
      },
      error: function (data) {
        alert("!ERROR: типы фурнитуры получить не удалось:");
      },
    });
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

  function filterOut() {
    var filtrValue = $("#typeOfFurniture").val();

    var elems = $(".type-line");
    var elemsTotal = elems.length;

    for (var i = 0; i < elemsTotal; ++i) {
      var line = $(elems[i]).parent();

      if (filtrValue === "") {
        $(line).removeClass("ghost");
      } else if ($(elems[i]).text() !== filtrValue) {
        $(line).addClass("ghost");
      } else {
        $(line).removeClass("ghost");
      }
    }
  }
});
