package com.myrsoft.apilapuntainmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myrsoft.apilapuntainmobiliaria.modelo.Inmueble;
import com.myrsoft.apilapuntainmobiliaria.modelo.Inquilino;
import com.myrsoft.apilapuntainmobiliaria.modelo.Propietario;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;
import com.google.android.gms.common.api.Api;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inmueble> inmuebleMutable;
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Inmueble> getInmuebleMutable() {
        if(inmuebleMutable == null){
            inmuebleMutable = new MutableLiveData<>();
        }
        return inmuebleMutable;
    }

    public void cargarInmueble(Bundle bundle){
        Inmueble propiedad = (Inmueble) bundle.getSerializable("inmueble");
        inmuebleMutable.setValue(propiedad);
    }
    public void editarDisponibilidad(Inmueble inmueble){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria apiInmobiliaria = ApiClientRetrofit.getApiInmobiliaria();
        Call<Inmueble> call = apiInmobiliaria.editarInmueble(token, inmueble);
        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                //Log.d("salida", response.raw().toString());
                if (response.isSuccessful()) {
                        inmuebleMutable.postValue(response.body());
                        Toast.makeText(context, "Editado correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                    Log.d("salida", response.raw().message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Inmueble> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al editar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}