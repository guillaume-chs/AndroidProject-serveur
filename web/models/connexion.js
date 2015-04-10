var crypto=require('crypto');
var db = require('../configDb');

module.exports.getConnexion = function (data, callback) {
  db.getConnection(function(err, connexion){
  	if(!err){
      var login = data.login;
      var shasum = crypto.createHash('sha1');
      shasum.update("foo");
      var pass = shasum.digest(data.pass);
    	req= "Select * from personne where pseudoPers ="+connexion.escape(login)+" and mdpPers = '"+pass+"'";
     	connexion.query(req, callback);
     	connexion.release();
    }
  });
};
