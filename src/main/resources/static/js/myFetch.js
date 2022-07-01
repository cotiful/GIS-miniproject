
$("#btn-search").click(() => {
    let keyword = $("#keyword").val();
    search(keyword);
})

$("#my-ranking-btn").click(() => {
    ranking();
})

//  동해 산불 
$("#my_donghae_forestFire").click(() => {
    donghaeForestFire();
    $("#location_title").html("동해시 읍면동 산불 발생 총횟수");

})

// 동해 등산로
$("#my_donghae_forestPath").click(() => {
    donghaeForestPath();
    $("#location_title").html("동해시 읍면동 등산로 총 길이");
})

// 동해 저수지
$("#my_donghae_resvoir").click(() => {
    donghaeResvoir();
    $("#location_title").html("동해시 읍면동 저수지 총 면적");
})
// 동해 병원
$("#my_donghae_hospital").click(() => {
    donghaeHospital();
    $("#location_title").html("동해시 읍면동 병원 거리");
})
// 동해 소방서
$("#my_donghae_firestation").click(() => {
    donghaeFirestation();
    $("#location_title").html("동해시 읍면동 소방서 거리");
})


// 동해시 산불
async function donghaeForestFire() {
    $.ajax({
        url: '/forest-fire',
        type: 'GET',
    }).success(function (data) {
        console.log("통신데이터값" + data);
        const myData = data.emdList;
        const myGeom = data.forestNumberList;
        $("#rank1").html(myData[0]);
        $("#rank2").html(myData[1]);
        $("#rank3").html(myData[2]);
        $("#rank1-1").html(myGeom[0]);
        $("#rank2-1").html(myGeom[1]);
        $("#rank3-1").html(myGeom[2]);

        // 폴리곤 레이어 집어주는 코드
    }).error(function () { console.log("통신실패") })

}

// 동해시 등산로
async function donghaeForestPath() {
    $.ajax({
        url: '/forest-path',
        type: 'GET',
    }).success(function (data) {
        console.log("통신데이터값" + data);
        const myData = data.emdList;
        const myGeom = data.forestPathList;
        $("#rank1").html(myData[0]);
        $("#rank2").html(myData[1]);
        $("#rank3").html(myData[2]);
        $("#rank1-1").html(myGeom[0]);
        $("#rank2-1").html(myGeom[1]);
        $("#rank3-1").html(myGeom[2]);

        // 폴리곤 레이어 집어주는 코드
    }).error(function () { console.log("통신실패") })
}

// 동해시 저수지
async function donghaeResvoir() {
    $.ajax({
        url: '/resv',
        type: 'GET',
    }).success(function (data) {
        console.log("통신데이터값" + data);
        const myData = data.emdList;
        const myGeom = data.resvoiList;
        $("#rank1").html(myData[0]);
        $("#rank2").html(myData[1]);
        $("#rank3").html(myData[2]);
        $("#rank1-1").html(myGeom[0]);
        $("#rank2-1").html(myGeom[1]);
        $("#rank3-1").html(myGeom[2]);

        // 폴리곤 레이어 집어주는 코드
    }).error(function () { console.log("통신실패") })

}


// 동해시 병원거리
async function donghaeHospital() {
    $.ajax({
        url: '/hospital',
        type: 'GET',
    }).success(function (data) {
        console.log("통신데이터값" + data);
        const myData = data.emdList;
        const myGeom = data.hospitalList;
        $("#rank1").html(myData[0]);
        $("#rank2").html(myData[1]);
        $("#rank3").html(myData[2]);
        $("#rank1-1").html(myGeom[0]);
        $("#rank2-1").html(myGeom[1]);
        $("#rank3-1").html(myGeom[2]);

        // 폴리곤 레이어 집어주는 코드
    }).error(function () { console.log("통신실패") })

}

// 동해시 소방서 거리
async function donghaeFirestation() {
    $.ajax({
        url: '/firestation',
        type: 'GET',
    }).success(function (data) {
        console.log("통신데이터값" + data);
        const myData = data.emdList;
        const myGeom = data.fireStationList;
        $("#rank1").html(myData[0]);
        $("#rank2").html(myData[1]);
        $("#rank3").html(myData[2]);
        $("#rank1-1").html(myGeom[0]);
        $("#rank2-1").html(myGeom[1]);
        $("#rank3-1").html(myGeom[2]);

        // 폴리곤 레이어 집어주는 코드
    }).error(function () { console.log("통신실패") })

}

