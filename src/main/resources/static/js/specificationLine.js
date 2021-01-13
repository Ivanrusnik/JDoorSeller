jQuery("document").ready(function () {

    let lineSpecifications;
    let specificationEtity;
    let id = $("#id").text();
    let line_id = $("#line_id").text();

    getLineSpecification();

    $("#close").on("click", function () {
        getItem();
    });

    $("#name").change(function () {
        setField("name", $("#name").val());
    });

    $("#valueSpec").change(function () {
        setField("value", $("#valueSpec").val());
    });

    $("#formula").change(function () {
        setField("formula", $("#formula").val());
    });

    $("#nameObject").change(function () {
        setField("independentName", $("#nameObject").val());
    });

    $("#releaseOperation").change(function () {
        setField("releaseOperation", $("#releaseOperation").val());
    });

    $("#writeOffOperation").change(function () {
        setField("writeOffOperation", $("#writeOffOperation").val());
    });

    function getItem() {
        location.href = location.origin + "/pages/specifications/" + id;
}

    function getFilterByIdScpecificationTypeFromUrl() {
        return id;
    }

    function getLineSpecification() {
        $.ajax({
            url: location.origin + "/specification/" + id + "/line/" + line_id,
            dataType: "json",
            success: function (data) {
                lineSpecifications = data;
                fillInLineSpecification();
            },
            error: function (data) {
                alert("!ERROR: данные о классах получить не удалось:" + data);
            },
        });
    }

    $("#save").click(function () {

        var templateJSON = JSON.stringify(lineSpecifications);

        $.ajax({
            type: "POST",
            url: location.origin + "/specification/line/" + id,
            contentType: "application/json",
            data: templateJSON,
            dataType: "json",
            success: function (data) {
                //alert("request it OK");
                getItem();
            },
            error: function (data) {
                alert("error: request");
            },
        });
    });

    function deleteLineSpecification() {
        $.ajax({
            url: location.origin + "/specification/line/" + line_id,
            method: "DELETE",
            success: function (data) {
                getItem();
            },
            error: function (data) {
                alert("!ERROR: елемнет удалить не удалось:");
            },
        });
    }

    $("#delete").click(function () {
        if (line_id != "0"){
            deleteLineSpecification();
        }
    });

    function fillInLineSpecification() {
        $("#name").val(lineSpecifications.name);
        $("#valueSpec").val(lineSpecifications.value);
        setValueInSelect("#formula", lineSpecifications.formula);
        $("#nameObject").val(lineSpecifications.independentName);
        $("#releaseOperation").val(lineSpecifications.releaseOperation);
        $("#writeOffOperation").val(lineSpecifications.writeOffOperation);
    }

    function setValueInSelect(jqSelect, value) {
        var opt = $(jqSelect + " > option");
        opt.each(function (indx, element) {
            if ($(this).val() == value) {
                $(this).attr("selected", "selected");
            }
        });
    }

    function setField(fieldName, value) {
        lineSpecifications[fieldName] = value;
    }

});

