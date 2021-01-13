class PriceComponent {

    static addLineRow(container, row, idname){
        $("<div>")
            .attr("class", row)
            .attr("id", idname)
            .appendTo("#"+container);

        return idname;
    }

    static addLineColumn(container, col, idname){
        $("<div>")
            .attr("class", col)
            .attr("id", idname)
            .appendTo("#"+container);

        return idname;
    }

    static deleteRow() {
        $(".lineForDelete").remove();
    }
}