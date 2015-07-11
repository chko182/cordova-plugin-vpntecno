/*global cordova, module*/

module.exports = {
    greet: function (name, successCallback, errorCallback) {
      console.log("hey1");
        cordova.exec(successCallback, errorCallback, "Hello", "greet", [name]);
    }
};
