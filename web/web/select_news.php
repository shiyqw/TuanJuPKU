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
		$union_id = $_SESSION["union_id"];
		include '../connect.php';
		$sq = "SELECT  ID, URL, title, abstract, iconURL, TheDate from News where UnionID = '$union_id'";
		$result = mysqli_query($db, $sq);
		$num_of_rows = mysqli_num_rows($result);
		$contents = array();
		while($row = mysqli_fetch_array($result)){
			$item = array("id" => $row[0], "URL" => $row[1], "title" => $row[2], "abstract" => $row[3], "iconURL" => $row[4], "thedate" => $row[5]);
			array_push($contents, $item);
		}
		$union_json = array("count" => $num_of_rows, "contents" => $contents);
		$ret_json = json_encode($union_json, JSON_UNESCAPED_SLASHES);
		header("Content-type:text/json");
		echo $ret_json;
		mysqli_close($db);
	 ?>
