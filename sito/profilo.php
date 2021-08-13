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
    <title>Sticky Footer Navbar Template · Bootstrap v5.0</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sticky-footer-navbar/">



    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sticky-footer-navbar/">


    <!-- Custom styles for this template -->
    <link href="css/sticky-footer-navbar.css" rel="stylesheet">
  </head>
  <body class="d-flex flex-column h-100">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<header class="p-2 bg-dark text-white">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
          <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"/></svg>
        </a>
        <ul class="nav col-12 col-lg-auto col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li><a href="home.php" class="nav-link px-1 text-white">Home</a></li>
          <li><a href="segnalazione.php" class="nav-link px-1 text-white">Segnalazione</a></li>
          <li><a href="visualizzaSegnalazione.php" class="nav-link px-1 text-white">Visualizza Segnalazione</a></li>
        </ul>

        <div class="text-end">
          <ul class="nav col-12 col-lg-auto me-lg-auto mb-1 justify-content-center mb-md-0">
            <li> <a href="profilo.php" class="nav-link px-2 text-white"><?php echo $_SESSION['nome'].' '.$_SESSION['cognome'];?></a></li>
           <li> <a class="btn btn-outline-light me-2" href="logout.php" role="button">Esci</a></li>
          </ul>
        </div>
      </div>
    </div>
</header>

<!-- Begin page content -->
<main class="flex-shrink-0 ">
  <div class="container ">
    <h2 class="mt-auto text-center">Ciao ecco i tuoi dati <?php echo  $_SESSION['nome'].' '.$_SESSION['cognome'];?> </h2>
    <div class="card card-dati-utente mt-5" style="width: 20rem;">
      <div class="card-header">
          Dati utente
      </div>
      <ul class="list-group list-group-flush">

        <li class="list-group-item"> <label class="text-muted">Nome: </label> <?php  echo $_SESSION['nome'];?></li>
        <li class="list-group-item"><label class="text-muted">Cognome: </label> <?php  echo $_SESSION['cognome'];?></li>
        <li class="list-group-item"><label class="text-muted">Email: </label><?php  echo ' '.$_SESSION['email'];?></li>
        <li class="list-group-item"><label class="text-muted">Codice Fiscale: </label><?php  echo $_SESSION['codicefiscale'];?></li>
        <li class="list-group-item"><label class="text-muted">Numero di telefono: </label><?php  echo $_SESSION['telefono'];?></li>
      </ul>
</div>
  </div>
</main>
<footer class="footer mt-auto py-3 bg-dark">
  <div class="container text-center">
    <span class="text-white f-ce" >Place sticky footer content here.</span>
  </div>
   <h1 class="visually-hidden">Sidebars examples</h1>


</footer>

  </body>
</html>
