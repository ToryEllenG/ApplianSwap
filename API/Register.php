<?php
    require("password.php");
    $connect = mysqli_connect("", "", "", "", "");

if (mysqli_connect_errno())  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }


    $email = $_POST['email'];
    $username = $_POST['username'];
    $password = $_POST['password'];


  function registerUser() {
   global $connect, $first_name, $last_name, $email, $username, $password, $phone_number;
   $passwordHash = password_hash($password, PASSWORD_DEFAULT);
   $statement = mysqli_prepare($connect, "INSERT INTO user_test (username, password, email) VALUES (?, ?, ?)");
   mysqli_stmt_bind_param($statement, "sss", $username, $passwordHash, $email);
   mysqli_stmt_execute($statement);
   mysqli_stmt_close($statement);
}

function usernameAvailable() { //checks if username or email already exists
   global $connect, $username, $email;
   $statement = mysqli_prepare($connect, "SELECT * FROM user_test WHERE username = ? OR email = ?");
   mysqli_stmt_bind_param($statement, "ss", $username, $email);
   mysqli_stmt_execute($statement);
   mysqli_stmt_store_result($statement);
   $count = mysqli_stmt_num_rows($statement);
   mysqli_stmt_close($statement);
   if ($count < 1){
       return true;
   }else {
       return false;
   }
}

$response = array();
$response["success"] = false;
if (usernameAvailable()){
   registerUser();
   $response["success"] = true;
}

echo json_encode($response);

?>
