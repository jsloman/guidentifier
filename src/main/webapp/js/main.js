"use strict";

function init() {
	var apisToLoad;
	var callback = function() {
		if (--apisToLoad == 0) { 
			ReactDOM.render(React.createElement(CategoryBox), document.querySelector('#content'));
		}
	}

	apisToLoad = 1; // must match number of calls to gapi.client.load()
	gapi.client.load('guidentifierApi', 'v1', callback, 'http://localhost:8888/_ah/api');
}