async function ranking() {

    $.ajax({
        url: '/analyst',
        type: 'GET',
    }).success(function (data) {
        console.log("통신데이터값" + data);
        const myData = data.emdList;
        const myGeom = data.pointsList;
        console.log(myGeom[0])
        $("#rank1").html(myData[0]);
        $("#rank2").html(myData[1]);
        $("#rank3").html(myData[2]);
        $("#rank1-1").html(myGeom[0]);
        $("#rank2-1").html(myGeom[1]);
        $("#rank3-1").html(myGeom[2]);


        // 폴리곤 레이어 집어주는 코드

        const format = new ol.format.WKT();


        var style = [
            new ol.style.Style({
                fill: new ol.style.Fill({
                    color: 'red',

                })
            }),
        ]

        // 1등 도시
        const wkt1 = data.textList[0];
        const feature1 = format.readFeature(wkt1, {
            dataProjection: 'EPSG:5174',
            featureProjection: 'EPSG:3857',
        });
        const vector1 = new ol.layer.Vector({
            source: new ol.source.Vector({
                features: [feature1]
            }),
            // style: (feature, resolution) => {
            //     console.log(feature, resolution)
            // }
            style: style,
            opacity: 0.65
        });
        // 2등 도시
        const wkt2 = data.textList[1];
        const feature2 = format.readFeature(wkt2, {
            dataProjection: 'EPSG:5174',
            featureProjection: 'EPSG:3857',
        });
        const vector2 = new ol.layer.Vector({
            source: new ol.source.Vector({
                features: [feature2]
            }),
            style: style,
            opacity: 0.4
        });

        // 3등 도시
        const wkt3 = data.textList[2];
        const feature3 = format.readFeature(wkt3, {
            dataProjection: 'EPSG:5174',
            featureProjection: 'EPSG:3857',
        });
        const vector3 = new ol.layer.Vector({
            source: new ol.source.Vector({
                features: [feature3]
            }),
            style: style,
            opacity: 0.2
        });

        map.addLayer(vector1);
        map.addLayer(vector2);
        map.addLayer(vector3);

        const polygon1 = feature1.getGeometry();
        view.fit(polygon1, { padding: [100, 50, 30, 100] });


        //const vectorAll = [vector1, vector2, vector3];
        // flash 효과 코드

        // const duration = 3000;
        // function flash(vector1) {
        //     const start = Date.now();
        //     const listenerKey = map.on('postcompose', animate);

        //     function animate(event) {
        //         const vectorContext = event.vectorContext;
        //         const frameState = event.frameState;
        //         var flashGeom = feature.getGeometry.clone();
        //         const elapsed = frameState.time - start;

        //         var elapsedRatio = elapsed / duration;
        //         var radius = easeOut(elapsedRatio) * 30;
        //         var opacity = easeOut(1 - elapsedRatio);

        //         const style = new Style({
        //             image: new CircleStyle({
        //                 radius: radius,
        //                 stroke: new Stroke({
        //                     color: 'rgba(255, 0, 0, ' + 0.5 * opacity + ')',
        //                     width: 0.25 + opacity,
        //                 }),
        //             }),
        //         });

        //         vectorContext.setStyle(style);
        //         vectorContext.drawGeometry(flashGeom);
        //         // tell OpenLayers to continue postrender animation
        //         if (elapsed > duration) {
        //             unByKey(listenerKey);
        //         } else {
        //             map.render();
        //         }
        //     }
        // }
        // flash(vector1);

        // source.on('flash', function (e) {
        //     flash(e.vector1);
        // });




    }).error(function () { console.log("통신실패") })

}

async function search(keyword) {

    // console.log(keyword);
    // let response = await fetch(`/emd?searchKeyword=${keyword}`);
    // console.log(response);
    // let responseParse = await response.json();
    // console.log(responseParse);
    $.ajax({
        url: '/emd',
        type: 'GET',
        data: { searchKeyword: keyword }
    }).success(function (data) {
        console.log(data);
        console.log("데이터모양" + ":" + data.textList);
        // 배열로 돼있어서 string 없애주기 위해[] 넣음
        const wkt = data.textList[0];
        const format = new ol.format.WKT();
        const feature = format.readFeature(wkt, {
            dataProjection: 'EPSG:5174',
            featureProjection: 'EPSG:3857',
        });

        const vector = new ol.layer.Vector({
            source: new ol.source.Vector({
                features: [feature],
            })
        });

        const polygon = feature.getGeometry();

        map.addLayer(vector);

        // 객체 선택하면 이동하게 만들어줌 
        view.fit(polygon, { padding: [170, 50, 30, 150] });

    }).error(function (textStatus) {
        console.log("fail");
    });

}

