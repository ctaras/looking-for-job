$(function () {

    $('.datepicker').datepicker();

    $('.datatable').DataTable();

    $('.btn-remove-data').on('click', function (event) {
        event.preventDefault();

        var id = this.getAttribute("data-id");
        var message = this.getAttribute("data-message");
        var urlRest = this.href;
        var urlRedirect = this.getAttribute("data-url-redirect");

        bootbox.confirm(message, function (result) {
            if (result) {
                $.ajax({
                    url: urlRest,
                    type: "DELETE",
                })

                window.location.href = urlRedirect;
            }
        });
    });
});