package com.myrsoft.apilapuntainmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myrsoft.apilapuntainmobiliaria.MainActivity;
import com.myrsoft.apilapuntainmobiliaria.modelo.Propietario;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> propietarioMutable;
    private MutableLiveData<Boolean> editableMutable;
    private Context context;


    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Propietario> getPropietarioMutable() {
        if (propietarioMutable == null){
            propietarioMutable = new MutableLiveData<>();
        }
        return propietarioMutable;
    }
    public LiveData<Boolean> getEditableMutable(){
        if (editableMutable == null){
            editableMutable = new MutableLiveData<>();
            editableMutable.setValue(false);
        }
        return editableMutable;
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
    public void CambiarEstado(){
        editableMutable.setValue(!editableMutable.getValue());
        }
        public void GuardarPropietario(Propietario p){
        String token = ApiClientRetrofit.leerToken(context);
        if (token.isEmpty()){
            Log.d("salida", "Falta token");
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return;
        }
        ApiClientRetrofit.ApiInmobiliaria apiInmobiliaria = ApiClientRetrofit.getApiInmobiliaria();
        Call<Propietario> call = apiInmobiliaria.editarPerfil(token, p);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                Log.d("salida", response.raw().toString());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        propietarioMutable.setValue(p);
                        Toast.makeText(context, "Editado correctamente", Toast.LENGTH_SHORT).show();
                        CambiarEstado();
                    }
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(context, "No se pudo obtener al usuario", Toast.LENGTH_SHORT).show();
            }
        });
        }
}