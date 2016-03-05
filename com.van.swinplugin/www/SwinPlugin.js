var exec = require('cordova/exec');

function SwinPlugin() { 
	console.log("SwinPlugin.js: is created");
}

SwinPlugin.prototype.switchApp = function(){
	console.log("SwinPlugin.js: switchApp");
	exec(function(result){ /*alert("OK" + reply);*/ }, function(result){ /*alert("Error" + reply);*/ },"SwinPlugin", "switchApp", []);
}

var swinPlugin = new SwinPlugin();
module.exports = swinPlugin;