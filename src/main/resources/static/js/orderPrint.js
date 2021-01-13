jQuery("document").ready(function () {
    if ($("#taxText").text() == "0") {
        $(".totalTax").attr("hidden", "");
    }
});