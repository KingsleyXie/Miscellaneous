var TOC = '<div class="toc">';

for (var i = 1; i <= 6; i++) {
	var elements = $(".post-content>h" + i);

	if (elements.length > 0) {
		TOC += '<ul>';
		$.each(elements, function(key, content) {
			var text = content.innerText;

			content.id = text;
			TOC +=
			'<a href="#' + text + '">' +
				'<li>' + text + '</li>' +
			'</a>';
		});
		TOC += '</ul>';
	}
}
TOC += '</div>';

$(".post-content>p:first").before(TOC);
