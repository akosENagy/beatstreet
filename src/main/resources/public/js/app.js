
var currentSong = null;

var main = function() {
    addGenreEventHandlers();
};

var loadSong = function() {
    // make it visible if this is the first song to be loaded.
    if (currentSong == null) {
        document.getElementById("player-currentsong").style.display = "block";
    }


    currentSong = this;
    // TODO setup current song element
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

    var newElement = document.getElementById("playerbody-song-proto").cloneNode(true);
    newElement.removeAttribute("id");

    var songElementLeft = newElement.children[0];
    var songElementRight = newElement.children[1];

    var image = songElementLeft.children[0];
    // image.setAttribute("src", song["path"]); if image exists?

    var infoContainer = songElementLeft.children[1];
    var songTitle = infoContainer.children[0];
    var songLength = infoContainer.children[1];
    var songOwner = infoContainer.children[2];

    songTitle.innerHTML = song["title"];
    songOwner.innerHTML = song["owner"]["name"];
    songLength.innerHTML = (~~(song["lengthInSeconds"] / 60)) + ":" + song["lengthInSeconds"] % 60;

    if (song["lengthInSeconds"] % 60 == 0) songLength.innerHTML += "0";

    newElement.style.display = "inline-block";
    newElement.addEventListener("click", loadSong);
    document.getElementById("player-main-body").appendChild(newElement);
};


$("document").ready(main);