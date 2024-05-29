package com.myrsoft.apilapuntainmobiliaria;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myrsoft.apilapuntainmobiliaria.modelo.Propietario;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Propietario> propietarioMutable;
    public MenuActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Propietario> getPropietarioMutable(){
        if (propietarioMutable == null){
            propietarioMutable = new MutableLiveData<>();
        }
        return propietarioMutable;
    }
    public void LeerUsuario(){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria apiInmobiliaria = ApiClientRetrofit.getApiInmobiliaria();
        Call<Propietario> p = apiInmobiliaria.obtenerPerfil(token);
        p.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    propietarioMutable.postValue(response.body());
                }else {
                    Log.d("salida", response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }
}
