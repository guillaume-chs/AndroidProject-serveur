var crypto=require('crypto');
var db = require('../configDb');

module.exports.inscription = function (data, callback) {
  db.getConnection(function(err, connexion){
  	if(!err){
      var login = data.login;
      var email = data.email;
      var shasum = crypto.createHash('sha1');
      shasum.update("foo");
      var pass = shasum.digest(data.pass);
    	req= "Insert into personne (pseudoPers,mdpPers,emailPers) values("+connexion.escape(login)+",'"+pass+"',"+connexion.escape(email)+")";
     	connexion.query(req, callback);
     	connexion.release();
    }
  });
};
