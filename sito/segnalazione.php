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
        <div class="container">
            <div class="container mt-auto">
                <div class="row row-cols-lg-2 row-cols-md-2 row-cols-sm-1 row-cols-1">
                    <div class="col mt-1">
                        <h3>utente</h3>
                        <!--  echo $_COOKIE['profile_viewer_uid'];  -->
                        <form method="POST" action="inviaSegnalazione.php" enctype="multipart/form-data">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="lat1" name="lat1" disabled>
                                <input type="text" class="form-control" id="lat" name="lat" hidden>
                                <label for="floatingInput">latitudine</label>

                            </div>
                            <div class="form-floating">
                                <input type="text" class="form-control" id="lon1" name="lon1" disabled>
                                <input type="text" class="form-control" id="lon" name="lon" hidden>
                                <label for="floatingInput">longitudine</label>
                            </div>
                            <div class="form-floating">
                                <select class="form-select" id="floatingSelect" aria-label="Floating label select example" name="dimensioneBuca">
                                    <option selected value="piccola">Piccola</option>
                                    <option value="media">Media</option>
                                    <option value="grande">Grande</option>
                                </select>
                                <label for="floatingSelect">Dimensione buca</label>
                            </div>
                            <div class="form-floating">
                                <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 100px" name="descrizione"></textarea>
                                <label for="floatingTextarea2">Descrizione</label>
                            </div>
                            <div class="mb-3">
                                <input class="form-control form-control-sm" id="formFileSm" type="file" name="fileCaricato">
                                <input type="hidden" id="geometriaPoly" name="geometriaPoly" value="NULL">
                                <input type="hidden" id="geometriaPoint" name="geometriaPoint" value="NULL">
                            </div>
                            <div>
                                <button class="w-100 btn btn-lg btn-primary" type="submit" name="inviaSegnalazione">Invia</button>
                            </div>
                        </form>
                    </div>
                    <div class="col mt-1">
                                 <span class="text-center" style="color: red;"> <?php echo $_SESSION['erroreGeomPoint']; unset($_SESSION['erroreGeomPoint']); ?></span>
                        <div id="map" class="map"></div>
                        <form class="form-inline" method="POST">
                            <div id="info" style="display: none;"></div>
                        <label for="track">
                            track position
                            <input id="track" type="checkbox" />
                        </label>
                            <label class="ms-3" for="type">Geometry type &nbsp;</label>
                            <select id="type">
                                <option >Scegli</option>
                                <option  value="Point">Scegli la posizione</option>
                                <option value="Polygon">Polygon</option>
                            </select>
                        </form>
                        
                        <p>
                            position accuracy : <code id="accuracy"></code>&nbsp;&nbsp;
                        </p>
                        <script src="mappaSegnalazione.js"></script>
                    </div>
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