jQuery("document").ready(function () {
  if ($("#fireproof").text() == "1") {
    $("#fireproofcheckbox").prop("checked", true);
  } else {
    $("#fireproofcheckbox").prop("checked", false);
  }

  if ($("#hot").text() == "1") {
    $("#hotcheckbox").prop("checked", true);
  } else {
    $("#hotcheckbox").prop("checked", false);
  }

  $("#close").on("click", function () {
    location.href = "doorclasslist";
  });
});
