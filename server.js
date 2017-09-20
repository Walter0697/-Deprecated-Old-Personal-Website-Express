//Code by Walter Cheng

//loading nescessary modules
const express = require('express');
const app = express();
const PORT = process.env.PORT || 3000;

//setup the index page
app.get('/', function(req, res){
	res.send("Hello World!");
});

app.listen(PORT, err => {
	if (err)
		console.log(err);
	else
		console.log('Server started')
});