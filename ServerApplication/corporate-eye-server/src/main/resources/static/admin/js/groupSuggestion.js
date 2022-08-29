var groupList = new Set([]);;

function removeFromGroupList(groupname) {
	groupList.delete(groupname);
	generateGroupList();
}

function addToGroupList(groupname) {


	groupList.add(groupname);

	console.log(groupList);

	$("#f_gname").val("");
	performGroupSearch("");
	generateGroupList();
}

function generateGroupList() {
	content = "";



	groupList.forEach(function(value) {

		content += '<div class="groupListItem border rounded"><div class="text-primary nameitem">' +
			value
			+ '</div><div class="deleteGroup" data-groupname="' + value + '"><i class="fa fa-close" style="color: red"></i></div></div>'


	});

	if (content.length == 0) {
		content = '<div class="alert alert-primary" style="padding:3px" role="alert">No groups in this rule!</div>';
	}

	$("#grouplist").html(content);
	$(".deleteGroup").off("click");
	$(".deleteGroup").on("click", function() {
		removeFromGroupList($(event.currentTarget).data('groupname'));
	});

}

function loggroup(message) {
	// console.loggroup(message);
	$("#loggroup").show();

	content = "<div class=\"list-group\">";

	$(message).each(function(index, val) {
		if (!groupList.has(val.groupName)) {


			content += '<a  class="list-group-item list-group-item-action suggestion-group-list-item" data-groupname="' + val.groupName + '" style="cursor:pointer">' + val.groupName + "</a>";
		}
	});

	content += "</div>";

	$("#loggroup").html(content);
	$("#loggroup").scrollTop(0);

	$('.suggestion-group-list-item').off("click");
	$('.suggestion-group-list-item').on('click', function(event) {
		// 'this' here = externalObject
		addToGroupList($(event.currentTarget).data('groupname'), $(event.currentTarget).text());
	});
}

function performGroupSearch(keyword) {

	if (keyword.length > 3) {
		$.ajax({
			url: "ajax/getGroupDetails?keyword=" + keyword,

			success: function(data) {
				loggroup(data);
			}
		});
	}
	else {
		$("#loggroup").hide();

	}
}

$(function() {


	$("#f_gname").on("keyup", function() {

		performGroupSearch($(this).val());

	});
});