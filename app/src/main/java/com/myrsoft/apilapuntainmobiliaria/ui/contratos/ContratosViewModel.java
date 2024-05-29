package com.myrsoft.apilapuntainmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myrsoft.apilapuntainmobiliaria.modelo.Inmueble;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> listaMutable;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<List<Inmueble>> getListaMutable(){
        if (listaMutable == null){
            listaMutable = new MutableLiveData<>();
        }
        return listaMutable;
    }
    public void leerInmuebles(){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria apiInmobiliaria = ApiClientRetrofit.getApiInmobiliaria();
        Call<List<Inmueble>> call = apiInmobiliaria.obtenerInmueblesAlquilados(token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                Log.d("salida", response.raw().toString());
                if (response.isSuccessful()){
                    listaMutable.postValue(response.body());
                }else {
                    Log.d("salida", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }
}