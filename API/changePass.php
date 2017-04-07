<?php
    require("password.php");

    $connect = mysqli_connect("", "", "", "", "");

//check connection
    if (mysqli_connect_errno())  {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
      }

    $username = $_POST["username"];
    $password = $_POST["password"]; //actual user's password
    $oldPassword = $_POST["oldPassword"]; //user's input for old password
    $newPassword = $_POST["newPassword"];

    $statement = mysqli_prepare($connect, "SELECT * FROM user_test WHERE username = ?");
    mysqli_stmt_bind_param($statement, "s", $username);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $colUserID, $colUsername, $colPassword, $colemail);

function updatePass() {
   global $connect, $username, $newPassword;
   $passwordHash = password_hash($newPassword, PASSWORD_DEFAULT);
   $statement = mysqli_prepare($connect, "UPDATE user_test SET password='$passwordHash' WHERE username='$username'");
   mysqli_stmt_execute($statement);
   mysqli_stmt_close($statement);
}

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        if (password_verify($oldPassword, $password)) { //check the user's inputted password to the original hashed value
	          updatePass();
            $response["success"] = true;
        }
    }

    echo json_encode($response);

?>
