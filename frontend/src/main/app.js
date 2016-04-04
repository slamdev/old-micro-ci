"use strict";

$(document).ready(function () {

    var endpointUrl = "/api/sample";

    $("#send").click(function () {
        $.ajax({
            url: endpointUrl,
            type: "GET"
        }).done(renderSuccessUpload).fail(renderError);
    });

    function renderSuccessUpload(result) {
        $("#result").text(result);
    }

    function renderError(result) {
        console.error("Ajax error occurred", result);
        $("#result").text("Error occurred. See console logs for details.");
    }
});