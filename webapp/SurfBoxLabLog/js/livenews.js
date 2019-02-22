/*
 *      Live News API
 *
 *      by, Andrew C.
 *
 */

$(function(){
    $.getJSON('core/news.json', function(data) {
        $("#txtNewsDate").text("Data published: " + data.liveNews.date);
        $("#txtNewsPublisher").text("by: " + data.liveNews.publisher);
        $("#txtNewsContent").text(data.liveNews.content);
    });
});