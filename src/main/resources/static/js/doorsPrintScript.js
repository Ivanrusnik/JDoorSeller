jQuery("document").ready(function () {
  var orderId = $("#shadowId").text();
  Door.init();

  $.ajax({
    url: "getPrintApp",
    data: { orderId: orderId },
    dataType: "json",
    success: function (data) {
      //alert('it\'s ok');
      drawADoor(data);
    },
    error: function (data) {
      alert("error:" + data);
    },
  });

  function drawADoor(data) {
    for (var i = 0; i < data.length; ++i) {
      //div
      $("<div>").attr("class", "mainDiv").attr("id", i).appendTo("body");
      $("<div>")
        .attr("class", "doorDiv")
        .attr("id", "doorDiv" + i)
        .appendTo("#" + i);
      $("<div>")
        .attr("class", "optionsDiv")
        .attr("id", "optionsDiv" + i)
        .appendTo("#" + i);

      Door.draw(data[i].door, i);

      //options cap
      $("<span>")
        .text(data[i].orderNumber)
        .appendTo("#optionsDiv" + i);
      $("<br>").appendTo("#optionsDiv" + i);
      $("<span>")
        .text(data[i].customer)
        .appendTo("#optionsDiv" + i);
      $("<br>").appendTo("#optionsDiv" + i);
      $("<span>")
        .text(data[i].orderDate)
        .appendTo("#optionsDiv" + i);

      //tabl door
      $("<Table>")
        .attr("id", "tabldoor" + i)
        .attr("class", "lux")
        .appendTo("#optionsDiv" + i);

      $("#tabldoor" + i).append(
        "<tr><th>" +
          "Наименование</th><th>" +
          "Кол-во,шт</th><th>" +
          "Цена,руб</th></tr>"
      );

      $("#tabldoor" + i).append(
        '<tr><td class="id">' +
          data[i].door.name +
          "</td><td>" +
          1 +
          "</td><td>" +
          data[i].door.priceWithMarkup +
          "</td></tr>"
      );

      //tabl sizes
      $("<span>")
        .text("Характеристики двери:")
        .appendTo("#optionsDiv" + i);
      $("<br>").appendTo("#optionsDiv" + i);
      $("<span>")
        .text("РАЗМЕРЫ")
        .appendTo("#optionsDiv" + i);
      drawTabl("tablsizes" + i, data[i].sizes, "#optionsDiv" + i);

      //tabl appearance
      $("<span>")
        .text("ВНЕШНИЙ ВИД:")
        .appendTo("#optionsDiv" + i);
      drawTabl("tablappearance" + i, data[i].appearance, "#optionsDiv" + i);

      //tabl internalView
      $("<span>")
        .text("ВНУТРЕННИЙ ВИД:")
        .appendTo("#optionsDiv" + i);
      drawTabl("tablinternalView" + i, data[i].internalView, "#optionsDiv" + i);

      //tabl furniture
      $("<span>")
        .text("ФУРНИТУРА:")
        .appendTo("#optionsDiv" + i);
      drawTabl("tablfurniture" + i, data[i].furniture, "#optionsDiv" + i);

      //commit
      $("<span>")
        .text("ПРИМЕЧАНИЕ:")
        .appendTo("#optionsDiv" + i);
      $("<div>")
        .attr("class", "comment")
        .appendTo("#optionsDiv" + i);

      $("<span>")
        .text(
          "С размерами, комплектацией и сроками производства " +
            "двери ознакомлен, согласен претензий не имею."
        )
        .appendTo("#optionsDiv" + i);
      $("<br>").appendTo("#optionsDiv" + i);
      $("<br>").appendTo("#optionsDiv" + i);

      $("<div>")
        .attr("id", "signature" + i)
        .appendTo("#optionsDiv" + i);
      $("<span>")
        .text("Исполнитель___________")
        .appendTo("#signature" + i);
      $("<span>")
        .attr("class", "customer_signature")
        .text("Заказчик___________")
        .appendTo("#signature" + i);
    }
  }

  function drawTabl(nameTabl, tabl, appendTo) {
    $("<Table>").attr("id", nameTabl).attr("class", "lux").appendTo(appendTo);

    var sizeslength = tabl.length;
    for (var j = 0; j < sizeslength; ++j) {
      $("#" + nameTabl).append(
        '<tr><td class="id">' +
          tabl[j].name +
          "</td><td>" +
          tabl[j].value +
          "</td></tr>"
      );
    }
  }
});
