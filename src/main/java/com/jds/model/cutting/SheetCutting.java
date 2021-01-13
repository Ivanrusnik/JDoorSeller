package com.jds.model.cutting;

import java.util.*;

public class SheetCutting {

    List<DoorPart> parts;
    List<Sheet> sheets;
    int numberOfSheets;


    public SheetCutting(List<DoorPart> parts, Sheet sheet) {
        this.parts = parts;
        this.sheets = new ArrayList<>();
        this.sheets.add(sheet);
        this.parts = preparedParts(parts,sheet);
        this.numberOfSheets = 0;
    }

    public void CompleteCutting(){

        int residue = getQuantityPosted();

        while (residue>0){

            boolean posted = attemptToPost();

            if (!posted){
                residue = getQuantityPosted();
                if(residue == 0){break;}
                sheets.add(new Sheet(sheets.get(numberOfSheets)));
                numberOfSheets++;
            }
        }
        for(Sheet sheet :sheets){
            sheet.printSheet();
            sheet.calculateAmountWorkSpace();
        }

    }

    public List<DoorPart> preparedParts(List<DoorPart> parts,Sheet sheet){


        parts = makeTheQuantitySingle(parts);

        List<DoorPart> newDoorParts = new LinkedList<>();
        for (int i =0; i<parts.size();  i++){

            DoorPart part = parts.get(i);
            part.setIndex(i);
            part.setSpace((double)(part.getWidth()*part.getHeight())/1000000);

            if((part.getHeight()>=sheet.getHeight())&&(part.getHeight()>=sheet.getWidth())){
                System.out.println("!!!ERROR the workpiece does not fit!");//exeption
            }

            if((part.getWidth()>=sheet.getHeight())&&(part.getWidth()>=sheet.getWidth())){
                System.out.println("!!!ERROR the workpiece does not fit!");//exeption
            }

            if((part.getHeight()<=sheet.getHeight()&&(part.getWidth()<=sheet.getWidth()))){
                newDoorParts.add(DoorPart.getInstansInverted(part));
            }

            //inverted 90
            if((part.getHeight()<=sheet.getWidth()&&(part.getWidth()<=sheet.getHeight()))){
                newDoorParts.add(part);
            }

            Collections.sort(newDoorParts, new Comparator<DoorPart>() {
                @Override
                public int compare(DoorPart o1, DoorPart o2) {
                    return o1.compareTo(o2);
                }
            });

        }
        return newDoorParts;
    }

    public int getQuantityPosted(){
        int count = 0;
        for(DoorPart doorPart: parts){
            if (!doorPart.isPosted()){
                count = count + doorPart.getQuantity();
            }
        }
        return count;
    }

    public boolean attemptToPost(){

        Boolean Post=false;

        List<HorizontalCut> horCutList = sheets.get(numberOfSheets).getHorCutList();
        for (int nH=0; nH < horCutList.size(); nH++){

            List<VerticalСut> verСutList = horCutList.get(nH).getVerticalСutList();
            for (int iH=0; iH < verСutList.size(); iH++){
                    VerticalСut verСut = verСutList.get(iH);
                    if (verСut.isFull()){continue;}

                    for(DoorPart doorPart:parts){
                        if (doorPart.isPosted()){continue;}
                        if ((verСut.getHg()>=doorPart.getWidth())&&(verСut.getHv()>=doorPart.getHeight())){

                            doorPart.setPositioningTop(sheets.get(numberOfSheets).getHeight()-verСut.getHg());
                            doorPart.setPositioningLeft(verСut.getK1());

                            placeAPartOnASheet(doorPart,verСut,nH);

                            sheets.get(numberOfSheets).getContainsParts().add(doorPart);
                            markPartAsPosted(doorPart,parts);

                            Post = true;
                            break;
                        }
                    }
                    if (Post)
                        break;
            }
            if (Post)
                break;
        }
        return Post;
    }

    public List<DoorPart> makeTheQuantitySingle(List<DoorPart> parts){

        List<DoorPart> newList = new ArrayList<>();
        for(DoorPart doorPart:parts){
            if(doorPart.getQuantity()>1){
                int quantity = doorPart.getQuantity();
                while (quantity>0){
                    newList.add(doorPart);
                    quantity--;
                }
            }
            else {
                newList.add(doorPart);
            }
        }

        return newList;
    }

