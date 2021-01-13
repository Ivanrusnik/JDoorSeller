jQuery("document").ready(function () {
  var curreiId = 0;

  $("tr").on("dblclick", function () {
    getTemplate($(this).children(".id").text());
  });

  $("tr").on("click", function () {
    curreiId = $(this).children(".id").text();
    oneEnableAllDisable(this);
  });

  $("#addLine").on("click", function () {
    location.href = "templates/0";
  });

  function getTemplate(typeId) {
    location.href = "templates/" + typeId;
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
