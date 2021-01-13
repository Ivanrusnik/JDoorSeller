class PageSelector {

    static fillInByStringList(selectName, list, emptyLine = false) {

        if (list != null) {
            $(selectName).empty();

            if (emptyLine) {
                $(selectName).append($("<option></option>"));
            }

            for (var i = 0; i < list.length; ++i) {
                $(selectName).append(
                    $("<option value=" + list[i] + ">" + list[i] + "</option>")
                );
            }
        }
    }

    static setValueInSelect(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }

}
