/***************************************************************************
  * A Javascript code to set "target=_blank" for `<a>` tags
  * Links with different hostname will be opened in a new tab then
  * Inspired By: https://i-meto.com/js-outlink/
  *************************************************************************/

// Implementation With JQuery:
$(document).ready(function($) {
	$("a[href*='https://']:not([href^='" +
		"https://" + location.hostname+ "'])")
	.attr("target","_blank");

	$("a[href*='http://']:not([href^='" +
		"http://" + location.hostname + "'])")
	.attr("target", "_blank");
});

// Implementation Without JQuery:
window.onload = function() {
	document.querySelectorAll(
		"a[href*='https://']:not([href^='" +
		"https://" + location.hostname + "'])"
	).forEach(function(ele) {
		ele.target = "_blank";
	});

	document.querySelectorAll(
		"a[href*='http://']:not([href^='" +
		"http://" + location.hostname + "'])"
	).forEach(function(ele) {
		ele.target = "_blank";
	});
}
