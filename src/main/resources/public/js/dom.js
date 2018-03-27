app.dom = {

	addNavbarListener: function() {
		let $window = $(window);
		$window.scroll(function() {
			if ($window.scrollTop() > 650) {
				$('.navbar').fadeOut(300);
			}
			if ($window.scrollTop() < 650) {
				$('.navbar').fadeIn(300);
			}
		});
	},

	checkPasswords: function() {
		let pass = document.getElementById("password").value;
		let confirm = document.getElementById("pw-confirm").value;

        if (pass !== confirm) {
            alert("Passwords do not match!");
            document.getElementById("password").style.borderColor = "#E34234";
            document.getElementById("pw-confirm").style.borderColor = "#E34234";
            return false;
        }

        return true;
	},

	checkUploadButton: function() {
		let uploadButton = document.getElementById("upload-link");
		if (uploadButton === null) {
			document.getElementsByClassName("player-header-container")[0].style.float = "right";
		}
	}
};