<?php
#Php Proxy Script
#file: proxy.php 
$target = $_GET['url']; 
$ar = $_GET;
unset( $ar['url'] );
$url = $target."?".http_build_query( $ar ); 
echo file_get_contents($url);
?>