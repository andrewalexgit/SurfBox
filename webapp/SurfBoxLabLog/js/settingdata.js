/*
 *      Config Data Handler
 *
 *       by, Andrew C.
 *
 */

var sysData;

// Main load of system info
$(function() {
    $.getJSON("core/config.json", function(data) {
        sysData = data;
        console.log(sysData);
        $("#txtPort").text("Server port " + sysData.settings.port);
    });
});