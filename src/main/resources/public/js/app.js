
var main = function() {
    addGenreEventHandlers();

};


var addGenreEventHandlers = function() {
    var elements = document.getElementsByClassName("sidebar-genre");

    for (let element of elements) {
        element.addEventListener("click", loadGenre);
    }
};

var loadGenre = function() {
    var genre = this.children[0].innerHTML;
    $.get("http://localhost:60001/api/genres/" + genre, function(data) {

        alert(data);
        // data == list of songs from that genre (JSON array).
        // TODO : load songs into main window.
    });
};

$("document").ready(main);