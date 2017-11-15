<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	include 'connection.php';
	showRide();
}
function showRide()
{
	global $connect;
	//$username =$_POST["username"];
	$query = " Select * FROM DRIVER where available='yes' ORDER BY id ASC; ";
	
	$result = mysqli_query($connect, $query);
	$number_of_rows = mysqli_num_rows($result);
	
	$temp_array  = array();
	
	if($number_of_rows > 0) {
		while ($row = mysqli_fetch_assoc($result)) {
			$temp_array[] = $row;
		}
	}
	
	header('Content-Type: application/json');
	echo json_encode($temp_array);
	//mysqli_close($connect);
	
}
?>

