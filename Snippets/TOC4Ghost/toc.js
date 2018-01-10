$(".toc").remove();
var TOC = '<div class="toc">文章目录<ul>';

var elements = $(".post-content").find(":header");
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

$(".post-content>p:first").before(TOC);
