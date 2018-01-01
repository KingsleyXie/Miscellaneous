window.onload = function() {
	var imgSize = window.innerWidth < 520 ?
		(window.innerWidth * 0.5 - 22.4) : 150;
	var imgs = document.getElementsByClassName("avatar");

	for(var i = 0; i < imgs.length; i++)
	{
		imgs[i].style.maxHeight = imgSize + 'px';

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
