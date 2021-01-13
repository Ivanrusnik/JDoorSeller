jQuery("document").ready(function () {
  getAvailableGroups();

  $(".classGroupDiv").on("click", ".classGroupButton", function () {
    var orderId = $("#orderId").text();
    var doorGroup = $(this).attr("id");
    location.href =
      "calculation?orderId=" + orderId + "&doorGroup=" + doorGroup;
  });

  function getAvailableGroups() {
    $.ajax({
      url: "availableGroups",
      dataType: "json",
      success: function (data) {
        //alert('success: ОК');
        createGroupsFromData(data);
      },
      error: function (data) {
        alert("error: доступные группы получить не удалось!");
      },
    });
  }

  function createGroupsFromData(tab) {
    for (var i = 0; i < tab.length; i++) {
      $("<div>")
        .attr("class", "col-md-4")
        .attr("id", "div" + i)
        .appendTo(".classGroupDiv");
      $("<h2>>")
        .text(tab[i].name)
        .appendTo("#div" + i);
      $("<p>")
        .text(tab[i].description)
        .appendTo("#div" + i);

      $("<p>")
        .attr("class", "btn btn-secondary classGroupButton")
        .attr("role", "button")
        .attr("id", tab[i].id)
        .text("выбрать>>")
        .appendTo("#div" + i);
    }
  }
});
