jQuery("document").ready(function () {
  displayAdminaAndUserField();

  if ($("#includesTaxInt").val() == 1) {
    $("#includesTax").prop("checked", true);
  } else {
    $("#includesTax").prop("checked", false);
  }

  $("#close").on("click", function () {
    location.href = "/";
  });

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
});
