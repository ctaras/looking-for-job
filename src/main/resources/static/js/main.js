$(function () {

    $('.datepicker').datepicker();

    $('.datatable').DataTable();

    $(document).on('click', '.btn-remove-data', function (event) {
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

    $(document).on('click', '.btn-viewed', function (event) {
        event.preventDefault();

        var row = $(this).parent().parent().parent();

        if (row.hasClass("notviewed")) {
            var urlRest = this.href;

            $.ajax({
                url: urlRest,
                type: "PUT"
            }).done(function (data) {
                row.removeClass("notviewed").addClass("viewed");
            });
        }
    });
});