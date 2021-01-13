jQuery("document").ready(function () {
  var curreiId = 0;

  displayAdminaAndUserField();

  colorizeTheLines();

  $("tr").on("dblclick", function () {
    getOrder($(this).children(".id").text());
  });

  $("tr").on("click", function () {
    curreiId = $(this).children(".id").text();
    oneEnableAllDisable(this);
  });

  $("#buttonDeleteOrder").on("click", function () {
    if (curreiId != 0) {
      deletOrder();
    } else {
      alert("!выбери заказ");
    }
  });

  $("#addOrder").on("click", function () {
    location.href = "order";
  });

  function getOrder(orderId) {
    location.href = "order?orderId=" + orderId;
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

  function displayAdminaAndUserField() {
    var isAdmin = $("#isAdmin").text();
    if (isAdmin == "true") {
      var accessElem = $(".accessAdmin");
      for (var i = 0; i < accessElem.length; i++) {
        $(accessElem[i]).removeClass("ghost");
      }
    }

    var report = $("#report").text();
    if (report == "true") {
      $(buttonbar).addClass("ghost");
      $(buttonbar).removeClass("row");
    }
  }

  function colorizeTheLines() {
    var colorFlag = $(".colorFlag");
    for (var i = 0; i < colorFlag.length; i++) {
      if (
        "READY" == $(colorFlag[i]).text() ||
        "IN_WORK" == $(colorFlag[i]).text()
      ) {
        $(colorFlag[i]).addClass("greenFlag");
      }
    }
  }
});
