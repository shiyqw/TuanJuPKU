<?php
	$db = mysqli_connect("localhost", "dingboyan", "tuanju02");
	if(!$db){
		echo "Can't connect to database!\n";
		exit;
	}
	$er = mysqli_select_db($db, "dingboyan");
	if(!$er){
		echo "Can't use database dingboyan\n";
		exit;
	}
	mysqli_query($db, 'set names utf8');
?>