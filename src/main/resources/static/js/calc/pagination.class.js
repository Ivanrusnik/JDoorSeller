class PaginationPage {

    static AMOUNT_ELEMENTS_IN_PAGE = 8;

    tabSize;
    amountElements;
    amountPag;
    biasInt;

    static init(nameClass) {
    }

    static generate(tab, bias, idMasterDiv) {
        let offsetTab = new PaginationPage();
        offsetTab.tabSize = tab.length;
        offsetTab.amountElements = PaginationPage.AMOUNT_ELEMENTS_IN_PAGE;
        offsetTab.amountPag = (
            offsetTab.tabSize / offsetTab.amountElements
        ).toFixed(0);
        offsetTab.biasInt = Number.parseInt(bias) * offsetTab.amountElements;

        $(".toolbarPageButton").remove();

        if (offsetTab.amountPag > 0) {
            for (let i = 0; i < offsetTab.amountPag; ++i) {
                $("<button>")
                    .attr("type", "button")
                    .attr("class", "btn btn-outline-dark toolbarPageButton")
                    .attr("data", i)
                    .text(i + 1)
                    .appendTo("#" + idMasterDiv);
            }
        }
        return offsetTab;
    }

    static show (){
        $("#toolbarPageDiv").removeClass("ghost");
    }

    static hide (){
        $("#toolbarPageDiv").addClass("ghost");
    }
}
