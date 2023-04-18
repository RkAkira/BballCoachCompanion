package com.efrei.bballcoachcompanion;

import android.app.Activity;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class LocateMatchActivity extends Activity {

    private MapView mMapView;
    private Marker mMarker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));
        setContentView(R.layout.locate_match_activity);

        mMapView = findViewById(R.id.map_view);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);

        IMapController mapController = mMapView.getController();
        mapController.setZoom(18.0);
        GeoPoint startPoint = new GeoPoint(48.8475, 2.3444); // Paris
        mapController.setCenter(startPoint);

        // Add a marker to the map
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
}
