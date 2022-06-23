var view = new ol.View({
	center : [ 14318984.363280362, 4514923.621443103 ],
	zoom : 7
});

var myUrl = 'http://127.0.0.1:8080/geoserver/wms'


var hospital = new ol.source.TileWMS({
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
});


var layers = [
	new ol.layer.Tile({
		source : new ol.source.OSM()
	}), new ol.layer.Tile({
		source : hospital
	})
	//   new ol.layer.Image({
	// 	source: new ol.source.ImageWMS({
	// 	  url: myUrl,
	// 	  params: {'LAYERS': 'miniproject:kw_forestfire_all'},
	// 	  ratio: 1,
	// 	  serverType: 'geoserver'
	// 	})
	//   })
];

var map = new ol.Map({
	target : "map",
	view : view,
	layers : layers
});

$("#forest-fire").click(()=>{
	forestfire();
});

var fireLayer = new ol.layer.Image({
	source: new ol.source.ImageWMS({
	url: myUrl,
	params: {'LAYERS': 'miniproject:kw_forestfire_all'},
	ratio: 1,
	serverType: 'geoserver'
	})
});

function forestfire(){
	map.addLayer(fireLayer);
 }

$("#clear-button").click(()=>{
	clear();
});

function clear(){
	map.removeLayer(fireLayer);
	//alert("초기화");
}

