//Code by Walter Cheng

//loading nescessary modules and setting up constant value
const PORT = process.env.PORT || 3000;
//const ipAddress = "localhost";
const ipAddress = "45.77.110.134";
const express = require('express');
const app = express();

app.use(express.static(__dirname + '/public'));

 //catch all requests and log them
/*app.use('*', function(req, res, next){
   console.log(req.url+' request for '+req.url);
   next(); //allow next route or middleware to run
});*/

//setup the index page
app.get('/', function(req, res){
	res.sendFile(__dirname + '/public/portfolio.html');
});

app.get('/about', function(req, res){
	res.sendFile(__dirname + '/public/about.html');
});

app.get('/project', function(req, res){
	res.sendFile(__dirname + '/public/project.html');
});

app.get('/resume', function(req, res){
	res.sendFile(__dirname + '/public/resume.html');
});

app.get('/project-:number', function(req, res){
	res.sendFile(__dirname + '/public/project-' + req.params.number + ".html");
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