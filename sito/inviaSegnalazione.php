<?php
session_start();
require_once 'configDB.php';
if ($_POST['geometriaPoint']==='NULL') {
    $_SESSION['erroreGeomPoint']='Seleziona la posizione sulla Mappa';
    header('Location: segnalazione.php');
    exit;
}


try {
    $dsn = "pgsql:host=$host;port=5432;dbname=$db;";
    // make a database connection
    $pdo = new PDO($dsn, $user, $password, [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION]);
    if ($pdo) {
        if($_POST['geometriaPoly']==='NULL'){
            $stmt = $pdo->prepare("INSERT INTO segnalazioni (email, descrizione, dim, data, lat, lon, geometry ) VALUES (:email, :descrizione, :dim, :data, :lat, :lon, :geometry)");
            $stmt->bindParam(':email', $_SESSION['email'], PDO::PARAM_STR);
            $stmt->bindParam(':descrizione', $_POST['descrizione'], PDO::PARAM_STR);
            $stmt->bindParam(':dim', $_POST['dimensioneBuca'], PDO::PARAM_STR);
            $stmt->bindParam(':geometry', $_POST['geometriaPoint'], PDO::PARAM_STR);
            $stmt->bindParam(':data', date("Y/m/d"), PDO::PARAM_STR);
            $stmt->bindParam(':lat', $_POST['lat'], PDO::PARAM_STR);
            $stmt->bindParam(':lon', $_POST['lon'], PDO::PARAM_STR);
            // $stmt->bindParam(':foto', $foto, PDO::PARAM_STR);
        }
        else{
            $stmt = $pdo->prepare("INSERT INTO segnalazioni (email, descrizione, dim, data, lat, lon, geometry ) VALUES (:email, :descrizione, :dim, :data, :lat, :lon, :geometry)");
            $stmt->bindParam(':email', $_SESSION['email'], PDO::PARAM_STR);
            $stmt->bindParam(':descrizione', $_POST['descrizione'], PDO::PARAM_STR);
            $stmt->bindParam(':dim', $_POST['dimensioneBuca'], PDO::PARAM_STR);
            $stmt->bindParam(':geometry', $_POST['geometriaPoly'], PDO::PARAM_STR);
            $stmt->bindParam(':data', date("Y/m/d"), PDO::PARAM_STR);
            $stmt->bindParam(':lat', $_POST['lat'], PDO::PARAM_STR);
            $stmt->bindParam(':lon', $_POST['lon'], PDO::PARAM_STR);
            // $stmt->bindParam(':foto', $foto, PDO::PARAM_STR);
        }

        $stmt->execute();
        //prova
        $stmt = $pdo->prepare("SELECT email, id FROM segnalazioni WHERE email=:email");
        $stmt->bindParam(':email', $_SESSION['email'], PDO::PARAM_STR);
        $stmt->execute(); 
        $stmt->bindColumn('id', $id_se);
        $user = $stmt->fetchall(PDO::FETCH_ASSOC);
        $_SESSION['RegistrazioneRiuscita']='Complimenti ti sei registrato adesso puoi accedere';
        $uploaddir = '/Applications/XAMPP/xamppfiles/htdocs/Progetto-Sit/sito/fileup/';
        $ext=  pathinfo($_FILES['fileCaricato']['name'], PATHINFO_EXTENSION);
        $uploadfile = $uploaddir . basename($id_se.'.'.$ext);


        if (move_uploaded_file($_FILES['fileCaricato']['tmp_name'], $uploadfile)) {
            echo $id_se;

            $stmt = $pdo->prepare("UPDATE segnalazioni SET foto=true where id=:id");
            $stmt->bindParam(':id', $id_se, PDO::PARAM_STR);
            $stmt->execute();
        } else {
            echo "Possible file upload attack!\n";
        }

        echo 'Here is some more debugging info:';
        print_r($_FILES);

        
    }
} catch (PDOException $e) {
    die($e->getMessage());
} finally {
    $pdo=null;
     header('Location: visualizzaSegnalazione.php');
}

?>