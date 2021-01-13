class SizeCostBlock {

    static WIDTH_JAVA_OBJECT= 'sizeCostWidth';
    static HEIGHT_JAVA_OBJECT = 'sizeCostHeight';

    static domSelect = {
        sizeOfTorification:'sizeOfTorification',
        costSizeOfTorification:'costSizeOfTorification',
        cost:'costOfChange',
        typeSize:{
            width:'Width',
            height:'Height'
        }
    }

    static init(){
        $(".size-cost-block").change(SizeCostBlock.change);
    }

    static change(){
        SizeCostBlock.saveField();
    }

    static saveField(nameFieldJavaObject) {

        SizeCostBlock.deleteLineIntoTemplate(SizeCostBlock.HEIGHT_JAVA_OBJECT);
        SizeCostBlock.deleteLineIntoTemplate(SizeCostBlock.WIDTH_JAVA_OBJECT);

        SizeCostBlock.saveLineToJavaObject(SizeCostBlock.HEIGHT_JAVA_OBJECT,SizeCostBlock.domSelect.typeSize.width);
        SizeCostBlock.saveLineToJavaObject(SizeCostBlock.WIDTH_JAVA_OBJECT,SizeCostBlock.domSelect.typeSize.height);

    }

    static saveLineToJavaObject(javaFieldName,typeSize){
        template[javaFieldName].push({
            startRestriction: SizeCostBlock.getVal(typeSize,SizeCostBlock.domSelect.costSizeOfTorification),
            stopRestriction: 0,
            step: SizeCostBlock.getVal(typeSize,SizeCostBlock.domSelect.sizeOfTorification),
            pairOfValues:0,
            cost: SizeCostBlock.getVal(typeSize,SizeCostBlock.domSelect.cost)
        });
    }

    static deleteLineIntoTemplate(nameFieldJavaObject){
        let size = template[nameFieldJavaObject].length;
        template[nameFieldJavaObject].splice(0, size);
    }

    static getVal(index, idNmae) {
        return $("#"+idNmae + index).val();
    }

    static setVal(index, idNmae, val) {
        $("#"+idNmae + index).val(val);
    }

    static setValueToDom(){

        const lineTemplateWidth = template[SizeCostBlock.WIDTH_JAVA_OBJECT];
        if(lineTemplateWidth.length>0){
            SizeCostBlock.setVal(SizeCostBlock.domSelect.typeSize.width,
                SizeCostBlock.domSelect.cost,
                lineTemplateWidth[0].cost);
            SizeCostBlock.setVal(SizeCostBlock.domSelect.typeSize.width,
                SizeCostBlock.domSelect.costSizeOfTorification,
                lineTemplateWidth[0].startRestriction);
            SizeCostBlock.setVal(SizeCostBlock.domSelect.typeSize.width,
                SizeCostBlock.domSelect.sizeOfTorification,
                lineTemplateWidth[0].step);
        }



        const lineTemplateHeight = template[SizeCostBlock.HEIGHT_JAVA_OBJECT];
        if(lineTemplateHeight.length>0) {
            SizeCostBlock.setVal(SizeCostBlock.domSelect.typeSize.height,
                SizeCostBlock.domSelect.cost,
                lineTemplateHeight[0].cost);
            SizeCostBlock.setVal(SizeCostBlock.domSelect.typeSize.height,
                SizeCostBlock.domSelect.costSizeOfTorification,
                lineTemplateHeight[0].startRestriction);
            SizeCostBlock.setVal(SizeCostBlock.domSelect.typeSize.height,
                SizeCostBlock.domSelect.sizeOfTorification,
                lineTemplateHeight[0].step);
        }
    }
}
