let app = {};

app.genres = {
    addGenreEventHandlers: function() {
        let elements = document.getElementsByClassName("sidebar-genre");

        for (let element of elements) {
            element.addEventListener("click", app.genres.loadGenre);
        }
    },

    loadGenre: function () {
        app.genres.selectGenre($(this));
        let genre = this.children[0].innerHTML;
        $.get("http://localhost:60001/api/genres/" + genre, function (data) {
            let playerBody = document.getElementById("player-main-body");
            while (playerBody.firstChild) {
                playerBody.removeChild(playerBody.firstChild);
            }

            for (let song of data) {
                app.songs.buildSongElement(song);
            }
        });
    },

    selectGenre: function($genreElement) {
        if (app.musicplayer.selectedGenre !== null) {
            app.musicplayer.selectedGenre.removeClass("selected-genre");
        }
        app.musicplayer.selectedGenre = $genreElement;
        app.musicplayer.selectedGenre.addClass("selected-genre");
    },
};