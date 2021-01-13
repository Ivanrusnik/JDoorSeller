class Door {
    static listColorsEntity;



    static init() {
        Door.getColorInstans();
    };

    static set(fieldName, value) {
        door[fieldName] = value;
        failureToSetValue = false;

    }

    static setGlass(fieldName, value) {
        door.doorGlass[fieldName] = value;
        failureToSetValue = false;
    }


    static draw(door, i) {
        let sideDoorOpen = door.sideDoorOpen;
        let config = new Object();
        config.color = door.doorColor;
        config.width = (door.widthDoor * 2) / 10;
        config.height = (door.heightDoor * 2) / 10;
        config.doorFanlightHeight = (door.doorFanlightHeight * 2) / 10;
        config.i = i;

        let sidesOpening = Door.getSidesOpening(door);

        config.sideOpeningL = sidesOpening.left;
        config.sideOpeningR = sidesOpening.right;
        config.leafCount = sidesOpening.leaf;

        if (door.isDoorGlass == 1 && door.doorGlass != null) {
            config.glassHeight = (door.doorGlass.glassHeight * 2) / 10;
            config.glassWidth = (door.doorGlass.glassWidth * 2) / 10;

            config.topGlassPosition = 0;
            if (door.doorGlass.bottomGlassPosition == 0) {
                //glassHeight
                config.topGlassPosition = (door.heightDoor - door.doorGlass.glassHeight) / 10;
            } else {
                config.topGlassPosition = ((door.heightDoor - Number(door.doorGlass.glassHeight) - Number(door.doorGlass.bottomGlassPosition))*2) / 10;
            }

            config.leftGlassPosition = 0;
            if (door.doorGlass.leftGlassPosition == 0) {
                //glassWidth
                config.leftGlassPosition = (door.widthDoor - door.doorGlass.glassWidth) / 10;
                config.leftGlassPositionInner = config.leftGlassPosition;
            } else {
                config.leftGlassPosition = (Number(door.doorGlass.leftGlassPosition) * 2) / 10;
                config.leftGlassPositionInner = ((door.widthDoor - door.doorGlass.glassWidth) * 2) / 10 - config.leftGlassPosition;
            }

        }

        this.delete(i);

        if (!!config.color && !!config.width && !!config.height) {
            //draw door L
            let container = Door.createContainer(i, "L");

            Door.createСolor(container, config);
            Door.createTrims(container, config, door);
            Door.createFanlight(container, config, "L");

            let containerLeaf = Door.createLeafContainer(container, config, "L");

            Door.dinamicRelief(containerLeaf, config, door, "L");
            Door.createCloser(containerLeaf, config, door);
            Door.createHandle(containerLeaf, config, door, "L");
            Door.createStep(containerLeaf, config, door);
            Door.createGlass(containerLeaf, config, door, "L");
            Door.createLogo(containerLeaf, door);
            Door.createHinges(containerLeaf, door);
            Door.createTopOutLockDecor(containerLeaf, door);
            Door.createLowerOutLockDecor(containerLeaf, door);

            //draw door R
            let containerR = Door.createContainer(i, "R");
            Door.createСolor(containerR, config);
            Door.createFanlight(containerR, config, "R");

            let containerLeafR = Door.createLeafContainer(containerR, config, "R");

            Door.createRelief(containerLeafR, config, "R");
            Door.createHandle(containerLeafR, config, door, "R");
            Door.createStep(containerLeafR, config, door);
            Door.createGlass(containerLeafR, config, door, "R");
            Door.createNightLock(containerLeafR, door);
            Door.createTopInLockDecor(containerLeafR, door);
            Door.createLowerInLockDecor(containerLeafR, door);
            Door.createShieldColor(containerLeafR, door);
            Door.createShieldDesign(containerLeafR, door);
            Door.createShieldGlass(containerLeafR, door);
        }
    }

    static delete(i) {
        $("#picture_doorL" + i).remove();
        $("#picture_doorR" + i).remove();
    }

    static getSidesOpening(door) {
        let result = {left: "", right: "", leaf: ""};
        if (door.doorLeaf == 1) {
            result.left = "InSlit";
            result.right = "OutSlit";
        } else if (door.doorLeaf == 2) {
            result.leaf = "2";

            if (!!door.sideDoorOpen) {
                if (door.sideDoorOpen == "LEFT") {
                    result.left = "InSlit_D_L";
                    result.right = "OutSlit_D_L";
                } else {
                    result.left = "InSlit_D_R";
                    result.right = "OutSlit_D_R";
                }
            } else {
                result.left = "InSlit_D_L";
                result.right = "OutSlit_D_L";
            }
        }
        return result;
    }

    static createContainer(i, side) {
        $("<div>")
            .attr("class", "picture_door" + side)
            .attr("id", "picture_door" + side + i)
            .appendTo("#doorDiv" + i);

        return "#picture_door" + side + i;
    }

    static dinamicRelief(containerLeaf, config, door, side) {
        if (door.doorDesign != null && door.doorDesign.doorDesign != null) {
            Door.createReliefDesign(containerLeaf, config, door.doorDesign.doorDesign.picturePath, door, side);
        } else {
            Door.createRelief(containerLeaf, config, side);
        }
    }

    static getPicturePath(nameColor) {
        let listColors = Door.listColorsEntity;
        for (let i = 0; i < listColors.length; i++) {
            if (listColors[i].name == nameColor) {
                return listColors[i].picturePath;
            }
        }
    }

    static createСolor(container, config) {
        let pathPicture = Door.getPicturePath(config.color);
        //let pathPicture = door.doorDesign.doorColor.picturePath;
        $("<img>")
            .attr("class", "color_door")
            .attr("src", pathPicture)
            .attr(
                "style",
                "width:" +
                config.width +
                "px; height:" +
                (config.doorFanlightHeight + config.height) +
                "px;"
            )
            .appendTo(container);
    }

    static createTrims(container, config, door) {
        //Trim
        if (door.leftDoorTrim == 0) {
            $("<div>")
                .attr("class", "puffinLeft")
                .attr(
                    "style",
                    "width:3.5%; height:" +
                    (config.doorFanlightHeight + config.height) +
                    "px;"
                )
                .appendTo(container);
        }
        if (door.rightDoorTrim == 0) {
            $("<div>")
                .attr("class", "puffinRight")
                .attr(
                    "style",
                    "width:3.5%; height:" +
                    (config.doorFanlightHeight + config.height) +
                    "px;"
                )
                .appendTo(container);
        }
        if (door.topDoorTrim == 0) {
            $("<div>")
                .attr("class", "puffinUp")
                .attr("style", "width:" + config.width + "px; height:2%;")
                .appendTo(container);
        }
    }

    static createFanlight(container, config, side) {
        if (config.doorFanlightHeight > 0) {
            //draw fanlight
            $("<div>")
                .attr("class", "fanlight")
                .attr("id", "fanlight" + side + config.i)
                .attr(
                    "style",
                    "width:" +
                    config.width +
                    "px; height:" +
                    config.doorFanlightHeight +
                    "px;"
                )
                .appendTo(container);

            $("<img>")
                .attr("class", "opening_side_images")
                .attr("src", "images/Door/fanlight_in.png")
                .attr(
                    "style",
                    "width:" +
                    config.width +
                    "px; height:" +
                    config.doorFanlightHeight +
                    "px;"
                )
                .appendTo(container);
        }
    }

    static createLeafContainer(container, config, side) {
        $("<div>")
            .attr("class", "Leaf")
            .attr("id", "Leaf" + side + config.i)
            .attr(
                "style",
                "width:" + config.width + "px; height:" + config.height + "px;"
            )
            .appendTo(container);

        return "#Leaf" + side + config.i;
    }

    static reflectionPicture(door){
        if (door.sideDoorOpen == "RIGHT"){
            return 1;
        } else return -1;
    }

    static createReliefDesign(containerLeaf, config, path, door, side) {
        let scaleX = Door.reflectionPicture(door);
        $("<img>")
            .attr("class", "opening_side_images")
            .attr("src", path)
            .attr(
                "style",
                "width:" + config.width + "px; height:" + config.height + "px; transform: scale("+ scaleX +", 1)"
            )
            .appendTo(containerLeaf);
    }

    static createRelief(containerLeaf, config, side) {
        $("<img>")
            .attr("class", "opening_side_images")
            .attr("src", "images/Door/" + config["sideOpening" + side] + ".png")
            .attr(
                "style",
                "width:" + config.width + "px; height:" + config.height + "px;"
            )
            .appendTo(containerLeaf);

    }

    static createCloser(containerLeaf, config, door) {
        if (door.furnitureKit != null && door.furnitureKit.closer != null) {
            $("<img>")
                .attr("class", "closer_images")
                .attr("src", "" + door.furnitureKit.closer.sketchPathFirst)
                .appendTo(containerLeaf);
        }
    }

    static createHandle(containerLeaf, config, door, side) {
        if (side === "L") {
            if (door.furnitureKit != null && door.furnitureKit.handle != null) {
                if (door.sideDoorOpen == "RIGHT") {
                    $("<img>")
                        .attr(
                            "class",
                            "handle_images left_side" + config.leafCount + "_handle"
                        )
                        .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo(containerLeaf);
                } else {
                    $("<img>")
                        .attr(
                            "class",
                            "handle_images rigth_side" + config.leafCount + "_handle mirrorX"
                        )
                        .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo(containerLeaf);
                }
            }
        } else {
            if (door.furnitureKit != null && door.furnitureKit.handle != null) {
                if (door.sideDoorOpen == "RIGHT") {
                    $("<img>")
                        .attr(
                            "class",
                            "handle_images rigth_side" + config.leafCount + "_handle mirrorX"
                        )
                        .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo(containerLeaf);
                } else {
                    $("<img>")
                        .attr(
                            "class",
                            "handle_images left_side" + config.leafCount + "_handle"
                        )
                        .attr("src", "" + door.furnitureKit.handle.sketchPathFirst)
                        .appendTo(containerLeaf);
                }
            }
        }
    }

    static createStep(containerLeaf, config, door) {
        if (
            door.furnitureKit != null &&
            door.stainlessSteelDoorstep != null &&
            door.stainlessSteelDoorstep != 0
        ) {
            $("<img>")
                .attr("class", "stainlessSteelDoorstepL_images")
                .attr("src", "images/findings/door handles sketch/000000001_R.png")
                .appendTo(containerLeaf);
        }
    }

    static createGlass(containerLeaf, config, door, key) {
        if (door.isDoorGlass == 1 && door.doorGlass != null) {

            let leftPosition = key === "L" ? config.leftGlassPosition : config.leftGlassPositionInner ;

            $("<img>")
                .attr("class", "opening_side_images")
                .attr("src", "images/Door/window.png")
                .attr(
                    "style",
                    "width:" +
                    config.glassWidth +
                    "px; height:" +
                    config.glassHeight +
                    "px; top:" +
                    config.topGlassPosition +
                    "px; left:" +
                    leftPosition +
                    "px;"
                )
                .appendTo(containerLeaf);
        }
    }

    static createLogo(containerLeaf, door) {
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }

        $("<img>")
            .attr("class", "logo_brand_images_" + side)
            .attr("src", "images/findings/logo.png")
            .appendTo(containerLeaf);
    }

    static createHinges(containerLeaf, door) {
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }

        $("<img>")
            .attr("class", "upper_hinge hinge_images_" + side)
            .attr("src", "images/findings/sharnir.png")
            .appendTo(containerLeaf);

        $("<img>")
            .attr("class", "middle_hinge hinge_images_" + side)
            .attr("src", "images/findings/sharnir.png")
            .appendTo(containerLeaf);

        $("<img>")
            .attr("class", "lower_hinge hinge_images_" + side)
            .attr("src", "images/findings/sharnir.png")
            .appendTo(containerLeaf);
    }

    static createNightLock(containerLeaf, door) {
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }

        $("<img>")
            .attr("class", "night_lock_images_" + side)
            .attr("src", "images/findings/night_lock.png")
            .appendTo(containerLeaf);
    }

    static createTopInLockDecor(containerLeaf, door) {
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }
        let imgKey;
        if (door.furnitureKit.topInLockDecor == null){
            imgKey ="images/findings/zamok.png";
        } else {
            imgKey = door.furnitureKit.topInLockDecor.sketchPathFirst;
        }
        //images/findings/zamok.png
        $("<img>")
            .attr("class", "top_lock_decor lock_decor_" + side)
            .attr("src", imgKey)
            .appendTo(containerLeaf);
    }

    static createLowerInLockDecor(containerLeaf, door) {
        let side = "R";
        if (door.sideDoorOpen == "RIGHT") {
            side = "L";
        }

        let imgKey;
        if (door.furnitureKit.lowerInLockDecor == null){
            imgKey ="images/findings/zamok.png";
        } else {
            imgKey = door.furnitureKit.lowerInLockDecor.sketchPathFirst;
        }

        $("<img>")
            .attr("class", "lower_lock_decor lock_decor_" + side)
            .attr("src", imgKey)
            .appendTo(containerLeaf);
    }

    static createTopOutLockDecor(containerLeaf, door) {
        let side = "L";
        if (door.sideDoorOpen == "RIGHT") {
            side = "R";
        }

        let imgKey;
        if (door.furnitureKit.topOutLockDecor == null){
            imgKey ="images/findings/zamok.png";
        } else {
            imgKey = door.furnitureKit.topOutLockDecor.sketchPathFirst;
        }

       // "images/findings/zamok.png"
        //door.furnitureKit.topOutLockDecor.sketchPathFirst;

        $("<img>")
            .attr("class", "top_lock_decor lock_decor_" + side)
            .attr("src", imgKey)
            .appendTo(containerLeaf);

    }

    static createLowerOutLockDecor(containerLeaf, door) {
        let side = "L";
        if (door.sideDoorOpen == "RIGHT") {
            side = "R";
        }

        let imgKey;
        if (door.furnitureKit.lowerOutLockDecor == null){
            imgKey ="images/findings/zamok.png";
        } else {
            imgKey = door.furnitureKit.lowerOutLockDecor.sketchPathFirst;
        }
        //images/findings/zamokIn.png
        $("<img>")
            .attr("class", "lower_lock_decor lock_decor_" + side)
            .attr("src", imgKey)
            .appendTo(containerLeaf);
    }

    static createShieldColor(containerLeaf, door) {
        if (door.shieldKit != null && door.shieldKit.shieldColor != null) {
            let scaleX = Door.reflectionPicture(door);
            $("<img>")
                .attr("class", "shield_color")
                .attr("src", door.shieldKit.shieldColor.picturePath)
                .attr("style",
                    "px; transform: scale("+ scaleX +", 1)")
                .appendTo(containerLeaf);
        }
    }

    static createShieldDesign(containerLeaf, door) {
        if (door.shieldKit != null && door.shieldKit.shieldDesign != null) {
            if (door.shieldKit.shieldColor.containsDesign == 0) {
                let scaleX = Door.reflectionPicture(door);
                $("<img>")
                    .attr("class", "shield_design")
                    .attr("src", door.shieldKit.shieldDesign.picturePath)
                    .attr("style",
                        "px; transform: scale(" + scaleX + ", 1)")
                    .appendTo(containerLeaf);
            }
        }
    }

    static createShieldGlass(containerLeaf, door) {
        if (door.shieldKit != null && door.shieldKit.shieldGlass != null) {
            if (door.shieldKit.shieldColor.containsDesign == 0) {
                let scaleX = Door.reflectionPicture(door);
                $("<img>")
                    .attr("class", "shield_glass")
                    .attr("src", door.shieldKit.shieldGlass.picturePath)
                    .attr("style",
                        "px; transform: scale(" + scaleX + ", 1)")
                    .appendTo(containerLeaf);
            }
        }
    }

    static getColorInstans() {
        $.ajax({
            url: "color/doorColors",
            dataType: "json",
            success: function (data) {
                Door.listColorsEntity = data;
            },
            error: function (data) {
                alert("!ERROR: типы фурнитуры получить не удалось:");
            },
        });
    }
}
