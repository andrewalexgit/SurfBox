/*
 *      User Data Handler
 *
 *       by, Andrew C.
 *
 */

var userData;

// Main load of user info
$(function() {
    $.getJSON("core/user.json", function(data) {
        userData = data;
        console.log(userData);
        $("#txtUsername").text("Welcome back " + userData.userInfo.name + "!");
        $(".avatar").append("<img class=\"imgAvatar\" src=\""+ userData.userInfo.profilePicture + "\" alt=\""+ userData.userInfo.systemNickname +"\">");
        $("#txtProfile").text("Your " + userData.userInfo.experience + " level " + userData.userInfo.systemNickname + " readings for today!");
    });
});