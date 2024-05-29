package com.myrsoft.apilapuntainmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> errorMutable;

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<String> getErrorMutable(){
        if (errorMutable == null){
            errorMutable = new MutableLiveData<>();
        }
        return errorMutable;
    }
    public void cambiarClave(String claveActual, String claveNueva){
        if (claveActual.isEmpty() || claveNueva.isEmpty()){
            errorMutable.setValue("Debe completar todos los campos");
        }else{
            String token = ApiClientRetrofit.leerToken(context);
            ApiClientRetrofit.ApiInmobiliaria apiInmobiliaria = ApiClientRetrofit.getApiInmobiliaria();
            Call<String> call = apiInmobiliaria.editarClave(token, claveActual, claveNueva);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        String tokenGuardar = "Bearer "+ response.body();
                        ApiClientRetrofit.guardarToken(context, tokenGuardar);
                        Toast.makeText(context, "Clave modificada correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Error, no se pudo modificar la clave", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("salida", t.getMessage());
                }
            });
        }
    }
}