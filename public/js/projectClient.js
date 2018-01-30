var technology = ""
var $temp_hash;

$(document).ready(function(){
	getTech();
	getProjects("type");

	$("#sortBtn").click(function(){
		$(".sorting").outerWidth($("#sortBtn").outerWidth());
		sortButton();
	});

	$(".sorting").click(function(){
		getProjects(this.id);
		sortButton();
		$('#sortBtn').text("Sort by " + this.id);
		$('#sortBtn').append("<i class='fa fa-arrow-down w3-margin-left'></i>");
	})

	$('#testing').click(function(){ getProjects("group"); });
});

//get the sorted projects from the server
function getProjects(sort_method)
{
	$container = $('#all-projects');

	$.ajax({
		method:"POST",
		url:"/projects/"+sort_method,
		dataType:"json"
	})
	.done(function(data){
		$container.empty();

		var currentTitle = "";
		
		//render the tag according to the data server return
		for (var i = 0; i < data.projects.length; i++)
		{
			var project = data.projects[i];
			div_tag = ''
			if (currentTitle != project.title)
			{
				currentTitle = project.title
				div_tag = '<p id="game" class="w3-large w3-center text-font w3-padding-32">'+currentTitle+'</p>';
			}
		
			div_tag += '<div id="'+project.short+'" class="project">';
			div_tag += '<div class="media">';

			div_tag += '<div class="media-left">';
			div_tag += '<img class="d-flex align-self-center mr-3" src="'+project.image+'" alt="'+project.short+'" style="width:250px"/><br/>';
			div_tag += '</div>';

			div_tag += '<div class="media-body text-center">';
			div_tag += '<h2>';
			if (project.icon)
				div_tag += '<img src="'+project.icon+'"" class="icon-32" />';

			div_tag += project.name+'<br/>';

			if (project.github)
				div_tag += '<a href="'+project.github+'"><i class="fa fa-github-square"></i>&nbsp;<span style="font-size:12px">Code here!</span></a></h2><br/>';
			else
				div_tag += '</h2><br/>';

			if (project.hackathon)
			{
				div_tag += '<a href="'+project.hackathon.link+'" class="w3-margin-left">';
				div_tag += 'Project in '
				div_tag += '<img src="'+project.hackathon.image+'" class="icon-32"/>';
				div_tag += '    '+project.hackathon.name+'</a><br/>'; 
				div_tag += '<a href="'+project.hackathon.devpost+'">DevPost Here!</a><br/>';
			}

			div_tag += '<h5>Technology used:</h5>';
			div_tag += '<p style="font-size:15px">';
			project['technology'].forEach(function(tech){
				div_tag += technology[tech];
			});

			div_tag += '</p>';

			project['description'].forEach(function(sentence)
			{
				div_tag += '<p>' + sentence + '<br/></p>';
			});

			if (project.youtube)
			{
				div_tag += '<p style="color:#666666">Youtube Video: <a href="'+project.youtube+'" class="fa fa-youtube-play"></a></p>';
			}

			if (project.download)
				for (var key in project.download)
				{
					div_tag += '<p style="color:#666666">' + key;
					div_tag += '<a href="'+ project.download[key]+'" class="fa fa-download"></a></p>';
				}			

			if (project.extra)
				div_tag += project.extra;

			div_tag += '</div></div></div><br/>';

			$container.append(div_tag);
		}

		//after rendering the method, see if there is any target from the path
		var hash = window.location.hash;
		if (hash !== "")
		{
			$('html, body').animate({
				scrollTop: $(hash).offset().top
			}, 800);
		}
	});
}

//get the icon dictionry to display the technology
function getTech()
{
	$.ajax({
			method:"POST",
			url:"/tech",
			dataType:"json"
	})
	.done(function(data){
		technology = data.technology;
	});
}

//toggle the button to get the drop down menu
function sortButton()
{
  document.getElementById("sortDropdown").classList.toggle("show");
}