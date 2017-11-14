<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createDriver();
	
}
function createDriver()
{
	global $connect;
	$id =$_POST["id"];
$query = " Select * FROM DRIVER where id=$id; ";

	
	$result = mysqli_query($connect, $query);
	$number_of_rows = mysqli_num_rows($result);
	
	$temp_array  = array();
	
	if($number_of_rows > 0) {
		while ($row = mysqli_fetch_assoc($result)) {
		$temp_array = $row['available'];
		}
	}

$available=$temp_array;
if($available=='yes'){
$query = "UPDATE driver SET available='no' WHERE id=$id";
$json['success'] = 'Set to unavailable';	
}else{
	$query = "UPDATE driver SET available='yes' WHERE id=$id";	
	$json['success'] = 'Set to available';	
}
	


echo json_encode($json);
mysqli_query($connect, $query) or die (mysqli_error($connect));
mysqli_close($connect);


}
?>