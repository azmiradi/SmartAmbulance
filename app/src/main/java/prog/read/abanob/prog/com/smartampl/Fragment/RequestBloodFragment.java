package prog.read.abanob.prog.com.smartampl.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import prog.read.abanob.prog.com.smartampl.HospitalDetailsActivity;
import prog.read.abanob.prog.com.smartampl.LoginActivity;
import prog.read.abanob.prog.com.smartampl.PrefManager;
import prog.read.abanob.prog.com.smartampl.R;
import prog.read.abanob.prog.com.smartampl.model.Blood;
import prog.read.abanob.prog.com.smartampl.model.Hospital;

public class RequestBloodFragment extends Fragment implements LocationListener {
    protected LocationManager locationManager;
      EditText Quntity;
     public static  String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
   public static Hospital hospital;

    View v;
    List<Blood> blood;
    Spinner bloodType;
    String bloadid;

    ImageView imageView;
    Button button;
    private ChasingDots mChasingDotsDrawable;
    View view;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onStart() {
        super.onStart();
        hospital=new Hospital();
        if (new PrefManager(getActivity()).isUserLogedOut())
        {
            startActivity();
        }
        else {
            initialization();

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions( //Method of Fragment
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0
                );
            } else {
                //
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }

    private void startActivity() {
        Intent intent=new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.request_bloodh_fragment, container, false);

      return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ///
             }
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        latitude= String.valueOf(location.getLatitude());
        longitude= String.valueOf(location.getLongitude());
        disLoading();
     }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
    private void initialization() {
        hospital=new Hospital();
        blood= new ArrayList<>();
        Quntity=v.findViewById(R.id.Quntity);
        button=v.findViewById(R.id.RequestBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendRequest();
            }
        });
        bloodType=v.findViewById(R.id.blood_type);


        //ImageView
        imageView = (ImageView) v. findViewById(R.id.image);
        mChasingDotsDrawable = new ChasingDots();
        mChasingDotsDrawable.setColor(getResources().getColor(R.color.colorPrimary));
        imageView.setImageDrawable(mChasingDotsDrawable);

        view=v.findViewById(R.id.reservation_second_screen);
        view.setVisibility(View.GONE);
        disLoading();
        bloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bloadid= String.valueOf(blood.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
         getAllBlood();
    }

    private void getAllBlood() {
        showLoading();
        final String url = "http://cinematvone.com/smart_ambulance/api/paramedic/bloodTypes";


        final List<String> names=new ArrayList<>();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("blood_types");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String id=jsonObject.getString("id");
                                String type=jsonObject.getString("type");
                                blood.add(new Blood(id,type));
                                names.add(type);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (getActivity(), R.layout.spiner, names);
                            bloodType.setAdapter(adapter);//setting the adapter data into the s
                            bloadid= String.valueOf(blood.get(0).getId());
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       if (latitude!=null){
                           disLoading();
                       }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mChasingDotsDrawable.isRunning())
                        {
                            disLoading();
                            Snackbar snackbar= Snackbar.make(view, error.getMessage(),Snackbar.LENGTH_LONG);
                            snackbar.setAction(getString(R.string.tryAgian), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getAllBlood();
                                }
                            });
                            snackbar.show();
                         }

                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(getRequest);
        getRequest.setShouldCache(false);
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinshed) {
            }
            public void onFinish() {
                if (mChasingDotsDrawable.isRunning())
                {
                    getAllBlood();
                }
            }
        }.start();
    }

    private void SendRequest() {

        final String url = "http://cinematvone.com/smart_ambulance/api/paramedic/hospital?long="+longitude+
                "&lat="+latitude+"&blood_type_id="+bloadid+"&quantity="+Quntity.getText().toString()
                +"&accessToken="+new PrefManager(getActivity()).getBrand().getAccessToken();
          showLoading();
          JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           String message =response.getString("message");
                           if (message.equals("Success"))
                           {
                               JSONObject object=response.getJSONObject("hospital");
                               String id =object.getString("id");
                               String name =object.getString("name");
                               String lat =object.getString("lat");
                               String lon =object.getString("long");
                               String distance =object.getString("distance");
                               String code =object.getString("code");
                               hospital.setCode(code);
                               hospital.setDistance(distance);
                               hospital.setId(id);
                               hospital.setLat(lat);
                               hospital.setLng(lon);
                               hospital.setName(name);
                               Intent intent=new Intent(getActivity(), HospitalDetailsActivity.class);
                               startActivity(intent);
                               getActivity().finish();
                           }
                         else {
                               Snackbar.make(view, message,Snackbar.LENGTH_LONG).show();
                           }
                           disLoading();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("++++++++++"+e.getMessage());

                            disLoading();
                        }

                        disLoading();
                      }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mChasingDotsDrawable.isRunning())
                        {
                            disLoading();
                            Snackbar snackbar= Snackbar.make(view, error.getMessage(),Snackbar.LENGTH_LONG);
                            snackbar.setAction(getString(R.string.tryAgian), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getAllBlood();
                                }
                            });
                            snackbar.show();
                        }
                     }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(getRequest);
        getRequest.setShouldCache(false);
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinshed) {
            }
            public void onFinish() {
                if (mChasingDotsDrawable.isRunning())
                {
                    SendRequest();
                }
            }
        }.start();
     }
    private void showLoading(){
        view.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        mChasingDotsDrawable.start();

    }

    private void disLoading(){
        view.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        mChasingDotsDrawable.stop();

    }
}
