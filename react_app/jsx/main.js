import * as React from "react";
import * as ReactDOM from "react-dom"
import Category from "./Category";

function init() {
	console.log("blah")
	var apisToLoad;
	var callback = function() {
		console.log("splat")

		if (--apisToLoad == 0) { 
			ReactDOM.render(React.createElement(Category.CategoryBox), document.querySelector('#content'));
		}
	}

	apisToLoad = 1; // must match number of calls to gapi.client.load()
	gapi.client.load('guidentifierApi', 'v1', callback, 'http://localhost:8888/_ah/api');
}

window.init = init