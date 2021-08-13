<?php
session_start();
require_once 'configDB.php';

try {
	$dsn = "pgsql:host=$host;port=5432;dbname=$db;";
	// make a database connection
	$pdo = new PDO($dsn, $user, $password, [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION]);
	if ($pdo) {
		echo "Connected to the $db database successfully!";

		/*$sql = "SELECT * FROM utenti WHERE email = ? AND password = ?";

		$sth = $pdo->prepare($sql);
		$sth->execute(array($_POST['email'], $_POST['password']));
		$result = $sth->fetchAll();

			print_r($result);
		*/
		$stmt = $pdo->prepare("SELECT password, email, cognome, nome, telefono, codicefiscale FROM utenti WHERE email=:email");
		$stmt->bindParam(':email', $_POST['email'], PDO::PARAM_STR);
		$stmt->execute(); 
		$stmt->bindColumn('password', $password);
		$stmt->bindColumn('email', $email);
		$stmt->bindColumn('nome', $nome);
		$stmt->bindColumn('cognome', $cognome);
		$stmt->bindColumn('telefono', $telefono);
		$stmt->bindColumn('codicefiscale', $codicefiscale);
		
		$user = $stmt->fetch(PDO::FETCH_ASSOC);
		// echo $_POST['password'];
		// echo $password;
		// if ($_POST['password'] === $password) {
		// 	// code...
		// 	echo "ok";
		// }
		// else{
		// 	echo 'no';
		// }
		if(password_verify($_POST['password'], $password)){
			session_regenerate_id();
			$_SESSION['loggedin'] = TRUE;
			$_SESSION['nome'] = $nome;
			$_SESSION['cognome']=$cognome;
			$_SESSION['email']=$email;
			$_SESSION['codicefiscale']=$codicefiscale;
			$_SESSION['telefono']=$telefono;
			$_SESSION['id'] = session_id();
			//$_SESSION['id'] = $id;
			header('Location: home.php');
		}
		else{
			$_SESSION['erroreEmail']='email o password sbagliate';
			header('Location: login.php');
		}

		}
} catch (PDOException $e) {
	die($e->getMessage());
} finally {
	$pdo=null;
}
