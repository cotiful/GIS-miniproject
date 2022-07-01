var view = new ol.View({
    center: [14318984.363280362, 4514923.621443103],
    zoom: 9.5
});


var layers = [
    new ol.layer.Tile({
        source: new ol.source.OSM()
    })
];


var myUrl = 'http://127.0.0.1:8080/geoserver/wms'

var map = new ol.Map({
    target: "map",
    view: view,
    layers: layers
});




// 도로 데이터
var roadLayer = new ol.layer.Tile({
    source: new ol.source.TileWMS({
        url: myUrl,
        params: {
            VERSION: '1.3.0',
            LAYERS: 'project:road',
            WIDTH: 256,
            HEIGHT: 256,
            TILED: true,
        },
        serverType: 'geoserver'
    })
});

// 전체 병원 데이터 
var hospitalLayer = new ol.layer.Tile({
    source: new ol.source.TileWMS({
        url: myUrl,
        params: {
            VERSION: '1.3.0',
            LAYERS: 'project:allhospital',
            WIDTH: 256,
            HEIGHT: 256,
            TILED: true,
        },
        serverType: 'geoserver'
    })
});


// 종합 병원 데이터 (완료)
var bigHospitalLayer = new ol.layer.Tile({
    source: new ol.source.TileWMS({
        url: myUrl,
        params: {
            VERSION: '1.3.0',
            LAYERS: 'project:bighospital',
            WIDTH: 256,
            HEIGHT: 256,
            CRS: 'EPSG:5174',
            TILED: true,
        },
        serverType: 'geoserver'
    })
});

// 산불 히트맵(완료)
var fireHeatMapLayer = new ol.layer.Image({
    source: new ol.source.ImageWMS({
        url: myUrl,
        params: {
            'LAYERS': 'project:forestfireall5174',
            'CRS': 'EPSG:3857'
        },
        ratio: 1,
        serverType: 'geoserver'
    })
});

// 산불 포인트 데이터 (완료)
var wmsSource = new ol.source.TileWMS({
    url: myUrl,
    // projection: osmSource.getProjection(),
    params: {
        'LAYERS': 'project:forestfireall',
        'tiled': true
    },
    serverType: 'geoserver'
});

var forestFireLayer = new ol.layer.Tile({
    source: wmsSource
});

var view2 = new ol.View({
    center: [0, 0],
    zoom: 1,
});

// map.on('singleclick', function (evt) {
//     // map.setTarget(map.layer.forestFireLayer);

//     // coorinate = ol.proj.transform(evt.coordinate, 'EPSG:5174', 'EPSG:3857');
//     console.log(evt.coordinate)
//     document.getElementById('info').innerHTML = '';

//     const viewResolution = /** @type {number} */ (view2.getResolution());

//     const surl = wmsSource.getFeatureInfoUrl(
//         evt.coordinate,
//         viewResolution,
//         'EPSG:3857',
//         { 'INFO_FORMAT': 'text/html' }
//     );
//     console.log(surl);

//     if (surl) {
//         document.getElementById('info').innerHTML =
//             '<iframe width="100%" height="100px" seamless="" src="' + surl + '"></iframe>';
//     }
// });


///////////////////////////////////////////////

// 등산로 화재 데이터 (완료)
var forestFireFromPeopleLayer = new ol.layer.Tile({
    source: new ol.source.TileWMS({
        url: myUrl,
        params: {
            VERSION: '1.3.0',
            LAYERS: 'project:forestfirepeople',
            WIDTH: 256,
            HEIGHT: 256,
            CRS: 'EPSG:5174',
            TILED: true,
        },
        serverType: 'geoserver'
    })
});


// 소방서 포인트(완료)
var firestationLayer = new ol.layer.Tile({
    source: new ol.source.TileWMS({
        url: myUrl,
        params: {
            VERSION: '1.3.0',
            LAYERS: 'project:newfirestation',
            WIDTH: 256,
            HEIGHT: 256,
            CRS: 'EPSG:3857',
            TILED: true,
        },
        serverType: 'geoserver'
    })
});

