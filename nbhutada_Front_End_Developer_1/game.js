// --== CS400 File Header Information ==--
// Name: Neil Bhutada
// Email: nbhutada@wisc.edu
// Team: MG
// Role: Front end developer 1
// TA: Harit 
// Lecturer: Florian
// Notes to Grader: Used Autoformat as adviced per TA. The size of scene is 600 x 600. 
function updateBossMeter(meter) //Updates boss's meter
{
    document.getElementById("boss").value = meter;
}
function updateUserMeter(meterValue) //Updates user's meter
{
    document.getElementById("user").value = meterValue;
}
function updateUserMeterDelay(meterValue) //Update user's meter with delay so that player can see boss's attack
{
	setTimeout(function() {updateUserMeter(meterValue);}, 1000);

}
function updateImage(image = "Choose Player.png",name = "",value = 0.0) //Loads the images at the begininng of the game
{
    document.getElementById("Image_3").src = image;
    document.getElementById("User").innerHTML = name;
    document.getElementById("user").value = value;
}
function lost() //Will print on screen that the person lost the game
{
	document.getElementById("Status").innerHTML = "YOU'VE LOST THE GAME";
}
function won() //Will print on screen the user won the game
{
	document.getElementById("Status").innerHTML = "YOU'VE WON THE GAME";
}
function updateHeader(text) //Updates the header to display the boss's move/user's status 
{
	document.getElementById("Head").innerHTML = text;
	setTimeout(function() {document.getElementById("Head").innerHTML = "GARY'S REVENGE";}, 1000);

}
function updateHeaderDelay(text) //Updates header with delay so that player can see what move the boss made
{
	setTimeout(function() {document.getElementById("Head").innerHTML = text},1000);
	setTimeout(function() {document.getElementById("Head").innerHTML = "GARY'S REVENGE";}, 2000);

}

