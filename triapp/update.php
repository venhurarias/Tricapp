<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createDriver();
	
}
function createDriver()
{
	global $connect;

$rideid =$_POST["rideid"];
$username=$_POST["username"];

	
$query = "UPDATE ride SET status='Done', driverpicked='$username', price=' ' WHERE rideid=$rideid";
$json['success'] = 'ride has been taken';
echo json_encode($json);
mysqli_query($connect, $query) or die (mysqli_error($connect));
mysqli_close($connect);


}
?>