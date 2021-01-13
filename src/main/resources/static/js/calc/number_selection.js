function average_Size_widthDoor(aver) {

  return Math.round(aver/2);
}

function select_introduction(startNumber, headName, idElement, minVal, maxVal) {
  $("#nameSelectForm").text(headName);
  $("#nameSelectForm").attr("data", idElement);

  let nameField = SizingDrum.getFieldNameFromInputId("input_widthDoor");
  let tab = RestrictionOfSelectionFields[nameField];
  for (var i=0; i<tab.length; i++) {

    if (tab[i].pairOfValues == 1) {
      if (startNumber < minVal || startNumber > maxVal) {
        startNumber = minVal + (maxVal - minVal) / 2;
      }
    } else {

      if (startNumber == tab[i].startRestriction) {
        average_widthDoorVal = i;
      }
    }
  }



  var numberL = parceLongValue(startNumber, "L");
  //var numberR = parceLongValue(startNumber,'R');

  add(
    "L",
    1,
    getPreviousValue(
      numberL,
      5,
      parceLongValue(minVal, "L"),
      parceLongValue(maxVal, "L")
    )
  );
  add("L", 1, numberL);
  add(
    "L",
    1,
    getNextValue(
      numberL,
      5,
      parceLongValue(minVal, "L"),
      parceLongValue(maxVal, "L")
    )
  );

  /*
    add ('R',5,getPreviousValue(numberR,5,parceLongValue(minVal,'R'),parceLongValue(maxVal,'R')));
    add ('R',5,numberR);
    add ('R',5,getNextValue(numberR,5,parceLongValue(minVal,'R'),parceLongValue(maxVal,'R')));
    */
  //alert(''+startNamber+', int:'+aInt+', nat:'+aNat);

  colorize(".numberR");
  //colorize('.numberL');

  $(".android_main_contener").attr("show", "no");
  $(".wall_of_darkness").attr("show", "no");
}

function add(side, n, firstValue, minVal, maxVal) {
  var elems = $(".number" + side);
  var sizeЕlems = elems.length;

  if (firstValue == null) {
    firstValue = getNextValue(
      $(elems[0]).text(),
      n,
      parceLongValue(minVal, side),
      parceLongValue(maxVal, side)
    );
  }

  $(elems[sizeЕlems - 1]).remove();

  $("<span>")
    .attr("class", "counter_line number" + side)
    .text(firstValue)
    .prependTo($(".counter_line_int" + side));

  colorize(".number" + side);
}

function reduce(side, n, minVal, maxVal) {
  var elems = $(".number" + side);
  var sizeЕlems = elems.length;
  var firstValue = $(elems[sizeЕlems - 1]).text();
  $(elems[0]).remove();
  $("<span>")
    .attr("class", "counter_line number" + side)
    .text(
      getPreviousValue(
        firstValue,
        n,
        parceLongValue(minVal, side),
        parceLongValue(maxVal, side)
      )
    )
    .appendTo($(".counter_line_int" + side));

  colorize(".number" + side);
}

function colorize(className) {
  var elems = $(className);
  var sizeЕlems = elems.length;
  for (var i = 0; i < sizeЕlems; ++i) {
    deleteDown($(elems[i]));

    if (i == 0 || i == sizeЕlems - 1) {
      $(elems[i]).addClass("line1");
    } else if (i == 1) {
      $(elems[i]).addClass("line");
    }
  }
}

function deleteDown(elem) {
  if (elem.hasClass("line1")) {
    elem.removeClass("line1");
  }
  if (elem.hasClass("line")) {
    elem.removeClass("line");
  }
}

function getNextValue(firstValue, n, minVal, maxVal) {
  let nameField = SizingDrum.getFieldNameFromInputId("input_widthDoor");
  var tab = RestrictionOfSelectionFields[nameField];

  for (var i=0; i<tab.length; i++) {
    if (tab[i].pairOfValues == 0) {
      if (average_widthDoorVal > maxSize_widthDoorVal - 1) {
        average_widthDoorVal = -1;
      }
      return tab[average_widthDoorVal + 1].startRestriction;

    }else {
      if (1 * n + parseInt(firstValue) <= maxVal) {
        return 1 * n + parseInt(firstValue);
      }
      return minVal;
    }
  }

}

function getPreviousValue(firstValue, n, minVal, maxVal) {
  let nameField = SizingDrum.getFieldNameFromInputId("input_widthDoor");
  var tab = RestrictionOfSelectionFields[nameField];

  for (var i=0; i<tab.length; i++){
    if(tab[i].pairOfValues == 0) {
      if (average_widthDoorVal < 1) {
        average_widthDoorVal = maxSize_widthDoorVal + 1;
      }
      return tab[average_widthDoorVal - 1].startRestriction;
    } else {
      if (parseInt(firstValue) - 1 * n > minVal) {
        return parseInt(firstValue) - 1 * n;
        }
        return maxVal;
    }
  }

}

function closeSelect() {
  $(".android_main_contener").attr("show", "ghost_lement");
  $(".wall_of_darkness").attr("show", "ghost_lement");
}

function parceLongValue(startNumber, side) {
  oneDiv = true;
  if (oneDiv) {
    return startNumber;
  } else {
    if (side == "L") {
      return (startNumber - (startNumber % 100)) / 100;
    } else {
      return startNumber % 100;
    }
  }
}
