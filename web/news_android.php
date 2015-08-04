	 <?php
		include 'connect.php';
		$union_id = $_GET["id"];
		$sq = "SELECT  ID, URL, title, abstract, iconURL, TheDate from News where UnionID = '$union_id'";
		$result = mysqli_query($db, $sq);
		$contents = array();
		while($row = mysqli_fetch_array($result)){
			$item = array("id" => $row[0], "URL" => $row[1], "title" => $row[2], "abstract" => $row[3], "iconURL" => $row[4], "thedate" => $row[5]);
			array_push($contents, $item);
		}
		$ret_json = json_encode($contents);
		header("Content-type:text/json");
		echo $ret_json;
		mysqli_close($db);
	 ?>
