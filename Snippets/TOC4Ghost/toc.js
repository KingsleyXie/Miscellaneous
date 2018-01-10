var TOC = '<div class="toc">文章目录';
var currHeading = '';

var elements = $(".post-content").find(":header");

if (elements.length > 0) {
	$.each(elements, function(key, content) {
		if (content.nodeName != currHeading) {
			TOC += '</ul>' + '<ul>';
			currHeading = content.nodeName;
		}
		var text = content.innerText;

		content.id = text;
		TOC +=
		'<a href="#' + text + '">' +
			'<li>' + text + '</li>' +
		'</a>';
	});
}

TOC += '</div>';

$(".post-content>p:first").before(TOC);
