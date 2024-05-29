package com.myrsoft.apilapuntainmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myrsoft.apilapuntainmobiliaria.modelo.Contrato;
import com.myrsoft.apilapuntainmobiliaria.modelo.Inmueble;
import com.myrsoft.apilapuntainmobiliaria.modelo.Inquilino;
import com.myrsoft.apilapuntainmobiliaria.modelo.Pago;
import com.myrsoft.apilapuntainmobiliaria.modelo.Propietario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClientRetrofit {
    public static final String URLBASE = "http://192.168.8.100:5000/";

    private static ApiInmobiliaria apiInmobilaria;

    public static ApiInmobiliaria getApiInmobiliaria(){
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInmobilaria = retrofit.create(ApiInmobiliaria.class);
        return apiInmobilaria;
    }

    public interface ApiInmobiliaria{
        @FormUrlEncoded
        @POST("Propietarios/Login")
        Call<String> login(@Field("Email") String usuario, @Field("Password") String password);

        @GET("Propietarios/Perfil")
        Call<Propietario> obtenerPerfil(@Header("Authorization") String token);

        @PUT("Propietarios/Editar")
        Call<Propietario> editarPerfil(@Header("Authorization") String token, @Body Propietario propietario);

        @FormUrlEncoded
        @PUT("Propietarios/EditarClave")
        Call<String> editarClave(@Header("Authorization") String token,
                                 @Field("actual") String actual,
                                 @Field("nueva") String nueva);

        @FormUrlEncoded
        @PUT("Propietarios/cambiarviejacontraseña")
        Call<Void> cambiarPassword(@Header("Authorization") String token, @Field("ClaveVieja") String claveVieja, @Field("ClaveNueva") String claveNueva, @Field("RepetirClaveNueva") String repetirClaveNueva);

        @FormUrlEncoded
        @POST("Propietarios/olvidecontraseña")
        Call<Void> enviarEmail(@Field("email") String email);

        //@POST("Propietarios/RecuperarClave")
        //Call<Propietario> enviarMailRecupero(@Field("email") String email);

        @GET("Inmuebles/Todos")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);

        //@GET("Inmuebles/Obtener/{id}")
        //Call<Inmueble> obtenerInmueble(@Header("Authorization") String token,  @Path("id") int id);

        @PUT("Inmuebles/Editar/{id}")
        Call<Inmueble> editarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart
        @POST("Inmuebles/Crear")
        Call<Inmueble> crearInmueble(@Header("Authorization") String token,
                                     @Part("Direccion") RequestBody direccion,
                                     @Part("Ambientes") RequestBody ambientes,
                                     @Part("Tipo") RequestBody tipo,
                                     @Part("Uso") RequestBody uso,
                                     @Part("Precio") RequestBody precio,
                                     @Part MultipartBody.Part imagen
        );

        @GET("Inmuebles/Alquilados")
        Call<List<Inmueble>> obtenerInmueblesAlquilados(@Header("Authorization") String token);

        @GET("Inquilinos/Obtener/{id}")
        Call<Inquilino> obtenerInquilinoPorInmueble(@Header("Authorization") String token, @Path("id") int id);

        @GET("Contratos/Obtener/{id}")
        Call<Contrato> obtenerContratoPorInmueble(@Header("Authorization") String token, @Path("id") int id);

        @GET("Pagos/Obtener/{id}")
        Call<List<Pago>> obtenerPagosPorContrato(@Header("Authorization") String token, @Path("id") int id);
    }

    public static void guardarToken(Context context, String token){
        SharedPreferences sp=context.getSharedPreferences("token.xml",0);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public static String leerToken(Context context){
        SharedPreferences sp=context.getSharedPreferences("token.xml",0);
        return sp.getString("token","");
    }
    public static void borrarToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("token");
        editor.apply();
    }
}