// 저수지 레이어(완료)
var resvoirLayer = new ol.layer.Image({
    source: new ol.source.ImageWMS({
        url: myUrl,
        params: { 'LAYERS': 'project:resvoir' },
        ratio: 1,
        serverType: 'geoserver'
    })
});

// 지목 대 레이어(완료)
var daeLayer = new ol.layer.Tile({
    source: new ol.source.TileWMS({
        url: myUrl,
        params: {
            LAYERS: 'project:dae',
            CRS: 'EPSG:3857'
        },
        ratio: 1,
        serverType: 'geoserver'
    })
});


// 등산로 화재 데이터(완료)

var forestPathLayer = new ol.layer.Tile({
    source: new ol.source.TileWMS({
        url: myUrl,
        params: {
            LAYERS: 'project:forestpath',
            CRS: 'EPSG:3857',
        },
        ratio: 1,
        serverType: 'geoserver'
    })
});

// 논, 밭 화재 데이터 

// var fireNonLayer = new ol.layer.Tile({
//     source: new ol.source.TileWMS({
//         url: myUrl,
//         params: {
//             LAYERS: 'project:forestpath',
//             CRS: 'EPSG:3857',
//         },
//         ratio: 1,
//         serverType: 'geoserver'
//     })
// });

let check = {

    bigFire: {
        fire: true,
        fireHeat: true,
        firePeople: true,
    },
    dae: true,
    non: true,
    road: true,
    path: true,
    resvoir: true,
    hosp: {
        allhosp: true,
        bighosp: true
    },
    firest: true,
}

function clickCheck(isClick, layerName) {
    console.log(isClick);
    if (isClick) {

        map.addLayer(layerName)
        isClick = false;
    } else {

        map.removeLayer(layerName);
        isClick = true;
    };
    return isClick;
}

$("#my_kw_jundad").click(() => {
    check.non = clickCheck(check.non, nonLayer);
})

$("#my_kw_forestPath").click(() => {
    check.path = clickCheck(check.path, forestPathLayer);
})

$("#my_kw_forestfire_from_people").click(() => {
    check.bigFire.firePeople = clickCheck(check.bigFire.firePeople, forestFireFromPeopleLayer);
})


$("#my_kw_hospital").click(() => {
    check.hosp.allhosp = clickCheck(check.hosp.allhosp, hospitalLayer);
})

$("#my_kw_big_hospital").click(() => {
    check.hosp.bighosp = clickCheck(check.hosp.bighosp, bigHospitalLayer); s
})

$("#my_kw_road").click(() => {
    check.road = clickCheck(check.road, roadLayer)
})

$("#my_forestfire_points").click(() => {
    check.bigFire.fire = clickCheck(check.bigFire.fire, forestFireLayer);
})

$("#my_kw_dae").click(() => {
    check.dae = clickCheck(check.dae, daeLayer);
})

$("#my_kw_res").click(() => {
    check.resvoir = clickCheck(check.resvoir, resvoirLayer);
})

$("#my_kw_firestation").click(() => {
    check.firest = clickCheck(check.firest, firestationLayer);
})

// $("#my_kw_forestPath").click(() => {
//     alert("등산로 데이터");
//     map.addLayer(forestPathLayer);
// })

$("#my_forestfire_heatmap").click(() => {
    check.bigFire.fireHeat = clickCheck(check.bigFire.fireHeat, fireHeatMapLayer);
});

$("#clear-button").click(() => {
    claerButton();
});

function claerButton() {
    map.removeLayer(fireHeatMapLayer);
    map.removeLayer(bigHospitalLayer);
    map.removeLayer(forestPathLayer);
    map.removeLayer(daeLayer);
    map.removeLayer(firestationLayer);
    map.removeLayer(forestFireLayer);
    map.removeLayer(resvoirLayer);
    map.removeLayer(roadLayer);
    map.removeLayer(hospitalLayer);

}




