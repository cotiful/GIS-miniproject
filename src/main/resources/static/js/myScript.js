var view = new ol.View({
	center : [ 14318984.363280362, 4514923.621443103 ],
	zoom : 7
});

var myUrl = 'http://127.0.0.1:8080/geoserver/wms'



var layers = [
	new ol.layer.Tile({
		source : new ol.source.OSM()
	})
];

var map = new ol.Map({
	target : "map",
	view : view,
	layers : layers
});


var hospitalLayer = new ol.layer.Tile({
	source: new ol.source.TileWMS({
		url :myUrl,
		params : {
			VERSION : '1.3.0',
			LAYERS : 'miniproject:kw_genhosp',
			WIDTH : 256,
			HEIGHT : 256,
			CRS : 'EPSG:5174',
			TILED : true,
		},
		serverType : 'geoserver'
})
});


var fireLayer = new ol.layer.Image({
	source: new ol.source.ImageWMS({
	url: myUrl,
	params: {'LAYERS': 'miniproject:kw_forestfire_all'},
	ratio: 1,
	serverType: 'geoserver'
	})
});


$("#my_kw_hospital").click(()=>{
  hospital();
})


 $("#forest-fire").click(()=>{
	forestFire();
});

$("#clear-button").click(()=>{
	forestFireClaer();
});

function forestFire(){
	map.addLayer(fireLayer);
 }

function forestFireClaer(){
	map.removeLayer(fireLayer);
	map.removeLayer(hospitalLayer);
	//alert("초기화");
}

function hospital(){
	map.addLayer(hospitalLayer);
}
