<?php
    $connect = mysqli_connect("", "", "", "", "");

if (mysqli_connect_errno())  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }


    $dishValue = $_POST['dishValue'];
    $dryerValue = $_POST['dryerValue'];
    $washerValue = $_POST['washerValue'];
    $fridgeValue = $_POST['fridgeValue'];
    $state = $_POST['state'];

   $statement = mysqli_prepare($connect, "INSERT INTO comparisons (dishValue, dryerValue, washerValue, fridgeValue, state) VALUES (?, ?, ?, ?, ?)");
mysqli_stmt_bind_param($statement, "sssss", $dishValue, $dryerValue, $washerValue, $fridgeValue, $state);
   mysqli_stmt_execute($statement);
   mysqli_stmt_close($statement);


$response = array();
$response["success"] = true;


echo json_encode($response);

?>
