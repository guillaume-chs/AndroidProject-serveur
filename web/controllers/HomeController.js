var f = require("../fonctions.js");

  // ////////////////////////////////////////////// A C C U E I L
module.exports.Index = function(request, response){
    response.title = "Vos photos en ligne";
    response.render('index', response);
};

module.exports.Home = function(request, response){
    if(response.locals.session.pseudo){
      response.title = "Vos photos en ligne";
      response.render('home', response);
    }
    else{
      response.redirect('/');
    }
};

module.exports.NotFound = function(request, response) {
    response.title = "Erreur 404";
    response.image = f.getRandomInt(1, 7);
    response.render("404", response);
};

module.exports.NotAllowed = function(request, response) {
    response.title = "Erreur 403";
    response.render("403", response);
};
