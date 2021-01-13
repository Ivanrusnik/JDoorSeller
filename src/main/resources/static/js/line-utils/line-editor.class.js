class LineEditor {

    static init() {

        LineEditor.changed = false;

        $("tbody").on("dblclick", ".vary_field", LineEditor.dblclick);

        $(window).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#edit").blur();
                LineEditor.countTotal();
            }
        });
    }

    static dblclick(e) {
        var t = e.target || e.srcElement;

        var elm_name = t.tagName.toLowerCase();

        if (elm_name == "input") {
            return false;
        }

        LineEditor.addFieldInCell(this);

        LineEditor.changed = true;
    }

    static addFieldInCell(jsOb) {
        if ($(jsOb).hasClass("text_select")) {
            LineEditor.addSelect(jsOb);
            $("#edit").focus();
            $("#edit").blur(function () {
                var val = $(this).val();
                var name = LineEditor.getNameSelectedValue(val);
                $(this).parent().empty().text(name).attr("val", val);
                LineEditor.countTotal();
            });
        } else if ($(jsOb).hasClass("text_input")) {
            LineEditor.addInput(jsOb);
            $("#edit").focus();
            $("#edit").blur(function () {
                var val = $(this).val();
                $(this).parent().empty().text(val);
                LineEditor.countTotal();
            });
        }
    }

    static addSelect(jsOb) {
        var val = $(jsOb).text();

        $(jsOb).empty();
        $("<select>")
            .attr("class", "form-control")
            .attr("id", "edit")
            .appendTo(jsOb);

        $("#edit").append($("<option></option>"));

        if (availableValues != null) {
            for (var i = 0; i < availableValues.length; i++) {
                $("#edit").append(
                    $(
                        "<option value=" +
                        availableValues[i].id +
                        ">" +
                        availableValues[i].name +
                        "</option>"
                    )
                );
            }
            LineEditor.setValueInSelect("#edit", val);
        } else {
            if (val != "") {
                $("#edit").append($("<option>" + val + "</option>"));
                setValueInSelect("#edit", val);
            }
        }
    }

    static addInput(jsOb) {
        var val = $(jsOb).html();

        $(jsOb).empty();
        $("<input>")
            .attr("class", "form-control")
            .attr("type", "text")
            .attr("id", "edit")
            .appendTo(jsOb);

        $("#edit").val(val);
    }

    static setValueInSelect(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).text().toLowerCase() == value.toLowerCase()) {
                $(this).attr("selected", "selected");
            }
        });
    }

    static getNameSelectedValue(val) {
        if (availableValues == null) {
            return "";
        }

        for (var i = 0; i < availableValues.length; i++) {
            if (availableValues[i].id == val) {
                return availableValues[i].name;
            }
        }

        return "";
    }

    static countTotal() {
        let tab_total = 0;
        $('.edit_line').each(function () {
            let quantity = $(this).children('.quantity_line').text();
            let price = $(this).children('.price_line').text();

            let discount = $(this).children('.discount_line').text();
            let priceDiscount = price -  Math.floor(price*discount/100);
            let totalItem = $(this).children('.total_line');
            let door_id = $(this).children('.id').text();

            let position = $(this).children('.position').text();

            LineEditor.setOrderDiscount(door_id, discount, position);

            let total = quantity * priceDiscount;
            $(totalItem).text(total);
            tab_total = tab_total + total;
        })
        $('#total').text(tab_total);
    }

    static addOrderDiscount(door_id, discount, position) {
        let orderDiscount = {};
        orderDiscount.order_id = order.order_id;
        orderDiscount.door_id = door_id;
        orderDiscount.discount = discount;

        orderDiscountList.push(orderDiscount);
    }

    static setOrderDiscount(door_id, discount, position) {
        let isExist = false;
        for (let i = 0; i < orderDiscountList.length; i++){
            if (order.order_id == orderDiscountList[i].order_id & door_id == orderDiscountList[i].door_id){
                orderDiscountList[i].discount = discount;
                isExist = true;
            }
        }
        if (!isExist){
            if (discount != 0){
                LineEditor.addOrderDiscount(door_id, discount, position)
            }
        }
    }


}