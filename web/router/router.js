var HomeController = require('./../controllers/HomeController');
var PersonneController = require("./../controllers/PersonneController");
var ConnexionController = require("./../controllers/ConnexionController");
// Routes
module.exports = function(app){
    // Main Routes
    app.get('/', HomeController.Index);

    app.post('/inscription', PersonneController.Inscription);

    app.get('/connexion', ConnexionController.Connexion)
};
