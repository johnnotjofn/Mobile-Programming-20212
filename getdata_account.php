<?php
$connect = mysqli_connect("localhost","root","","chan_nuoi_thong_minh");
// var_dump($connect->connect_errno);
if ($connect->connect_errno) {
	echo "Fail to connect to MySQL: " . mysqli_connect_error();
	exit();
}

mysqli_query($connect, "SET NAMES 'utf8'");

$query = "SELECT * FROM account";
$data = mysqli_query($connect, $query);
// var_dump($data);
class User{
	public $Id;
	public $Username;
	public $Password;

	function __construct($id, $username, $password){
		$this->Id = $id;
		$this->Username = $username;
		$this->Password = $password;
	}
}

// $row = mysqli_fetch_assoc($data);
// var_dump($row);
$mangUser = array();

while($row = mysqli_fetch_assoc($data)){
	$newUser = new User($row['id'], $row['username'], $row['password']);
	array_push($mangUser, $newUser);	
}
// var_dump($mangUser);
echo json_encode($mangUser);
?>


