package prog.read.abanob.prog.com.smartampl;

import androidx.appcompat.app.AppCompatActivity;
import prog.read.abanob.prog.com.smartampl.Fragment.RequestBloodFragment;
import prog.read.abanob.prog.com.smartampl.model.Hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HospitalDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
      private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    PinView pinView;
    TextView hospitalName,distance;
    Hospital h;
    LatLng latLng1;

    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        h=RequestBloodFragment.hospital;
        latLng1= new LatLng(Double.parseDouble(RequestBloodFragment.latitude),Double.parseDouble(RequestBloodFragment.longitude));

         pinView=findViewById(R.id.pinView);
        hospitalName=findViewById(R.id.hospitalName);
        distance=findViewById(R.id.distance);
        pinView.setText(h.getCode());
        pinView.setEnabled(false);
        distance.setText(distance(latLng1.latitude,latLng1.longitude,Double.parseDouble(h.getLat()),Double.parseDouble(h.getLng()))+"");
        hospitalName.setText(h.getName());
        mapFragment.getMapAsync(this);
        mapFragment.getMapAsync(this);


    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist)*1000;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void ShowRouts(View view) {
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ny = new LatLng(Double.parseDouble(h.getLat()),Double.parseDouble(h.getLng()));
         mMap.addMarker(new MarkerOptions().position(ny).title(h.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ny,20));

    }
}
