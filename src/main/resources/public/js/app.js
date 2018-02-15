
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
        var playerBody = document.getElementById("player-main-body");
        while (playerBody.firstChild) {
            playerBody.removeChild(playerBody.firstChild);
        }

        for (let song of data) {
            buildSongElement(song);
        }
    });
};

var buildSongElement = function(song) {
    var playerBody = document.getElementById("player-main-body");

    var songTitle = document.createElement("h3");
    songTitle.className += "player-body-song-title";
    songTitle.innerHTML = song["title"];

    //title, id, path, princeincents, owner, lengthinseconds

    var songOwner = document.createElement("p");
    songOwner.className += "player-body-song-owner";
    songOwner.innerHTML = song["owner"]["name"];

    var songDiv = document.createElement("div");
    songDiv.className += "playerbody-song";

    songDiv.appendChild(songTitle);
    songDiv.appendChild(songOwner);
    playerBody.appendChild(songDiv);
};

$("document").ready(main);