<?php
session_start();
if (isset($_SESSION['loggedin'])) {
    header('Location: home.php');
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

    <link href="css/sticky-footer-navbar.css" rel="stylesheet">
    

    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sticky-footer-navbar/">
    
    <!-- Custom styles for this template -->

  </head>
  <body class="d-flex flex-column h-100">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<header class="p-2 bg-dark text-white">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
          <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"/></svg>
        </a>
        <ul class="nav col-12 col-lg-auto col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li><a href="home.php" class="nav-link px-1 text-white">Home</a></li>
        
        </ul>

        <div class="text-end">
          <ul class="nav col-12 col-lg-auto me-lg-auto mb-1 justify-content-center mb-md-0">
           <li> <a class="btn btn-outline-light me-2" href="login.php" role="button">Accedi</a></li>
          </ul>
        </div>
      </div>
    </div>
</header>

<!-- Begin page content -->
<main class="flex-shrink-0">
  <div class="form-signin ">
    <form method="POST" action="controlloReg.php">

      <div class="form-floating">
        <input type="text" class="form-control" id="floatingInput" placeholder="Inserisci il nome" name="nome" required>
        <label for="floatingInput"  >Nome</label>
      </div>
      <div class="form-floating ">
        <input type="text" class="form-control" id="floatingInput" placeholder="Inserisci il cognome" name="cognome" required>
        <label for="floatingInput">Cognome</label>
      </div>
    <div class="form-floating ">
      <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" name="email" required>
      <label for="floatingInput">Email</label>
    </div>
    <div class="form-floating ">
      <input type="text" class="form-control" id="floatingInput" placeholder="Inserisci il nome" name="codicefiscale" pattern="[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]" required>
      <label for="floatingInput">Codice Fiscale</label>
    </div>
     <div class="form-floating">
      <input type="tel" class="form-control" id="floatingPassword" placeholder="10" name="telefono" pattern="[0-9]{10}" required>
      <label for="floatingPassword">Numero di telefono</label>
    </div>
    <div class="form-floating ">
      <input type="password" class="form-control" id="floatingInput" placeholder="Inserisci il nome" name="password" required>
      <label for="floatingInput">Password</label>
    </div>
    <div class="form-floating ">
      <input type="password" class="form-control" id="floatingInput" placeholder="Inserisci il nome" name="ripetiPassword" required>
      <label for="floatingInput">Ripeti password</label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" type="submit" name="SignIn">Registrati</button>
    <span><?php  echo $_SESSION['erroreEmail']; session_destroy();?></span>
    <span><?php  echo $_SESSION['errorePassword']; session_destroy();?></span>
    <span><?php  echo $_SESSION['erroreCodicefiscale']; session_destroy();?></span>
    </form>
  </div>
</main>


<footer class="footer mt-auto py-3 bg-dark">
  <div class="container text-center">
    <span class="text-white">Place sticky footer content here.</span>
  </div>
</footer>
      
  </body>
</html>
