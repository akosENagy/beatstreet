app.musicplayer = {

    currentSong: null,
    selectedSong: null,
    selectedGenre: null,

    initPlayer: function() {
        // Variables
        let player = document.getElementById('currentsong-audio'),
            isPlaying = false,
            playBtn = document.getElementById('play-button'),
            $volumeSlider = $("#volume-slider")[0],
            $volumeButton = $("#volume-control"),
            lastVolume = 0.5,
            cartButton = document.getElementById("cart-button"),
            cartModal = document.getElementById("cart-modal");

        // Controls Listeners
        // ----------------------------------------------------------
        if (playBtn !== null) {
            playBtn.addEventListener('click', togglePlay);
        }

        initNextAndPrevious();
        app.genres.addGenreEventHandlers();
        player.volume = 0.5;
        $volumeButton.click(toggleMute);

        app.dom.addNavbarListener();

        $volumeSlider.addEventListener("click", setVolume);
        $volumeSlider.addEventListener("change", setVolume);
        $volumeSlider.addEventListener("input", setVolume);

        app.dom.checkUploadButton();
        app.cart.getCart();

        cartButton.addEventListener("click", app.cart.displayModal);

        $(".close").click(function() {
           cartModal.style.display = "none";
        });

        window.onclick = function(event) {
            if (event.target == cartModal) {
                cartModal.style.display = "none";
            }
        };

        // Controls & Sounds Methods
        // ----------------------------------------------------------
        function togglePlay() {
            let playButton = $('#play-button');
            if (app.musicplayer.currentSong !== null) {
                if (player.paused === false) {
                    player.pause();
                    isPlaying = false;
                    playButton.removeClass('glyphicon-pause');
                    playButton.addClass('glyphicon-play');
                } else {
                    app.songs.playCurrentSong();
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

        function initNextAndPrevious() {
            $("#next-button").on("click", app.songs.nextSong);
            $("#prev-button").on("click", app.songs.previousSong);
        }
    },

    initProgressBar: function() {
        let player = document.getElementById('currentsong-audio');
        //let length = player.duration;
        let current_time = player.currentTime;

        // calculate current value time
        document.getElementById("player-currentsong-currenttime").innerHTML = calculateCurrentValue(current_time);

        let progressbar = document.getElementById('seekObj');
        progressbar.value = player.currentTime === 0 ? 0 : (player.currentTime / player.duration);
        progressbar.addEventListener("click", seek);

        if (player.currentTime === player.duration) {
            app.songs.nextSong();
        }


        // HELPER FUNCTIONS

        function seek(evt) {
            let percent = evt.offsetX / this.offsetWidth;
            player.currentTime = percent * player.duration;
            progressbar.value = percent / 100;
        }

        function calculateCurrentValue(currentTime) {
            let current_minute = parseInt(currentTime / 60) % 60,
                current_seconds_long = currentTime % 60,
                current_seconds = current_seconds_long.toFixed();
            return (current_minute) + ":" + (current_seconds < 10 ? "0" + current_seconds : current_seconds);
        }
    }
};