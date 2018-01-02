window.onload = function() {
	reCenterImgs();

	addEventListener("resize", function() {
		var matches = (window.innerWidth < 520);
		formerState =
			(typeof formerState == "undefined") ?
			matches : formerState

		//Width changes from (520-)px to (520+)px
		//Or width is less than 520px
		if (matches != formerState
			|| matches) {
			reCenterImgs();
			formerState = matches;
		}
	});
}

function reCenterImgs() {
	var imgSize = parseFloat(
		getComputedStyle(
			document.getElementsByClassName("link-box")[0]
		).getPropertyValue("width")
	);
	var imgs = document.getElementsByClassName("avatar");

	for(var i = 0; i < imgs.length; i++) {
		imgs[i].style.maxHeight = imgSize + 'px';
		imgs[i].style.margin = '0 0';

		var vert = '0', horz = '0';
		var rect = imgs[i].getBoundingClientRect();

		if (parseFloat(rect.width.toFixed(1)) < imgSize)
			horz = ((imgSize - imgs[i].width) / 2).toFixed(1);
		if (parseFloat(rect.height.toFixed(1)) < imgSize)
			vert = ((imgSize - imgs[i].height) / 2).toFixed(1);

		imgs[i].style.margin = vert + 'px ' + horz + 'px';
	}
}
