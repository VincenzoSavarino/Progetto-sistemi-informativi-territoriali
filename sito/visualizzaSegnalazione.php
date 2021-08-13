<?php
// We need to use sessions, so you should always start sessions using the below code.
session_start();
// If the user is not logged in redirect to the login page...
if (!isset($_SESSION['loggedin'])) {
    header('Location: index.html');
    exit;
}

?>
<!doctype html>
<html lang="en" class="h-100">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.83.1">
    <title>Segnalazione</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sticky-footer-navbar/">
    <!-- Bootstrap core CSS -->
    <!-- Custom styles for this template -->
    <link href="css/sticky-footer-navbar.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.5.0/css/ol.css" type="text/css">
    <!-- <script src="https://unpkg.com/elm-pep"></script> -->
    <style>
    .map {
        height: 400px;
        width: 100%;
    }
    </style>
</head>

<body class="d-flex flex-column h-100">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.5.0/build/ol.js"></script>
    <header class="p-2 bg-dark text-white">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                    <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                        <use xlink:href="#bootstrap" /></svg>
                </a>
                <ul class="nav col-12 col-lg-auto col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="home.php" class="nav-link px-1 text-white">Home</a></li>
                    <li><a href="segnalazione.php" class="nav-link px-1 text-white">Segnalazione</a></li>
                    <li><a href="visualizzaSegnalazione.php" class="nav-link px-1 text-white">Visualizza Segnalazione</a></li>
                </ul>
                <div class="text-end">
                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-1 justify-content-center mb-md-0">
                        <li> <a href="profilo.php" class="nav-link px-2 text-white">
                                <?php echo $_SESSION['nome'].' '.$_SESSION['cognome'];?></a></li>
                        <li> <a class="btn btn-outline-light me-2" href="logout.php" role="button">Esci</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </header>
    <!-- Begin page content -->
    <main class="flex-shrink-0">
        <div class="container ">

            <div class="table table-responsive ">
                <?php 
                    require_once 'configDB.php';
                    try {
                            $dsn = "pgsql:host=$host;port=5432;dbname=$db;";
                            // make a database connection
                            $pdo = new PDO($dsn, $user, $password, [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION]);
                            if ($pdo) {
                                $stmt = $pdo->prepare("SELECT * FROM segnalazioni WHERE email=:email");
                                $stmt->bindParam(':email', $_SESSION['email'], PDO::PARAM_STR);
                                $stmt->execute();
                                $stmt->bindColumn('id', $id_segnalazione);
                                $stmt->bindColumn('lat', $latitudine);
                                $stmt->bindColumn('lon', $longitudine);
                                $stmt->bindColumn('descrizione', $descrizione);
                                $stmt->bindColumn('dim', $dimensione);
                                $stmt->bindColumn('data', $data);
                                // $user = $stmt->fetch(PDO::FETCH_ASSOC);
            
                                
                            }
                        } catch (PDOException $e) {
                            die($e->getMessage());
                        } finally {
                            $pdo=null;
                        }

                ?>
                <table class="table">
                    <thead class="thead-dark">
                       
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">latitudine</th>
                            <th scope="col">longitudine</th>
                            <th scope="col">dimensione</th>
                            <th scope="col">descrizione</th>
                            <th scope="col">data</th>
                        </tr>
                    </thead>
                    <tbody>
                         <?php while ($user = $stmt->fetch(PDO::FETCH_ASSOC)){?>
                        <tr>
                            <th scope="row"><?php  echo $id_segnalazione; ?></th>
                            <td><?php  echo $latitudine; ?></td>
                            <td><?php  echo $longitudine; ?></td>
                            <td><?php  echo $dimensione; ?></td>
                            <td><?php  echo $descrizione; ?></td>
                            <td><?php  echo $data; ?></td>
                        </tr>
                    <?php } ?>
                    </tbody>

                </table>
            </div>
        </div>
    </main>
    <footer class="footer mt-auto py-3 bg-dark">
        <div class="container text-center">
            <span class="text-white f-ce">Place sticky footer content here.</span>
        </div>
        <h1 class="visually-hidden">Sidebars examples</h1>
    </footer>
</body>

</html>