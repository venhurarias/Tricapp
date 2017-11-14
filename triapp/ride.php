<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createDriver();
	
}
function createDriver()
{
	global $connect;
$place =$_POST["place"];
$note =$_POST["note"];
$driver =$_POST["driver"];
$price=$_POST["price"];

if(!empty($place) && !empty($note)&& !empty($driver)&& !empty($price)){
	
$query = " Insert into ride(place,note,driver, price) value ('$place','$note','$driver', '$price');";
$json['success'] = 'ride has been requested';
echo json_encode($json);
mysqli_query($connect, $query) or die (mysqli_error($connect));
mysqli_close($connect);
}
else{
	$json['error'] = 'you must type all fields';
				echo json_encode($json);
}

}
?>