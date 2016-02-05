/**
 * Created by beaver on 2/4/16.
 */

var columnDefs = [

    {
        headerName: "Caption", field: "caption", width: 350, rowGroupIndex: 1, cellRenderer: {

        renderer: 'group'

    }, sort: 'asc', suppressSizeToFit: true
    },

    {
        headerName: "Company",
        field: "companyCaption",
        width: 250,
        rowGroupIndex: 0,
        sort: 'asc',
        suppressSizeToFit: true
    },

    {headerName: "Site", field: "siteCaption", width: 90, sort: 'asc', cellStyle: {'text-align': 'center'}},

    {headerName: "Date", field: "registrationDate", width: 90, sort: 'desc', cellStyle: {'text-align': 'center'}},

    {headerName: "Cur", field: "currencyCaption", width: 40, cellStyle: {'text-align': 'center'}},

    {headerName: "Price (max)", field: "priceMax", width: 80, cellStyle: {'text-align': 'right'}},

    {headerName: "Price (min)", field: "priceMin", width: 80, cellStyle: {'text-align': 'right'}},

    {headerName: "sCur", field: "systemCurrencyCaption", width: 40, cellStyle: {'text-align': 'center'}},

    {
        headerName: "Price (max)",
        field: "priceMinForCourseSystemCurrency",
        width: 80,
        cellStyle: {'text-align': 'right'}
    },

    {
        headerName: "Price (min)",
        field: "priceMaxForCourseSystemCurrency",
        width: 80,
        cellStyle: {'text-align': 'right'}
    },

    {headerName: "City", field: "cityCaption", width: 80, cellStyle: {'text-align': 'center'}},

    {headerName: "URL", field: "url", width: 250, onCellDoubleClicked: myCellDoubleClicked}
];

var gridOptions = {

    columnDefs: columnDefs,

    rowData: null,

    groupUseEntireRow: false,

    groupSuppressAutoColumn: true,

    enableSorting: true,

    enableColResize: true
};

function sizeToFit() {

    gridOptions.api.sizeColumnsToFit();

}

function myCellDoubleClicked(event) {

    var value = event.value;
    //console.log(event.value)
    if (value !== undefined)
        window.open(value);
}

function autoSizeAll() {

    var allColumnIds = [];

    columnDefs.forEach(function (columnDef) {

        allColumnIds.push(columnDef.field);

    });

    gridOptions.columnApi.autoSizeColumns(allColumnIds);

}


// setup the grid after the page has finished loading

document.addEventListener('DOMContentLoaded', function () {

    var gridDiv = document.querySelector('#vacancy-grid');

    new ag.grid.Grid(gridDiv, gridOptions);

    $.get('/vacancies/listdata', function (data) {
        gridOptions.api.setRowData(data.listModel)
    });

});
