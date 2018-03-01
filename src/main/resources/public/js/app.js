
var currentSong = null;
var selectedSong = null;
var selectedGenre = null;

var main = function() {
    addGenreEventHandlers();
    initPlayer();
    initNextAndPrevious();
};

var loadSong = function() {
    // make it visible if this is the first song to be loaded.
    var currentSongElement = document.getElementById("player-currentsong");
    if (currentSong == null) {
        currentSongElement.style.display = "block";
    }

    if (currentSong == this) {
        return;
    }

    var player = document.getElementById("currentsong-audio");
    player.setAttribute("src", this.dataset["path"]);
    currentSong = this;
    var currentSongInfo = this.children[0];
    var image = currentSongInfo.children[0].getAttribute("src");
    currentSongInfo = currentSongInfo.children[1];

    var title = currentSongInfo.children[0].innerHTML;
    var length = currentSongInfo.children[1].innerHTML;
    var owner = currentSongInfo.children[2].innerHTML;

    currentSongElement = currentSongElement.children[0]; //switch to inside container.
    currentSongElement.children[0].setAttribute("src", image);
    $(".player-currentsong-title").html(title);
    $("#player-currentsong-length").html(length);
    $(".player-currentsong-owner").html(owner);

    selectSong($(this));

    playCurrentSong();
};

var addGenreEventHandlers = function() {
    var elements = document.getElementsByClassName("sidebar-genre");

    for (let element of elements) {
        element.addEventListener("click", loadGenre);
    }
};

var loadGenre = function() {
    selectGenre($(this));
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

    newElement.setAttribute("data-path", song["path"]);
    newElement.style.display = "inline-block";
    newElement.addEventListener("click", loadSong);
    document.getElementById("player-main-body").appendChild(newElement);
};

var initProgressBar = function() {
    var player = document.getElementById('currentsong-audio');
    var length = player.duration;
    var current_time = player.currentTime;

    // calculate current value time
    var currentTime = calculateCurrentValue(current_time);
    document.getElementById("player-currentsong-currenttime").innerHTML = currentTime;

    var progressbar = document.getElementById('seekObj');
    progressbar.value = player.currentTime === 0 ? 0 : (player.currentTime / player.duration);
    progressbar.addEventListener("click", seek);

    if (player.currentTime == player.duration) {
        $('#play-button').removeClass('glyphicon-pause');
        $('#play-button').addClass('glyphicon-play');
    }

    function seek(evt) {
        var percent = evt.offsetX / this.offsetWidth;
        player.currentTime = percent * player.duration;
        progressbar.value = percent / 100;
    }
};

var initPlayer = function() {
    // Variables
    var playerContainer = document.getElementById('audio-container'),
        player = document.getElementById('currentsong-audio'),
        isPlaying = false,
        playBtn = document.getElementById('play-button'),
        $volumeSlider = $("#volume-slider")[0],
        $volumeButton = $("#volume-control"),
        lastVolume = 0.5;

    // Controls Listeners
    // ----------------------------------------------------------
    if (playBtn != null) {
        playBtn.addEventListener('click', function() {
            togglePlay();
        });
    }

    player.volume = 0.5;
    $volumeButton.click(toggleMute);

    $volumeSlider.addEventListener("click", setVolume);
    $volumeSlider.addEventListener("change", setVolume);
    $volumeSlider.addEventListener("input", setVolume);

    // Controls & Sounds Methods
    // ----------------------------------------------------------
    function togglePlay() {
        var playButton = $('#play-button');
        if (currentSong !== null) {
            if (player.paused === false) {
                player.pause();
                isPlaying = false;
                playButton.removeClass('glyphicon-pause');
                playButton.addClass('glyphicon-play');
            } else {
                playCurrentSong();
                isPlaying = true;
            }
        }
    }

    function setVolume() {
        player.volume = $volumeSlider.value / 100;
        if ($volumeSlider.value > 50) {
            if (!($volumeButton.hasClass("glyphicon-volume-up"))) {
                $volumeButton.removeClass("glyphicon-volume-off");
                $volumeButton.removeClass("glyphicon-volume-down");
                $volumeButton.addClass("glyphicon-volume-up");
            }
        } else if ($volumeSlider.value > 0) {
            if (!($volumeButton.hasClass("glyphicon-volume-down"))) {
                $volumeButton.removeClass("glyphicon-volume-off");
                $volumeButton.removeClass("glyphicon-volume-up");
                $volumeButton.addClass("glyphicon-volume-down");
            }
        } else {
            if (!($volumeButton.hasClass("glyphicon-volume-off"))) {
                $volumeButton.removeClass("glyphicon-volume-down");
                $volumeButton.removeClass("glyphicon-volume-up");
                $volumeButton.addClass("glyphicon-volume-off");
            }
        }
    }

    function toggleMute() {
        if (!($volumeButton.hasClass("glyphicon-volume-off"))) {
            lastVolume = $volumeSlider.value;
            $volumeSlider.value = 0;
            setVolume();
        } else {
            $volumeSlider.value = lastVolume;
            setVolume();
        }
    }
};

var calculateCurrentValue = function(currentTime) {
    var current_hour = parseInt(currentTime / 3600) % 24,
        current_minute = parseInt(currentTime / 60) % 60,
        current_seconds_long = currentTime % 60,
        current_seconds = current_seconds_long.toFixed(),
        current_time = (current_minute) + ":" + (current_seconds < 10 ? "0" + current_seconds : current_seconds);

    return current_time;
};

var playCurrentSong = function() {
    document.getElementById("currentsong-audio").play();
    var $playButton = $("#play-button");

    if ($playButton.hasClass("glyphicon-play")) {
        $playButton.removeClass("glyphicon-play");
        $playButton.addClass("glyphicon-pause");
    }
};

var selectGenre = function($genreElement) {
    if (selectedGenre !== null) {
        selectedGenre.removeClass("selected-genre");
    }
    selectedGenre = $genreElement;
    selectedGenre.addClass("selected-genre");
};

var selectSong = function($songElement) {
    if (selectedSong !== null) {
        selectedSong.removeClass("selected-song");
    }
    selectedSong = $songElement;
    selectedSong.addClass("selected-song");
};

var initNextAndPrevious = function() {
    $("#next-button").on("click", function () {
        if (selectedSong !== null) {
            var selectedInside = selectedSong[0];
            var nextSong = selectedInside.nextElementSibling;
            if (nextSong === undefined || nextSong === null) {
                nextSong = $("#player-main-body").children()[0];
            }
            nextSong.click();
        }


    });

    $("#prev-button").on("click", function () {
        if (selectedSong !== null) {
            var selectedInside = selectedSong[0];
            var prevSong = selectedInside.previousElementSibling;
            if (prevSong === undefined || prevSong === null) {
                var siblings = $("#player-main-body").children();
                prevSong = siblings[siblings.length - 1];
            }
            prevSong.click();
        }
    });
};

$(main);