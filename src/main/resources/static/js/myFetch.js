$("#btn-search").click(() => {
    let keyword = $("#keyword").val();
    search(keyword);
})

async function search(keyword) {
    alert('데이터를 잘 가져왔습니다.');
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
        console.log(data.textList);
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

