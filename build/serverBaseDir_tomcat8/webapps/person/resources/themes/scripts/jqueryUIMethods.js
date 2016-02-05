$(function() {
	$("input[type=submit], a, button").button();
});

$(function() {
    $( "#evalDueDate" ).datepicker({
        showOn: "button",
        buttonImage: "views/styles/images/calendar.gif",
        buttonImageOnly: true
    });
});