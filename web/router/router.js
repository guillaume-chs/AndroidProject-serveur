var HomeController = require('./../controllers/HomeController');
var PersonneController = require("./../controllers/PersonneController");
var ConnexionController = require("./../controllers/ConnexionController");
// Routes
module.exports = function(app){
    // Main Routes
    app.get('/', HomeController.Index);

    app.post('/inscription', PersonneController.Inscription);

    //Connexion
    app.get('/connexion', ConnexionController.Connexion)
    app.post('/connexion', ConnexionController.Connect)

    //Déconnexion
    app.get('/deconnexion',ConnexionController.Deconnexion)

    //User
    app.get('/home', HomeController.Home)
    app.post('/home', HomeController.Home)

    //Default
    //TODO route par default


};
