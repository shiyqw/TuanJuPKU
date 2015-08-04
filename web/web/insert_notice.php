	 <?php
		session_start();
		if(!isset($_SESSION['user_id'])){
			echo "Not logged in!";
			$home_url = "signin.html";
			header("Location: ".$home_url);
		}
		if(!isset($_SESSION['union_id'])){
			echo "UnionID is invalid!";
			exit;
		}
		$union_id = $_SESSION['union_id'];
		include '../connect.php';
		$content = $_POST["content"];
		$date = date("Y-m-d");
		$sq = "insert into UnionInfo(UnionID, Content, TheDate) values ('$union_id', '$content', '$date')";
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
