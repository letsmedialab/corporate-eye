var userList = {};

function removeFromUserList(username) {
	delete userList[username];
	generateUserList();
}

function addToUserList(username, name) {


	userList[username] = name;

	console.log(userList);

	$("#f_name").val("");
	performUserSearch("");
	generateUserList();
}

function generateUserList() {
	content = "";



	Object.entries(userList).forEach(function([key, value]) {

		content += '<div class="userListItem border rounded"><div class="text-primary nameitem">' +
			value
			+ '</div><div class="deleteUser" data-username="' + key + '"><i class="fa fa-close" style="color: red"></i></div></div>'


	});

	if (content.length == 0) {
		content = '<div class="alert alert-primary" style="padding:3px" role="alert">No users in this rule!</div>';
	}
console.log(content);
	$("#userlist").html(content);
	$(".deleteUser").off("click");
	$(".deleteUser").on("click", function() {
		removeFromUserList($(event.currentTarget).data('username'));
	});

}

function loguser(message) {
	// console.log(message);
	$("#loguser").show();

	content = "<div class=\"list-group\">";

	$(message).each(function(index, val) {
		if (userList[val.username] == undefined) {


			content += '<a  class="list-group-item list-group-item-action suggestion-list-item" data-username="' + val.username + '" style="cursor:pointer">' + val.name + "</a>";
		}
	});

	content += "</div>";

	$("#loguser").html(content);
	$("#loguser").scrollTop(0);

	$('.suggestion-list-item').off("click");
	$('.suggestion-list-item').on('click', function(event) {
		// 'this' here = externalObject
		addToUserList($(event.currentTarget).data('username'), $(event.currentTarget).text());
	});
}

function performUserSearch(keyword) {

	if (keyword.length > 3) {
		$.ajax({
			url: "ajax/getUserDetails?keyword=" + keyword,

			success: function(data) {
				loguser(data);
			}
		});
	}
	else {
		$("#loguser").hide();

	}
}

$(function() {


	$("#f_name").on("keyup", function() {

		performUserSearch($(this).val());

	});
});