    public void placeAPartOnASheet(DoorPart doorPart,VerticalСut verСut,int nH){

        VerticalСut newVerСut = new VerticalСut(true,
                doorPart.getIndex(),
                doorPart.getWidth(),
                doorPart.getHeight(),
                0,0,false);

        int h = sheets.get(numberOfSheets).getHeight();

        int start = h-(h-nH);
        int end = h-(h-nH)+doorPart.getWidth();
        int lineHeight = verСut.getK1();

        int residueL = 0;

        List<HorizontalCut> horCutList = sheets.get(numberOfSheets).getHorCutList();
        int l = 0;
        int n = start;
        while (n<end){
            List<VerticalСut> verCutList = horCutList.get(n).getVerticalСutList();

            int indexEmptyBorder = 0;
            VerticalСut emptyVerCut = getEmptyBorder(verCutList,verСut.getK1(),indexEmptyBorder);


            int heightEmptyBorder =0;
            try{
                if (emptyVerCut.getK1()<lineHeight) {
                    heightEmptyBorder = lineHeight - emptyVerCut.getK1();
                    int k1 = emptyVerCut.getK1();
                    int k2 = emptyVerCut.getK1() + heightEmptyBorder;
                    VerticalСut newEmptyVerCut = new VerticalСut(false, 0,
                            emptyVerCut.getHg(),
                            heightEmptyBorder,
                            k1, k2, false);
                    verCutList.add(newEmptyVerCut);
                }
            }
            catch (Exception e){
                //вывод сообщений
            }

            emptyVerCut.setHv( emptyVerCut.getHv()-(newVerСut.getHv()+heightEmptyBorder));

            newVerСut.setK1(emptyVerCut.getK1());
            newVerСut.setK2(emptyVerCut.getK2()-emptyVerCut.getHv());

            emptyVerCut.setK1(emptyVerCut.getK1()+doorPart.getHeight()+heightEmptyBorder);

            l = verCutList.get(indexEmptyBorder).Hv;

            if (indexEmptyBorder==0){
                verCutList.add(newVerСut);
            }
            else {
                verCutList.add(indexEmptyBorder,newVerСut);
            }
            n++;
        }

        reconsiderTheDepth(horCutList,start,l);
    }

    public VerticalСut getEmptyBorder(List<VerticalСut> verCutList,int lineHeight,int indexEmptyBorder){

        indexEmptyBorder = 0;
        for(VerticalСut verticalСut:verCutList){
            if(!verticalСut.isFull() && (verticalСut.getK1()<=lineHeight) && (verticalСut.getK2()>=lineHeight)){
                return verticalСut;
            }
            indexEmptyBorder++;
        }
        return null;
    }

    public void reconsiderTheDepth(List<HorizontalCut> horCutList,int start,int l) {

        int n = start-1;
        int q = 0; //Счетчик
        int l1 = 0;

        while (n >= 0){
            List<VerticalСut> vertСutList = horCutList.get(n).getVerticalСutList();
            if(vertСutList.get(0).getHv()> l){
                if ((l1 == 0) || (l1 != vertСutList.get(0).getHv())){
                    l1 = vertСutList.get(0).getHv();

                    q = 0;
                    vertСutList.get(0).setHg(q);
                    q++;
                }
                else{
                    vertСutList.get(0).setHg(q);
                    q++;
                }
            }
            n--;
        }

    }

    public void markPartAsPosted(DoorPart doorPart,List<DoorPart> parts){

        int fineIndex = doorPart.getIndex();
        for(DoorPart lineDoorPart:parts){
            if(lineDoorPart.getIndex()==fineIndex){
                lineDoorPart.setPosted(true);
            }
        }
    }

    public List<DoorPart> getParts() {
        return parts;
    }

    public void setParts(List<DoorPart> parts) {
        this.parts = parts;
    }

    public List<Sheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public int getNumberOfSheets() {
        return numberOfSheets;
    }

    public void setNumberOfSheets(int numberOfSheets) {
        this.numberOfSheets = numberOfSheets;
    }

    public void clearHardCalculationData(){
        for(Sheet sheet:sheets){
            sheet.setHorCutList(null);
        }
    }
}
