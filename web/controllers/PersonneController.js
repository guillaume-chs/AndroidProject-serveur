var model = require("../models/personne.js");

module.exports.Inscription = function(request, response){
    var data ={"login":request.body.pseudo,"email":request.body.email,"pass":request.body.mdp}

    model.inscription( data,function (err, result) {
       if (err) {
           console.log(err);
           return;
       }
       response.title = "Inscription";
       response.render('inscription', response);
    });
};
