package com.example.ntut.ex10_02;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private GoogleMap map;
    private Address address;
    private String locationName;
    ///////////////////////////////////////////////
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private EditText editText_Where;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                                                    .findFragmentById(R.id.fragment_Google_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 確認權限
        askPermissions();
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(connectionCallbacks)
//                    .addOnConnectionFailedListener(onConnectionFailedListener)
                    .build();
        }
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.setTrafficEnabled(true);
        //檢查是否取得user的權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ) {
            map.setMyLocationEnabled(true);

        }
    }

    public void onclick_Button_Direct(View view) {
        boolean Direct = (lastLocation != null) && (address != null);
        if (!Direct) {

            Toast.makeText(this, "Information Wrong", Toast.LENGTH_LONG).show();
            return;
        }
        Log.e("locationListener", "lastLocation=" + lastLocation + "    address=" + address);
        double fromLat = lastLocation.getLatitude();
        double fromLng = lastLocation.getLongitude();
        double toLat = address.getLatitude();
        double toLng = address.getLongitude();
//      direct(裝置的lat,裝置的lng,目的的lat,目的的lng)
        direct(fromLat, fromLng, toLat, toLng);

        Log.e("locationListener", "lat=" + lastLocation.getLatitude() + " lon=" + lastLocation.getLongitude());
        editText_Where.setText("");
    }

    public void onclick_Button_Search(View view) {
        editText_Where = (EditText) findViewById(R.id.editText_where);
////判斷字串是否合法
        boolean useful_EditText_data = useful_EditText_data(editText_Where);
        if (!useful_EditText_data) {
            return;
        }
////抓取user輸入的位置
        locationName = editText_Where.getText().toString().trim();
        Marker_On_Map(locationName_To_Address(locationName));
//將鍵盤收起來
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText_Where.getWindowToken(), 0);//收起鍵盤*************
    }

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks =
            new GoogleApiClient.ConnectionCallbacks() {

                @Override
                public void onConnected(Bundle bundle) {
                    //Log.i(TAG, "GoogleApiClient connected");
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        LocationRequest locationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                //用時間來取得最新定位(時間/次)
                                .setInterval(10000)
                                //用距離來取得最新定位(距離/次)
                                .setSmallestDisplacement(1000);
                        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
                        //取得裝置的最後的位置
                        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                    }
                }


                @Override
                public void onConnectionSuspended(int i) {
                    //             showToast(R.string.msg_GoogleApiClientConnectionSuspended);
                }
            };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lastLocation = location;
            Log.e("locationListener", "lat=" + lastLocation.getLatitude() + " lon=" + lastLocation.getLongitude());
        }
    };


    //////////////////////////////////////////////////////////////////////////////////////////
//          將字串做解析
    private List<Address> locationName_To_Address(String locationName) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;
        int maxResults = 1;
        try {
            addressList = geocoder.getFromLocationName(locationName, maxResults);
        } catch (IOException e) {
            Log.e("locationName_To_Address",e.getMessage().toString());
        }
        return addressList;
    }

//drow Mark on map
    private void Marker_On_Map(List<Address> addressList) {

        if (addressList == null || addressList.isEmpty()) {
            Log.e("Marker_On_Map", "addressList == null || addressList.isEmpty()");
        } else {
            address = addressList.get(0);

            LatLng position = new LatLng(address.getLatitude(), address.getLongitude());

            String snippet = address.getAddressLine(0);

            map.addMarker(new MarkerOptions().position(position).title(locationName).snippet(snippet));
            map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(position).zoom(15).build()));
        }
    }

////處理導航
    private void direct(double fromLat, double fromLng, double toLat,double toLng)
    {
        String uriStr = String.format(Locale.US,"http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",fromLat,
                fromLng, toLat, toLng);
        Intent intent = new Intent();
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        intent.setAction(android.content.Intent.ACTION_VIEW);

        //取得google map 網頁版的導航參數
        intent.setData(Uri.parse(uriStr));

        //叫google map App來處理
        startActivity(intent);
    }

///<Permissions>///////////////////////////////////////////////////////////////

    // <REQ_PERMISSIONS>請求的識別編碼
    private static final int REQ_PERMISSIONS = 0;

    private void askPermissions() {
        //<permissionsRequest>該app目前無法取用的權限名單
        Set<String> permissionsRequest = new HashSet<>();
        //<permissions>該app想要取用的權限名單
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
//先確認目前擁有的權限名單
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(this, permission);

            if (result != PackageManager.PERMISSION_GRANTED) {
                //紀錄目前沒有的權限
                permissionsRequest.add(permission);
            }
        }

        if (!permissionsRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]), REQ_PERMISSIONS);
        }
    }

    // onRequestPermissionsResult 詢問權限後User的request
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSIONS:
                boolean get_permission = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        get_permission = false;
                    }
                }

                if (!get_permission) {
                    Toast.makeText(this, R.string.premission_not_get, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    /*判斷參數是否合法*/
    private boolean useful_EditText_data(EditText editText) {

        String text = editText.getText().toString();
        if (text.trim().isEmpty()) {
            editText.setError("Can not be null");//當editText為空值
            return false;
        } else {
            return true;
        }
    }


}
