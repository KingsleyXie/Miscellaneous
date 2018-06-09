document.querySelectorAll("p").forEach(
	ele => {
		var txt = ele.innerHTML;
		// Starts with '#'
		if (!txt.indexOf('#')) {
			// Filter the ending '#'s
			txt = txt.replace(/([^#]+?)#+$/g, '$1');

			// Get the index of heading (before next paragraph)
			var idx = txt.indexOf('<');
			if (idx == -1) idx = txt.length;

			// Split heading size and content
			var heading = txt.substr(0, idx)
				.match(/(#+?)([^#]+)/);

			// Generate the corresponding HTML code
			var size = heading[1].length;
			var html =
				'<h' + size + '>' +
				heading[2] +
				'</h' + size + '>';

			// Replace '<p>' element with generated code
			ele.innerHTML = html + '<p>' + txt.substr(idx, txt.length);
		}
	}
);
