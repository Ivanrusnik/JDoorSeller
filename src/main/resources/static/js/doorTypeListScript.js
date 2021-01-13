jQuery("document").ready(function () {
  var typeId = 0;
  var classId = $("#currentClassId").attr("value");

    changeListGroupItem(".list-group-item");

  function changeListGroupItem(selector){
    classId = $(selector).attr("data");
    if (location.href == (location.origin + "/doortypelist") && classId != ""){
      location.href = "doortypelist?classId=" + classId;
    }
  }

  $(".list-group").on("click", ".list-group-item", function () {
    classId = $(this).attr("data");
    location.href = "doortypelist?classId=" + classId;
  });

  $("tr").on("dblclick", function () {
    getType($(this).children(".id").text());
  });

  $("tr").on("click", function () {
    typeId = $(this).children(".id").text();
    oneEnableAllDisable(this);
  });

  $("#buttonDelete").on("click", function () {
    if (curreiId != 0) {
      deletType();
    } else {
      alert("!выбери тип");
    }
  });

  $("#addClass").on("click", function () {
    getType(0);
  });

  function getType(typeId) {
    location.href = "doortype?typeId=" + typeId + "&classId=" + classId;
  }

  function oneEnableAllDisable(item) {
    var elems = $('tr[pickOut="on"]');
    var elemsTotal = elems.length;

    for (var i = 0; i < elemsTotal; ++i) {
      $(elems[i]).attr("pickOut", "off");
    }
    $(item).attr("pickOut", "on");
  }

  function deletType() {
    $.ajax({
      type: "DELETE",
      url: "doortype?typeId=" + typeId,
      success: function (data) {
        alert("delete completed" + data);
        location.href = "doortypeList";
      },
      error: function (data) {
        alert("delete error:" + data);
      },
    });
  }
});
