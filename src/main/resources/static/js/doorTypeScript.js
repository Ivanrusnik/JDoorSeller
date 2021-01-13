jQuery("document").ready(function () {
  if ($("#priceListInt").val() == 1) {
    $("#priceList").prop("checked", true);
    drawSettingPrice("#priceList");
  }

  $("#close").on("click", function () {
    location.href = "doortypelist";
  });

  $("#priceList").on("click", function () {
    drawSettingPrice(this);
  });

  function drawSettingPrice(elem) {
    if ($(elem).is(":checked")) {
      $("#priceSettingByPrice").removeClass("ghost");
    } else {
      $("#priceSettingByPrice").addClass("ghost");
    }
  }
});
