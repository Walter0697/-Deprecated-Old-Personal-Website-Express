var showNumber;

$(document).ready(function(){
	getProjects();
});

//get the sorted projects from the server
function getProjects()
{
	$.ajax({
		method:"POST",
		url:"/projects/date",
		dataType:"json"
	})
	.done(function(data){
		$container = $('#homepage-projects');
		$container.empty();

		var div_tag = "";
		//can be changed in the future
		showNumber = data.projects.length;

		//render the tag according to the data server return
		for (var i = 0; i < showNumber; i++)
		{

			if (i % 4 == 0)
				div_tag += '<div class="w3-row-padding row w3-center">';
			
			div_tag += '<div class="w3-col m3">';
			div_tag += '<a href="/project#'+data.projects[i].short+'">';
			div_tag += '<img src="'+data.projects[i].image;
			div_tag += '" style="width:80%" class="w3-hover-opacity" alt="' + data.projects[i].short + '">';
			div_tag += '</a>';
			div_tag += '</div>';

			if (i % 4 == 3)
				div_tag += "</div><br/>";
		}

		$container.append(div_tag);
	});
}