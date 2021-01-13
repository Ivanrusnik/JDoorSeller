class TypeCardBlock {
  static display(idDoorClass, classListParam) {
    TypeCardBlock.deleteAll();

    for (var i = 0; i < classListParam.length; ++i) {
      if (classListParam[i].id == idDoorClass) {
        var doorTypes = classListParam[i].doorTypes;
        for (var j = 0; j < doorTypes.length; ++j) {
          $("<li>")
            .attr("class", "typeLine list-group-item")
            .attr("id", "doorType" + doorTypes[j].id)
            .attr("data", doorTypes[j].id)
            .text(doorTypes[j].name)
            .attr("Item", "doorType")
            .attr("data-LeafDoorLeaf", doorTypes[j].doorLeaf)
            .appendTo(".select_door_type_list");

          $("<div>")
            .attr("class", "typeLineDaughter ghost")
            .attr("id", "doorTypeDaughter" + doorTypes[j].id)
            .appendTo("#doorType" + doorTypes[j].id);

          // $("<img>")
          //     .attr("class", "images_door_class")
          //     .attr("src", doorTypes[j].namePicture)
          //     .appendTo("#doorTypeDaughter" + doorTypes[j].id);

          $("#doorType" + doorTypes[j].id).on("hover", function () {
            var id = $(this).attr("data");
            alert(id);
            $("#doorType" + doorTypes[j].id).removeClass("ghost");
          });
        }
      }
    }
  }
  static deleteAll() {
    $(".typeLine").remove();
  }
}
