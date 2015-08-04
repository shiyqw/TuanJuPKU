	 <?php
		include 'connect.php';
		$sq = "SELECT ID, Name, iconURL from UnionAccount";
		$result = mysqli_query($db, $sq);
		$contents = array();
		while($row = mysqli_fetch_array($result)){
			$item = array("id" => $row[0], "Name" => $row[1], "iconURL" => $row[2]);
			array_push($contents, $item);
		}
		$ret_json = json_encode($contents);
		header("Content-type:text/json");
		echo $ret_json;
		mysqli_close($db);
	 ?>
