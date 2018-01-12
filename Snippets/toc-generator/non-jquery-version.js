const config = {
	"title": "Table Of Contents",
	"contentWrapper": ".post-content"
};

var elements = Array.prototype.filter.call(
	document.querySelector(config.contentWrapper)
	.querySelectorAll("h1,h2,h3,h4,h5,h6"),

	function(ele) {
		var result = true;

		document.querySelectorAll("blockquote")
		.forEach(function(bq) {
			bq.querySelectorAll("h1,h2,h3,h4,h5,h6")
			.forEach(function(v) {
				if (ele == v) result = false;
			});
		});

		return result;
	}
);

if (elements.length > 0) {
	var TOC = '<div class="toc">' + config.title + '<ul>';

	var currHeading = elements[0].nodeName;
	var records = new Array();

	elements.forEach(function(content) {
		var text = content.innerText;
		var link = '<a href="#' + text + '">' + text  + '</a>';
		content.id = text;

		switch (currHeading.localeCompare(content.nodeName)) {
			case 0:
				TOC += '</li><li>' + link;
				break;

			case 1:
				currHeading = content.nodeName;

				if (!records.includes(currHeading)) {
					console.warn(
						'Warning: There may be some problem with your heading structure, ' +
						'so the generated TOC is not guaranteed to be in right order.'
					);
				}

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

	TOC += '</ul></div>';
	document.querySelector(config.contentWrapper).children[0]
	.insertAdjacentHTML('beforebegin', TOC);
} else {
	console.warn('No heading found to generate TOC.');
}
