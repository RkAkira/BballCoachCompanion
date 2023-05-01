package com.efrei.bballcoachcompanion;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocateMatchActivity extends AppCompat {

    private MapView mMapView;
    private Marker mMarker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));
        setContentView(R.layout.locate_match_activity);

        mMapView = findViewById(R.id.map_view);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);

        IMapController mapController = mMapView.getController();
        mapController.setZoom(18.0);
        GeoPoint startPoint = getAddressLocation("43 allée de la ferme d'armenon, Gif-sur-yevette");
        mapController.setCenter(startPoint);
        mMarker = new Marker(mMapView);
        mMarker.setPosition(startPoint);
        mMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mMapView.getOverlays().add(mMarker);

    }

    @Override
    public void onPause(){
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public GeoPoint getAddressLocation(String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && addresses.size() > 0) {
                Address firstAddress = addresses.get(0);
                double lat = firstAddress.getLatitude();
                double lon = firstAddress.getLongitude();
                return new GeoPoint(lat, lon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
