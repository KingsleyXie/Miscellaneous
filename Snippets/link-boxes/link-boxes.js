window.onload = function() {
	reCenterImgs();

	addEventListener("resize", function() {
		console.log(window.innerWidth);
	});

	var mql = window.matchMedia("(max-width: 520px)");
	var matchState = mql.matches;

	mql.addListener(function(e) {
		//Width changes from (520-)px to (520+)px
		//Or width is less than 520px
		if ((e.matches != matchState)
			|| e.matches) {
			console.log("emmm");
			reCenterImgs();
			matchState = e.matches;
		}
	});
}

function reCenterImgs() {
	var imgSize = window.innerWidth < 520 ?
		(window.innerWidth * 0.5 - 22.4) : 150;
	var imgs = document.getElementsByClassName("avatar");

	for(var i = 0; i < imgs.length; i++)
	{
		imgs[i].style.maxHeight = imgSize + 'px';
		imgs[i].style.margin = '0 0';

		var rect = imgs[i].getBoundingClientRect();
		if (rect.width.toFixed(1) < imgSize) {
			imgs[i].style.margin = '0 ' + String(
				(imgSize - imgs[i].width) / 2
			) + 'px';
		}
		if (rect.height.toFixed(1) < imgSize) {
			imgs[i].style.margin = String(
				(imgSize - imgs[i].height) / 2
			) + 'px 0';
		}
	}
}
