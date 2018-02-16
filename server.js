//Code by Walter Cheng

//loading nescessary modules and setting up constant value
//const ipAddress = "localhost";    //just for testing in localhost
//const PORT = 5000;

const ipAddress = "45.77.110.134";
const PORT = process.env.PORT || 80;
const fs = require('fs');
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

//getting the projects by different sorting method
app.post('/projects/:sort', function(req, res){
	data = fs.readFileSync(__dirname + '/json/project.json');
	info = JSON.parse(data);

	//to generate the return object
	outputDictionary = {'projects':[]};

	processDictionary = {};

	if (req.params.sort == "type")
	{
		for (var i = 0; i < info.projects.length; i++)
		{
			info.projects[i]['title'] = info.projects[i]['type'];

			if (!(info.projects[i]['type'] in processDictionary))
				processDictionary[info.projects[i]['type']] = [];
			processDictionary[info.projects[i]['type']].unshift(info.projects[i]);
		}
		
		for (var key in processDictionary)
		{
			for (var i = 0; i < processDictionary[key].length; i++)
			{
				outputDictionary['projects'].push(processDictionary[key][i]);
			}
		}
	}
	else if (req.params.sort == "date")
	{
		var counter = 1;
		while (info.projects.length != 0)
		{
			for (var i = 0; i < info.projects.length; i++)
			{
				if (counter == info.projects[i].index)
				{
					info.projects[i].title = "Latest to old";
					outputDictionary['projects'].unshift(info.projects[i]);
					info.projects.splice(i, 1);
					counter++;
					break;
				}
			}
		}
	}
	else if (req.params.sort == "language")
	{
		for (var i = 0; i < info.projects.length; i++)
		{
			info.projects[i]['title'] = "Code with " + info.projects[i]['technology'][0];
			if (!(info.projects[i]['title'] in processDictionary))
				processDictionary[info.projects[i]['title']] = [];
			processDictionary[info.projects[i]['title']].push(info.projects[i]);
		}

		for (var key in processDictionary)
		{
			for (var i = 0; i < processDictionary[key].length; i++)
			{
				outputDictionary['projects'].push(processDictionary[key][i]);
			}
		}
	}
	else if (req.params.sort == "group")
	{
		for (var i = 0; i < info.projects.length; i++)
		{
			if (info.projects[i]['group'])
				info.projects[i]['title'] = "Group Projects";
			else
				info.projects[i]['title'] = "Personal Projects";

			if (!(info.projects[i]['title'] in processDictionary))
				processDictionary[info.projects[i]['title']] = [];
			processDictionary[info.projects[i]['title']].unshift(info.projects[i]);
		}

		for (var key in processDictionary)
		{
			for (var i = 0; i < processDictionary[key].length; i++)
			{
				outputDictionary['projects'].push(processDictionary[key][i]);
			}
		}
	}

	res.setHeader('Content-Type', 'application/json');
	res.send(outputDictionary);
});

app.post('/tech', function(req, res){
	data = fs.readFileSync(__dirname + '/json/technology.json');
	info = JSON.parse(data);

	res.setHeader('Content-Type', 'application/json');
	res.send(info);
})

app.get("*", function(req, res){
	res.send(404);
});

app.listen(PORT, ipAddress, function(err){
	if (err)
		console.log(err);
	else
		console.log('Server started');
});