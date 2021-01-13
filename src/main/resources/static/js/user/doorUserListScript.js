jQuery("document").ready(function () {
  var curreiId = 0;

  $("tr").on("dblclick", function () {
    getUser($(this).children(".id").text());
  });

  $("tr").on("click", function () {
    curreiId = $(this).children(".id").text();
    oneEnableAllDisable(this);
  });

  $("#addClass").on("click", function () {
    getUser(0);
  });

  $("#buttonDelete").on("click", function () {
    if (curreiId != 0) {
      deletClass();
    } else {
      alert("!выбери класс");
    }
  });

  function getUser(userId) {
    location.href = "user?userId=" + userId;
  }

  function oneEnableAllDisable(item) {
    var elems = $('tr[pickOut="on"]');
    var elemsTotal = elems.length;

    for (var i = 0; i < elemsTotal; ++i) {
      $(elems[i]).attr("pickOut", "off");
    }
    $(item).attr("pickOut", "on");
  }

  function deletClass() {
    $.ajax({
      type: "DELETE",
      url: "doorclass?classId=" + curreiId,
      success: function (data) {
        alert("delete completed" + data);
        location.href = "doorclasslist";
      },
      error: function (data) {
        alert("delete error:" + data);
      },
    });
  }
});
