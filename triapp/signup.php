<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createDriver();
	
}
function createDriver()
{
	global $connect;
$username =$_POST["username"];
$firstname =$_POST["firstname"];
$lastname =$_POST["lastname"];
$platenumber=$_POST["platenumber"];
$password= $_POST["password"];
$address= $_POST["address"];
$reg=$_POST["reg"];
if(!empty($username) && !empty($password)&& !empty($firstname)&& !empty($lastname)&& !empty($platenumber)&& !empty($address)&& !empty($reg)){
	$query = "Select * from driver where username='$username' ";
			$result = mysqli_query($connect, $query);
			if(mysqli_num_rows($result)>0){
				$json['error'] = 'username already exist';
				echo json_encode($json);
				mysqli_close($connect);
			}
			else{
$query = " Insert into driver(username,password,firstname,lastname,platenumber,address, reg) value ('$username','$password','$firstname', '$lastname', '$platenumber', '$address', '$reg');";
$json['success'] = 'account has been created';
echo json_encode($json);
mysqli_query($connect, $query) or die (mysqli_error($connect));
mysqli_close($connect);
}}
else{
	$json['error'] = 'you must type all fields';
				echo json_encode($json);
				mysqli_query($connect, $query) or die (mysqli_error($connect));
mysqli_close($connect);
}

}
?>