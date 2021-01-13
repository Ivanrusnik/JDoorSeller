priceGroup = null;
usersRoles = null;

jQuery("document").ready(function () {


    getAllPriceGroup();
    getRoles();


    if ($("#enabled").text() == "1") {
        $("#enabledcheckbox").prop("checked", true);
    } else {
        $("#enabledcheckbox").prop("checked", false);
    }

    $("#close").on("click", function () {
        location.href = "users";
    });

    $("#userorder").on("click", function () {
        var userId = $("#userId").val();
        location.href = "orders?userId=" + userId;
    });


    function setPriceGroup() {
        let priceGroupFromJObject = $("#priceGroupsFromJObject").val();
        PageSelector.setValueInSelect("#PriceGroups", priceGroupFromJObject);
    }

    function getAllPriceGroup() {
        $.ajax({
            url: location.origin + "/doortype/price-group",
            dataType: "json",
            success: function (data) {
                priceGroup = data;
                PageSelector.fillInByStringList("#PriceGroups", priceGroup);
                setPriceGroup();
            },
            error: function (data) {
                alert("!ERROR: ценовые группы получить не удалось:");
            },
        });
    }

    function getRoles() {
        $.ajax({
            url: location.origin + "/users/roles",
            dataType: "json",
            success: function (data) {
                usersRoles = data;
                PageSelector.fillInByStringList("#role", usersRoles);
                setUsersRole();
            },
            error: function (data) {
                alert("!ERROR: ценовые группы получить не удалось:");
            },
        });
    }

    function setUsersRole() {
        let uisersRolesFromObject = $("#RoleUserFromObject").val();
        PageSelector.setValueInSelect("#role", uisersRolesFromObject);
    }

});
