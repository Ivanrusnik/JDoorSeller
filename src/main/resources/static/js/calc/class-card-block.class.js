class ClassCardBlock {
  static display(classList) {
    ClassCardBlock.deleteAll();

    for (var i = 0; i < classList.length; ++i) {
      var divName = classList[i].name;
      var divId = classList[i].id;

      $("<div>")
        .attr("class", "card text-dark border-dark class_card")
        .attr("data", divId)
        .attr("id", "doorClass" + divId)
        .appendTo(".select_door_class");

      $("<div>")
        .attr("class", "images_door_class_div")
        .attr("id", "doorClassDiv" + divId)
        .appendTo("#doorClass" + divId);

      $("<img>")
        .attr("class", "images_door_class")
        .attr("dataName", divName)
        .attr("src", classList[i].namePicture)
        .attr("Item", "doorClass")
        .appendTo("#doorClassDiv" + divId);

      $("<div>")
        .attr("class", "images_door_class_p")
        .attr("id", "doorClassDivP" + divId)
        .appendTo("#doorClassDiv" + divId);

      $("<p>")
        .text(divName)
        .appendTo("#doorClassDivP" + divId);

      $("<div>")
        .attr("class", "card-body")
        .attr("id", "card-body" + divId)
        .appendTo("#doorClass" + divId);

      $("<p>")
        .attr("class", "card-text")
        .text(classList[i].description)
        .appendTo("#card-body" + divId);
    }
  }
  static deleteAll() {
    $(".class_card").remove();
  }
}
