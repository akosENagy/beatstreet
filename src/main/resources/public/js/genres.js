let app = {};

app.genres = {
    addGenreEventHandlers: function() {
        let elements = document.getElementsByClassName("sidebar-genre");

        for (let element of elements) {
            element.addEventListener("click", app.genres.loadGenre);
        }
    },

    loadGenre: function () {
        if (!$(this).hasClass("selected-genre")) {
            app.genres.selectGenre($(this));
            let genre = this.children[0].innerHTML;

            $.get("http://localhost:60001/api/genres/" + genre, function (data) {
                let playerBodySongs = document.getElementById("player-body-songs");
                while (playerBodySongs.firstChild) {
                    playerBodySongs.removeChild(playerBodySongs.firstChild);
                }

                for (let song of data) {
                    app.songs.buildSongElement(song);
                }

                $("#player-body-header").text(genre);
                $("#most-recent").removeClass("selected");
            });
        }
    },

    selectGenre: function($genreElement) {
        if (app.musicplayer.selectedGenre !== null) {
            app.musicplayer.selectedGenre.removeClass("selected-genre");
        }
        app.musicplayer.selectedGenre = $genreElement;
        app.musicplayer.selectedGenre.addClass("selected-genre");
    },

    showGenres: function() {
        let $genres = $('.sidebar-genres-container');
        $("#genres-header").addClass("selected");
        $("#most-recent").removeClass("selected");
        $genres.show(300);
    }
};