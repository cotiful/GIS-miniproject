var view = new ol.View({
	center : [ 14318984.363280362, 4514923.621443103 ],
	zoom : 8
});

var myUrl = 'http://127.0.0.1:8080/geoserver/wms' 

var tileSource = new ol.source.TileWMS({
	url : myUrl,
	params : {
		VERSION : '1.3.0',
		LAYERS : 'miniproject:kw_forestarea',
		//LAYERS : 'seoul:admin_emd,seoul:subway,seoul:subway_station',
		WIDTH : 256,
		HEIGHT : 256,
//			STYLES : 'population_density',
		CRS : 'EPSG:5174',
		TILED : true
	},
	serverType : 'geoserver'
});

var hospital = new ol.source.TileWMS({
	url :myUrl,
	params : {
		VERSION : '1.3.0',
		LAYERS : 'miniproject:kw_genhosp',
		WIDTH : 256,
		HEIGHT : 256,
		CRS : 'EPSG:5174',
		TILED : true
	},
	serverType : 'geoserver'
});

var layers = [
	new ol.layer.Tile({
		source : new ol.source.OSM()
	}), new ol.layer.Tile({
		source : tileSource
	}), new ol.layer.Tile({
		source : hospital
	})
];

var map = new ol.Map({
	target : 'map',
	view : view,
	layers : layers
});
