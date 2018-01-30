$(document).ready(function(){
	getProjects();
});

//get the sorted projects from the server
function getProjects()
{
	$.ajax({
		method:"POST",
		url:"/projects/type",
		dataType:"json"
	})
	.done(function(data){
		$container = $('#homepage-projects');
		$container.empty();

		var currentTitle = "";
		var div_tag = "";

		//counting the number of each projects
		var tempDict = {};
		for (var i = 0; i < data.projects.length; i++)
		{
			if (data.projects[i].title in tempDict)
				tempDict[data.projects[i].title]++;
			else
				tempDict[data.projects[i].title] = 1;
		}

		var counter = 0;
		//render the tag according to the data server return
		for (var i = 0; i < data.projects.length; i++)
		{
			var project = data.projects[i];
			if (currentTitle != project.title)
			{
				currentTitle = project.title;
				div_tag += '<h3 class="w3-center text-font">'+project.title.toUpperCase()+'</h3>';;
				div_tag += '<div class="w3-row-padding row w3-center">';
			}

			div_tag += '<div class="w3-col m3">';
			div_tag += '<a href="/project#'+project.short+'">'
			div_tag += '<img src="'+project.image;
			div_tag += '" style="width:80%" class="w3-hover-opacity" alt="' + project.short + '">';
			div_tag += '</a>';
			div_tag += '</div>';

			if (i+1 == data.projects.length || currentTitle != data.projects[i+1].title)
			{
				div_tag += "</div><br/>";
			}	
		}

		$container.append(div_tag);
	});
}