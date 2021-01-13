class AvailableManager {
    constructor(doorTemplate, availableFurnitureList) {
        this.doorTemplate = doorTemplate;
        this.availableFurnitureList = availableFurnitureList;
    }

    makeFieldsAvailable() {
        if (this.doorTemplate != null) {
            AvailableManager.makeAvailable("comment");
            AvailableManager.makeAvailable("handle");
            AvailableManager.makeAvailable("shieldKit");
            AvailableManager.makeAvailable("doorColor");
            AvailableManager.makeAvailable("additionalDoorSettings");
            AvailableManager.makeAvailable("sideDoorOpen");
        }

        this.makeAvailableIfExists("topLock","kit");
        this.makeAvailableIfExists("lowerLock","kit");

        this.makeAvailableIfExists("handle","");

        this.makeAvailableIfExists("lowerLockCylinder","");
        this.makeAvailableIfExists("topLockCylinder","");

        this.makeAvailableIfExists("topInLockDecor","");
        this.makeAvailableIfExists("topOutLockDecor","");

        this.makeAvailableIfExists("lowerInLockDecor","");
        this.makeAvailableIfExists("lowerOutLockDecor","");

        this.makeAvailableIfExists("closer","");

        this.makeAvailableIfExists("typeDoorGlass","");
        this.makeAvailableIfExists("toning","");
        this.makeAvailableIfExists("armor","");
    }

    makeAvailableIfExists(name,postfix) {
        if (availableFurnitureList[name] && availableFurnitureList[name].length > 0) {
            AvailableManager.makeAvailable(name + postfix);
        }
    }

    static makeAvailable(name) {
        $("#name" + name).attr("available", "yes");
    }

    static makeUnavailable(name) {
        $("#name" + name).attr("available", "no");
    }
}
