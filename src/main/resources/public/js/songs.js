app.songs = {
    playCurrentSong: function() {
        document.getElementById("currentsong-audio").play();
        let $playButton = $("#play-button");

        if ($playButton.hasClass("glyphicon-play")) {
            $playButton.removeClass("glyphicon-play");
            $playButton.addClass("glyphicon-pause");
        }
    },

    loadSong : function() {
        // make it visible if this is the first song to be loaded.
        let currentSongElement = document.getElementById("player-currentsong");
        if (app.musicplayer.currentSong === null) {
            currentSongElement.style.display = "block";
        }

        if (app.musicplayer.currentSong === this) {
            return;
        }

        let player = document.getElementById("currentsong-audio");
        player.setAttribute("src", this.dataset["path"]);
        app.musicplayer.currentSong = this;
        let currentSongInfo = this.children[0];
        let image = currentSongInfo.children[0].getAttribute("src");
        currentSongInfo = currentSongInfo.children[1];

        let title = currentSongInfo.children[0].innerHTML;
        let length = currentSongInfo.children[1].innerHTML;
        let owner = currentSongInfo.children[2].innerHTML;

        currentSongElement = currentSongElement.children[0]; //switch to inside container.
        currentSongElement.children[0].setAttribute("src", image);
        $(".player-currentsong-title").html(title);
        $("#player-currentsong-length").html(length);
        $(".player-currentsong-owner").html(owner);

        app.songs.selectSong($(this));
        app.songs.playCurrentSong();
    },

    selectSong: function($songElement) {
        if (app.musicplayer.selectedSong !== null) {
            app.musicplayer.selectedSong.removeClass("selected-song");
        }
        app.musicplayer.selectedSong = $songElement;
        app.musicplayer.selectedSong.addClass("selected-song");
    },

    nextSong: function() {
        if (app.musicplayer.selectedSong !== null) {
            let selectedInside = app.musicplayer.selectedSong[0];
            let nextSong = selectedInside.nextElementSibling;
            if (nextSong === undefined || nextSong === null) {
                nextSong = $("#player-main-body").children()[0];
            }
            nextSong.click();
        }
    },

    previousSong: function() {
        if (app.musicplayer.selectedSong !== null) {
            let selectedInside = app.musicplayer.selectedSong[0];
            let prevSong = selectedInside.previousElementSibling;
            if (prevSong === undefined || prevSong === null) {
                let siblings = $("#player-main-body").children();
                prevSong = siblings[siblings.length - 1];
            }
            prevSong.click();
        }
    },

    buildSongElement: function(song) {
        let newElement = document.getElementById("playerbody-song-proto").cloneNode(true);
        newElement.removeAttribute("id");

        let songElementLeft = newElement.children[0];
        //let songElementRight = newElement.children[1];

        // let image = songElementLeft.children[0];
        // image.setAttribute("src", song["path"]); if image exists?

        let infoContainer = songElementLeft.children[1];
        let songTitle = infoContainer.children[0];
        let songLength = infoContainer.children[1];
        let songOwner = infoContainer.children[2];

        songTitle.innerHTML = song["title"];
        songOwner.innerHTML = song["owner"]["name"];
        songLength.innerHTML = (~~(song["lengthInSeconds"] / 60)) + ":" + song["lengthInSeconds"] % 60;

        if (song["lengthInSeconds"] % 60 === 0) songLength.innerHTML += "0";

        newElement.setAttribute("data-path", song["path"]);
        newElement.style.display = "inline-block";
        newElement.addEventListener("click", app.songs.loadSong);
        document.getElementById("player-main-body").appendChild(newElement);
        document.getElementById("player-main-body").appendChild(newElement);
    }
};