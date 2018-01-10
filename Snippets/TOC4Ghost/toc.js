$(".toc").remove();
var config = {

	"title": "文章目录",
	"contentWrapper": ".post-content",
	"contentStart": ".post-content>p:first"
};



var TOC = '<div class="toc">' + config.title + '<ul>';

var elements = $(config.contentWrapper).find(":header");
var currHeading = elements[0].nodeName;

if (elements.length > 0) {
	$.each(elements, function(key, content) {
		var text = content.innerText;
		var link = '<a href="#' + text + '">' + text  + '</a>';
		content.id = text;

		switch (currHeading.localeCompare(content.nodeName)) {
			case 0:
				TOC += '</li><li>' + link;
				break;

			case 1:
				TOC += '</li></ul><li>' + link;
				currHeading = content.nodeName;
				break;

			case -1:
				TOC += '<ul><li>' + link;
				currHeading = content.nodeName;
				break;
		}
		console.log(TOC);
	});
}

TOC += '</ul></div>';

$(config.contentStart).before(TOC);
