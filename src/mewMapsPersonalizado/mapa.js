var map;
var idInfoBoxAberto;
var infoBox = [];
var markers = [];
var marker;

var long, lat;
var enderecoFormatado;
var geocoder;

function initialize() {
    var latlng = new google.maps.LatLng(-18.8800397, -47.05878999999999);

    var options = {
        zoom: 5,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("mapa"), options);
    geocoder = new google.maps.Geocoder();

    marker = new google.maps.Marker({
        map: map,
        draggable: true
    });
    
    google.maps.event.addListener(marker, 'drag', function () {
        geocoder.geocode({'latLng': marker.getPosition()}, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    lat = marker.getPosition().lat();
                    long = marker.getPosition().lng();
                    enderecoFormatado = results[0].formatted_address;
                }
            }
        });
    });
/*
    var infowindow = new google.maps.InfoWindow(), marker;

    google.maps.event.addListener(marker, 'click', (function (marker, i) {
        return function () {
            infowindow.setContent(enderecoFormatado);
            infowindow.open(map, marker);
        }
    })(marker));*/
}

initialize();

function abrirInfoBox(id, marker) {
    if (typeof (idInfoBoxAberto) == 'number' && typeof (infoBox[idInfoBoxAberto]) == 'object') {
        infoBox[idInfoBoxAberto].close();
    }

    infoBox[id].open(map, marker);
    idInfoBoxAberto = id;
}

function carregarPontos() {

    $.getJSON('pontos.json', function (pontos) {

        var latlngbounds = new google.maps.LatLngBounds();

        $.each(pontos, function (index, ponto) {

            var marker = new google.maps.Marker({
                position: new google.maps.LatLng(ponto.Latitude, ponto.Longitude),
                title: "Meu ponto personalizado! :-D",
                icon: 'marcador.png'
            });

            var myOptions = {
                content: "<p>" + ponto.Descricao + "</p>",
                pixelOffset: new google.maps.Size(-150, 0)
            };

            infoBox[ponto.Id] = new InfoBox(myOptions);
            infoBox[ponto.Id].marker = marker;

            infoBox[ponto.Id].listener = google.maps.event.addListener(marker, 'click', function (e) {
                abrirInfoBox(ponto.Id, marker);
            });

            markers.push(marker);

            latlngbounds.extend(marker.position);

        });

        var markerCluster = new MarkerClusterer(map, markers);

        map.fitBounds(latlngbounds);

    });

}

carregarPontos();

function carregarNoMapa(endereco) {
    geocoder.geocode({'address': endereco + ', Brasil', 'region': 'BR'}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[0]) {

                lat = results[0].geometry.location.lat();
                long = results[0].geometry.location.lng();
                enderecoFormatado = results[0].formatted_address;

                var location = new google.maps.LatLng(lat, long);
                marker.setPosition(location);
                map.setCenter(location);
                map.setZoom(15);
            }
        } else {
            enderecoFormatado = "Endereço Inválido";
        }
    });
}

function  getLatitude() {
    return lat;
}
function  getLongitude() {
    return long;
}

function getEnderecoFormatado() {
    return enderecoFormatado;
}