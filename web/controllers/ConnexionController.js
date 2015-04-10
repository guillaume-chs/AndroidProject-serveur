var model = require("../models/connexion.js");

module.exports.Connexion = function(request, response){
   response.title = "Connexion";
   response.render('connexion', response);
};

module.exports.Connect= function(request, response){
    var data ={"login":request.body.pseudo,"pass":request.body.mdp}

    model.getConnexion ( data,function (err, result) {
       if (err) {
           console.log(err);
           return;
       }
       if(result.length == 1){
         response.locals.session.pseudo = result[0].pseudoPers
         response.redirect("/home");
       }
    });
};
