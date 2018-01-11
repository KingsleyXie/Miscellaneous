function addHeading(val) {
	if (val == '') return;
	var ele = document.createElement(val);
	var txt = document.createTextNode("Sample Heading " + val.replace('h', ''));
	ele.appendChild(txt);

	var post = document.getElementById("post-content");
	post.appendChild(ele);
}

function generateTOC() {
	$(".toc").remove();
	var config = {
		"title": "Table Of Contents",
		"contentWrapper": ".post-content"
	};

	var TOC = '<div class="toc">' + config.title + '<ul>';

	var elements = $(config.contentWrapper).children(":header");
	var currHeading = elements[0].nodeName;
	var records = new Array();

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
					currHeading = content.nodeName;
					while (records.includes(currHeading)) {
						TOC += '</li></ul>';
						records.pop();
					}
					TOC += '<li>' + link;
					break;

				case -1:
					TOC += '<ul><li>' + link;
					records.push(currHeading);
					currHeading = content.nodeName;
					break;
			}
		});
	}

	TOC += '</ul></div>';

	$(config.contentWrapper).find(">:first-child").before(TOC);
}
