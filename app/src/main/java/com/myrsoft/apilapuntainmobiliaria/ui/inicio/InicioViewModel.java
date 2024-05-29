package com.myrsoft.apilapuntainmobiliaria.ui.inicio;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<miMapa> mapaMutable;

    public InicioViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<miMapa> getMapaMutable() {
        if (mapaMutable == null){
            mapaMutable = new MutableLiveData<>();
        }
        return mapaMutable;
    }
    public void iniciarMapa(){
        mapaMutable.setValue(new miMapa());
    }
    public class miMapa implements OnMapReadyCallback{
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
          googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            LatLng inmobiliaria = new LatLng(-33.184304,-66.312575);
            googleMap.addMarker(new MarkerOptions().position(inmobiliaria));

            CameraPosition camera = new CameraPosition.Builder()
                    .target(inmobiliaria)
                    .zoom(19)
                    .bearing(45)
                    .tilt(60)
                    .build();
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(camera);
            googleMap.animateCamera(update);
        }
    }
}