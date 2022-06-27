
var wfsGetFeatureUrl = 'http://localhost:8080/geoserver/project/ows?' +
    'service=WFS' +
    '&version=1.0.0' +
    '&request=GetFeature' +
    '&typeName=project:gangwon' +
    '&maxFeatures=50' +
    '&outputFormat=text/javascript'


$("#wfs-button").click(() => {

    var wkt = ''
    var format = new WKT();

    var feature = format.readFeature(wkt, {
        dataProjection: 'EPSG:5174',
        featureProjection: 'EPSG:3857'
    });

    $.ajax({
        url: wfsGetFeatureUrl,
        type: 'GET',
        dataType: 'jsonp',
        jsonpCallback: 'responseJSON'
    }).done(function (getFeatureResult) {
        if (getFeatureResult.features != undefined) {
            var vectorLayer = new ol.layer.Vector({
                source: new ol.source.Vector({
                    features: (new ol.format.GeoJSON()).readFeatures(getFeatureResult)
                })
            });
            map.addLayer(vectorLayer);
        }
    }).success(function (response) {
        console.log("success");
    }).fail(function (response) {
        console.log("fail");
    });
})


