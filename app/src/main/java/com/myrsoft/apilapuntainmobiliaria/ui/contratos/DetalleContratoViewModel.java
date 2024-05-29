package com.myrsoft.apilapuntainmobiliaria.ui.contratos;

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

import com.myrsoft.apilapuntainmobiliaria.modelo.Contrato;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Contrato> contratoMutable;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Contrato> getContratoMutable(){
        if (contratoMutable == null){
            contratoMutable = new MutableLiveData<>();
        }
        return contratoMutable;
    }
    public void obtenerInquilino(Bundle bundle){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria apiInmobiliaria = ApiClientRetrofit.getApiInmobiliaria();
        Call<Contrato> call = apiInmobiliaria.obtenerContratoPorInmueble(token, Integer.parseInt(bundle.getString("id")));
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                Log.d("salida", response.raw().toString());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        contratoMutable.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}