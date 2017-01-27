var exec = require('cordova/exec'),
	testService;

function TestService() {

	this.handleSuccess = function(data) {
		console.log("Success execute function ", data);
	}

	this.handleError = function(data) {
		console.error("Error while execute function ", data);
	}

	this.execute = function(fnName) {
		if(!fnName || (!(fnName instanceof String) && !(typeof fnName == "string"))) {
			console.error("Unknown function : " + fnName);
			return;
		}
		exec(this.handleSuccess, this.handleError, "TestPlugin", fnName, [{param: "testParam"}]);
	}

	this.test = function() {
		this.execute("test");
	}

	this.alarm = function() {
		this.execute("alarm");
	}

	this.job = function() {
		this.execute("job");
	}
}

testService = new TestService();

console.log("Exec JS in testService: ", testService);

module.exports = testService;