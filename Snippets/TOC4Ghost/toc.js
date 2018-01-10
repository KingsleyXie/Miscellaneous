var toc = '<div class="toc">';

for (var i = 1; i <= 6; i++) {
	var ele = $(".post-content>h" + i);

	if (ele.length > 0) {
		toc += '<ul>';
		$.each(ele, function(k, v) {
			toc += '<li>' + v.innerText + '</li>';
		});
		toc += '</ul>';
	}
}
toc += '</div>';

$(".post-content>p:first").before(toc);
