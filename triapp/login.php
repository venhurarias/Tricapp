
<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
	include 'connection.php';
	showStudent();
}
function showStudent()
{
	global $connect;
	$username =$_POST["username"];
	$password= $_POST["password"];
	$query = " Select * FROM DRIVER where username = '{$username}' and password = '{$password}';";
	
	$result = mysqli_query($connect, $query);
	$number_of_rows = mysqli_num_rows($result);
	

	
	if($number_of_rows > 0) {
			$json['success'] = 'welcome';
		while ($row = mysqli_fetch_assoc($result)) {
			$res = $row;
		}
		echo json_encode($json+$res);
	}else{
		$json['error'] = 'account doesnt exist';
		echo json_encode($json);
	}
	
	header('Content-Type: application/json');

	
	
	mysqli_close($connect);
	
}
?>