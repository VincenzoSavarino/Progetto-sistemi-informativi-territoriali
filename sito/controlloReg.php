<?php
session_start();
require_once 'configDB.php';
// Now we check if the data was submitted, isset() function will check if the data exists.
// if (!isset($_POST['username'], $_POST['password'], $_POST['email'])) {
// 	// Could not get the data that should have been sent.
// 	exit('Please complete the registration form!');
// }
// // Make sure the submitted registration values are not empty.
// if (empty($_POST['username']) || empty($_POST['password']) || empty($_POST['email'])) {
// 	// One or more values are empty.
// 	exit('Please complete the registration form');
// }


try {
	$dsn = "pgsql:host=$host;port=5432;dbname=$db;";
	// make a database connection
	$pdo = new PDO($dsn, $user, $password, [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION]);
	if ($pdo) {
		echo "Connected to the $db database successfully!";
		$stmt = $pdo->prepare("SELECT email, codicefiscale FROM utenti WHERE email=:email OR  codicefiscale=:codicefiscaleLetto");
		$stmt->bindParam(':email', $_POST['email'], PDO::PARAM_STR);
		$stmt->bindParam(':codicefiscaleLetto', $_POST['codicefiscale'], PDO::PARAM_STR);

		$stmt->execute(); 
		//non serve non voglio leg i res del DB
		// $stmt->bindColumn('email', $email);// restituita
		$stmt->bindColumn('codicefiscale', $codicefiscale);
		$user = $stmt->fetch(PDO::FETCH_ASSOC);
		//echo $email;

		
		if ($stmt->rowCount()>0) {
			if ($codicefiscale === $_POST['codicefiscale']) {
				$_SESSION['erroreCodicefiscale']='codice Fiscale presente';
				
			}
			else{
				$_SESSION['erroreEmail']='email exists';

			}	
			header('Location: register.php');
		}
		else{			
			if ($_POST['password']===$_POST['ripetiPassword']) {
				$stmt = $pdo->prepare("INSERT INTO utenti (nome, cognome, email, password, codicefiscale, telefono ) VALUES (:nome, :cognome, :email, :passwordC, :codicefiscale, :telefono)");
				$stmt->bindParam(':nome', $_POST['nome'], PDO::PARAM_STR);
				$stmt->bindParam(':cognome', $_POST['cognome'], PDO::PARAM_STR);
				$stmt->bindParam(':email', $_POST['email'], PDO::PARAM_STR);
				$password = password_hash($_POST['password'], PASSWORD_DEFAULT);
				$stmt->bindParam(':passwordC', $password, PDO::PARAM_STR);
				$stmt->bindParam(':codicefiscale', $_POST['codicefiscale'], PDO::PARAM_STR);
				$stmt->bindParam(':telefono', $_POST['telefono'], PDO::PARAM_STR);
				$stmt->execute();
				$_SESSION['RegistrazioneRiuscita']='Complimenti ti sei registrato adesso puoi accedere';
				header('Location: login.php');
			}
			else{
				$_SESSION['errorePassword']='Ripeti pass non =';
				header('Location: register.php');
			}

		}
		
	}
} catch (PDOException $e) {
	die($e->getMessage());
} finally {
	$pdo=null;
}

?>