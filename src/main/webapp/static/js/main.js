let main = document.getElementById("main");

feather.replace({ 'aria-hidden': 'true' });

window.onload = function() {
	var anchorElements = document.getElementsByTagName('a');
	for (var i in anchorElements)
		anchorElements[i].onclick = function() {
			console.log(this.href);
			console.log("-------------------");
			console.log(this.href.match(/([^\/]*)\/*$/)[1]);
			loadInnerHtml(this.href);
			return false;
		}
}

function loadInnerHtml(url_data) {
	$.ajax({
		type: "GET",
		url: url_data,
		success: function(resp) {
			main.innerHTML = resp;
			let scriptName = url_data.match(/([^\/]*)\/*$/)[1];
			changeScript(scriptName + ".js");
		}
	});
}

function changeScript(value) {
	var s = document.createElement("script");
	s.type = "text/javascript";
	s.src = "static/js/" + value;
	s.innerHTML = null;
	s.id = "new_script";
	document.getElementById("div_script").innerHTML = "";
	document.getElementById("div_script").appendChild(s);
}
