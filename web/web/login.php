<?php
session_start();
$error_msg = "";
//if(!isset($_SESSION['user_id'])){
	
//    if(isset($_POST['submit'])){
        include '../connect.php';
        $username = mysqli_real_escape_string($db,trim($_POST['username']));
        $password = mysqli_real_escape_string($db,trim($_POST['password']));

        if(!empty($username)&&!empty($password)){
            $query = "SELECT ID, Name FROM UnionAccount WHERE login_name = '$username' AND "."ThePassword = SHA('$password')";
            $result = mysqli_query($db,$query);
            if(mysqli_num_rows($result)==1){
                $row = mysqli_fetch_array($result);
                $_SESSION['user_id']=$row[0];
                $_SESSION['union_id']=$row[0];
				$_SESSION['name'] = $row[1];
                $home_url = 'dash.php';
                header('Location: '.$home_url);
            }else{
                $error_msg = 'Sorry, you must enter a valid username and password to log in.';
				$home_url = "signin.html";
				header('Location: '.$home_url);
            }
        }else{
            $error_msg = 'Sorry, you must enter a valid username and password to log in.';
			$home_url = "signin.html";
			header('Location: '.$home_url);
        }
//    }
//}else{
//    $home_url = 'dash.php';
//    header('Location: '.$home_url);
//}
?>
