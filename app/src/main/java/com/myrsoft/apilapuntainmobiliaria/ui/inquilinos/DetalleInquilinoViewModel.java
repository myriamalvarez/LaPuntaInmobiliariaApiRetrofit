package com.myrsoft.apilapuntainmobiliaria.ui.inquilinos;

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
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> inquilinoMutable;
    private Context context;

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Inquilino> getInquilinoMutable(){
        if (inquilinoMutable == null){
            inquilinoMutable = new MutableLiveData<>();
        }
        return inquilinoMutable;
    }
    public void cargarInquilino(Bundle bundle){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria apiInmobiliaria = ApiClientRetrofit.getApiInmobiliaria();
        Call<Inquilino> call = apiInmobiliaria.obtenerInquilinoPorInmueble(token, Integer.parseInt(bundle.getString("id")));
        call.enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(@NonNull Call<Inquilino> call, Response<Inquilino> response) {
                Log.d("salida", response.raw().toString());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        inquilinoMutable.postValue(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<Inquilino> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}