	 <?php
		include 'connect.php';
		$union_id = $_GET["id"];
		$sq = "SELECT  UnionInfo.ID, Name, Content from UnionInfo,UnionAccount where UnionID = '$union_id'and UnionAccount.ID = UnionID";
		$result = mysqli_query($db, $sq);
		$contents = array();
		while($row = mysqli_fetch_array($result)){
			$item = array("id" => $row[0], "name" => $row[1], "content" => $row[2]);
			array_push($contents, $item);
		}
		$ret_json = json_encode($contents);
		header("Content-type:text/json");
		echo $ret_json;
		mysqli_close($db);
	 ?>
