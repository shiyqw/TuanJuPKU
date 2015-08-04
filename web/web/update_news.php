	 <?php
		session_start();
		if(!isset($_SESSION['user_id'])){
			echo "Not logged in!";
			$home_url = "signin.html";
			header("Location: ".$home_url);
		}
		include '../connect.php';
		$id = $_POST["id"];
		$title = $_POST["title"];
		$url = $_POST["URL"];
		$date = date("Y-m-d");
		$sq = "update News set title = '$title', URL = '$url', TheDate = '$date' where ID = '$id'";
		$result = mysqli_query($db, $sq);
		if($result){
			$ret = array("status" => "success");
			$ret_json = json_encode($ret, JSON_UNESCAPED_SLASHES);
			header("Content-type:text/json");
			echo $ret_json;
		}
		else{
			$ret = array("status" => "failed");
			$ret_json = json_encode($ret, JSON_UNESCAPED_SLASHES);
			header("Content-type:text/json");
			echo $ret_json;
		}
		mysqli_close($db);
	 ?>
