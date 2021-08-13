var Map = ol.Map;
var View = ol.View;
var WKT = ol.format.WKT;
var OSM = ol.source.OSM;
var VectorSource = ol.source.Vector;
var TileLayer = ol.layer.Tile; 
var VectorLayer = ol.layer.Vector;

const raster = new TileLayer({
  source: new OSM(),
});
var i='\'';
var wkt =document.getElementById('geomwkt');
 console.log(wkt.value);
// var wkt='POLYGON((76869.3752264143 -23865.670589195797,317205.3070835129 -663372.484751422,-446764.2785340359 -907667.0186436959,76869.3752264143 -23865.670589195797))';
const format = new WKT();

const feature = format.readFeature(wkt.value);
// const feature = format.readFeature(wkt);

const vector = new VectorLayer({
  source: new VectorSource({
     features: [feature],
  }),
});

const map = new Map({
  layers: [raster, vector],
  target: 'map',
  view: new View({
    center: [0, 0],
    zoom: 2,
  }),
});
