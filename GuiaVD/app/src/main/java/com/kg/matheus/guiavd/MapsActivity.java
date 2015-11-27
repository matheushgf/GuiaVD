package com.kg.matheus.guiavd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String m_Text = "";
    Controlador crud = new Controlador(getBaseContext());
    JsonUsers j = new JsonUsers();
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        m_Text=getUsername();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location atual = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        LatLng aqui = new LatLng(atual.getLatitude(), atual.getLongitude());

        if(j.verificaConexao(getBaseContext())) {
            try {
                users = j.jsonParseUser(j.getJson(getBaseContext()));
            } catch (IOException e) {
                e.printStackTrace();
            }

//            boolean existe = false;
//            String st = "";
//            for (User u : users) {
//                if (u.getUsername() == m_Text){
//                    existe = true;
//                    Toast t = Toast.makeText(getApplicationContext(), "Existe", Toast.LENGTH_LONG);
//                    t.show();
//                }
//                st=st+u.getUsername()+" ";
//            }
//            if (existe) {
//                j.updateUser(m_Text, atual.getLatitude(), atual.getLongitude(), getApplicationContext());
//            }else{
//                j.createUser(m_Text, atual.getLatitude(), atual.getLongitude(), getApplicationContext());
//            }
        }

        for(User u: users){
            LatLng m = new LatLng(u.getLat(), u.getLon());
            mMap.addMarker(new MarkerOptions().position(m).title(u.getUsername()).snippet(u.getUsername()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(m));
        }
        mMap.addMarker(new MarkerOptions().position(aqui).title("Você está aqui"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aqui, 11f));


    }

    public String getUsername(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Criar nome de usuário");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        return m_Text;
    }
}
