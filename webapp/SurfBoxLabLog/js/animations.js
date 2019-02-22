/*
 *      Main Animations
 *
 *       by, Andrew C.
 *
 */

function mainAnimations() {
    $("#txtTitle").hide();
    $("#navBar").hide();
    $("#txtInfo").hide();
    $("#txtTitle").fadeIn(1000);
    $("#txtInfo").slideToggle(1000);
}

function navBarToggle(x) {
    x.classList.toggle("change");
    $("#navBar").slideToggle();
}

$("")