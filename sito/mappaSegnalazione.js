var ImageWMS = ol.source.ImageWMS;
var Map = ol.Map;
var OSM = ol.source.OSM;
var View = ol.View;
var ImageLayer = ol.layer.Image;
var TileLayer = ol.layer.Tile;
var Feature = ol.Feature;
var Geolocation = ol.Geolocation;
var Point = ol.geom.Point;
// var {Circle as CircleStyle, Fill, Stroke, Style} = ol.style;
var CircleStyle = ol.style.Circle;
var Fill = ol.style.Fill;
var Stroke = ol.style.Stroke;
var Style = ol.style.Style;
var proj = ol.proj;
var toStringXY = ol.coordinate.toStringXY;
var toStringHDMS = ol.coordinate.toStringHDMS;
var toLonLat = ol.proj.toLonLat;
var fromLonLat = ol.proj.fromLonLat;
// var {OSM, Vector as VectorSource} = ol.source;
var VectorSource = ol.source.Vector;
var VectorLayer = ol.layer.Vector;

var Draw = ol.interaction.Draw;
var Polygon = ol.geom.Polygon;
var format = new ol.format.WKT();

var view = new View({
    center: [0, 0],
    zoom: 2,
});

const mappa = new TileLayer({
    source: new OSM(),
});

const source = new VectorSource({
    // features: [accuracyFeature, positionFeature]
});
const vector = new VectorLayer({
    source: source,
});

const mappaWMS = new ImageLayer({
    source: new ImageWMS({
        ratio: 1,
        url: 'http://localhost:8080/geoserver/SegnalazioniUtente/wms',
        params: {
            'VERSION': '1.1.1',
            "STYLES": '',
            "LAYERS": 'SegnalazioniUtente:segnalazioni',
            "exceptions": 'application/vnd.ogc.se_inimage',
        }
    })
})


var map = new Map({
    layers: [mappa, vector],
    target: 'map',
    view: view,
});

var geolocation = new Geolocation({
    // enableHighAccuracy must be set to true to have the heading value.
    trackingOptions: {
        enableHighAccuracy: true,
    },
    projection: view.getProjection(),
});

function el(id) {
    return document.getElementById(id);
}

el('track').addEventListener('change', function() {
    geolocation.setTracking(this.checked);
});

// update the HTML page when the position changes.
geolocation.on('change', function() {
    el('accuracy').innerText = geolocation.getAccuracy() + ' [m]';

    var coo = toStringXY(toLonLat(geolocation.getPosition()), 10).split(',');

    document.getElementById('lat').value = coo[1];
    document.getElementById('lon').value = coo[0];
    document.getElementById('lat1').value = coo[1];
    document.getElementById('lon1').value = coo[0];
    //document.cookie="profile_viewer_uid="+geolocation.getPosition();
    // el('altitudeAccuracy').innerText = geolocation.getAltitudeAccuracy() + ' [m]';
    // el('heading').innerText = geolocation.getHeading() + ' [rad]';
    // el('speed').innerText = geolocation.getSpeed() + ' [m/s]';

    map.setView(new ol.View({
        //center: [1795941.70, 4709367.44],
        center: fromLonLat(toLonLat(geolocation.getPosition())),
        zoom: 17,
    }));

});

// handle geolocation error.
geolocation.on('error', function(error) {
    var info = document.getElementById('info');
    info.innerHTML = error.message;
    info.style.display = '';
});

var accuracyFeature = new Feature();
geolocation.on('change:accuracyGeometry', function() {
    accuracyFeature.setGeometry(geolocation.getAccuracyGeometry());
});

var positionFeature = new Feature();
positionFeature.setStyle(
    new Style({
        image: new CircleStyle({
            radius: 6,
            fill: new Fill({
                color: '#3399CC',
            }),
            stroke: new Stroke({
                color: '#fff',
                width: 2,
            }),
        }),
    })
);

geolocation.on('change:position', function() {
    var coordinates = geolocation.getPosition();
    positionFeature.setGeometry(coordinates ? new Point(coordinates) : null);
    document.getElementById("geometriaPoint").value = format.writeFeature(positionFeature);

});
var ff = new Feature();

new VectorLayer({
    map: map,
    source: new VectorSource({
        features: [accuracyFeature, positionFeature, ff],
    }),
});

// map.on('singleclick', function(evt) {
//     var coordinate = evt.coordinate;
//     var clickC = toStringXY(toLonLat(coordinate), 10).split(',');
//     document.getElementById('lat').value = clickC[1];
//     document.getElementById('lon').value = clickC[0];
//     // var hdms = toStringHDMS(toLonLat(coordinate));
//     // document.getElementById('coordinate').value=hsms;
//     var punto = new Point(coordinate);
//     positionFeature.setGeometry(punto);
// });

const typeSelect = document.getElementById('type');
typeSelect.onchange = function() {
    map.removeInteraction(draw);
    addInteraction();
};


// f.setStyle(
//     new Style({
//         stroke: new Stroke({
//             color: 'blue',
//             width: 3,
//         }),
//         fill: new Fill({
//             color: 'rgba(0, 0, 255, 0.1)',
//         }),
//     }),
// );

let draw; // global so we can remove it later
function addInteraction() {
    const value = typeSelect.value;
    if (value === 'Polygon') {
        draw = new Draw({
            type: typeSelect.value,
        });

        draw.on('drawend', function(e) {
            ff.setGeometry(new Polygon(e.feature.getGeometry().getCoordinates()));
            // // var f1 = new PoiPolygonnt(e.feature.getGeometry().getCoordinates()[0]);
            // console.log('ff ' + ff.getGeometry());
            // f.setGeometry(f1);
            console.log(e.feature.getGeometry().getCoordinates()[0]);
            
            console.log(format.writeFeature(e.feature));

            document.getElementById("geometriaPoly").value = format.writeFeature(e.feature);
            console.log(map.getProjection);
        });
        map.addInteraction(draw);


    } else if (value === 'Point') {
        draw = new Draw({
            type: typeSelect.value,
        });
        draw.on('drawend', function(e) {
            var punto = new Point(e.feature.getGeometry().getCoordinates());
            positionFeature.setGeometry(punto);
            // console.log(a.length);

            // var feature = new Feature({
            //         geometry: new Polygon(e.feature.getGeometry().getCoordinates()[0]),

            // })
            var cooClick = toStringXY(toLonLat(e.feature.getGeometry().getCoordinates()), 10).split(',');
            document.getElementById('lat').value = cooClick[1];
            document.getElementById('lon').value = cooClick[0];
            document.getElementById('lat1').value = cooClick[1];
            document.getElementById('lon1').value = cooClick[0];

            var format = new ol.format.WKT();
            console.log(format.writeFeature(e.feature));
            document.getElementById("geometriaPoint").value = format.writeFeature(e.feature);
            // document.getElementById("createdAt").value = format.writeFeature(e.feature);


        });
        map.addInteraction(draw);

    }
    // var punto = new Point(e.feature.getGeometry().getCoordinates());
    
    // document.getElementById("geometriaPoly").value = format.writeFeature(positionFeature);
}