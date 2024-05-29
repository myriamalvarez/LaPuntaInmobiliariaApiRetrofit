package com.myrsoft.apilapuntainmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myrsoft.apilapuntainmobiliaria.modelo.Pago;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
private Context context;
private MutableLiveData<List<Pago>> pagosMutable;
    public PagosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<List<Pago>> getPagosMutable(){
        if (pagosMutable == null){
            pagosMutable = new MutableLiveData<>();
        }
        return pagosMutable;
    }
    public void obtenerPagos(Bundle bundle){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria apiInmobiliaria = ApiClientRetrofit.getApiInmobiliaria();
        Call<List<Pago>> call = apiInmobiliaria.obtenerPagosPorContrato(token, bundle.getInt("id"));
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                Log.d("salida", response.raw().toString());
                if(response.isSuccessful()){
                    pagosMutable.postValue(response.body());
                } else{
                    Log.d("salida", response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }
}