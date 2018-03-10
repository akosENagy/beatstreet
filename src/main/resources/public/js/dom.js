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
	}
}