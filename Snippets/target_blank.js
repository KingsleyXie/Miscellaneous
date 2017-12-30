/***************************************************************************
  * A Javascript code to set "target=_blank" for `<a>` tags using JQuery
  * Links with different hostname will be opened in a new tab then
  * Inspired By: https://i-meto.com/js-outlink/
  *************************************************************************/

$(document).ready(function($) {
	$("a[href*='http://']:not([href^='" +
		"http://" + location.hostname + "'])")
	.attr("target", "_blank");

	$("a[href*='https://']:not([href^='" +
		"https://" + location.hostname+ "'])")
	.attr("target","_blank");
});
