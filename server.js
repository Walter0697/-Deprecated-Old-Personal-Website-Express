//Code by Walter Cheng

//loading nescessary modules and setting up constant value
const PORT = process.env.PORT || 8080;
//const ipAddress = "localhost";
const ipAddress = "45.77.110.134";
const express = require('express');
const app = express();

app.use(express.static(__dirname + '/public'));

//setup the index page
app.get('/', function(req, res){
	res.sendFile(__dirname + '/public/homepage.html');
});

app.get('/project', function(req, res){
	res.sendFile(__dirname + '/public/projectpage.html');
});

app.get('/grade', function(req, res){
	res.contentType('application/pdf');
    res.sendFile(__dirname + '/public/gradepage.pdf');
});

app.get("*", function(req, res){
	res.send(404);
});

app.listen(PORT, ipAddress, function(err){
	if (err)
		console.log(err);
	else
		console.log('Server started');
});