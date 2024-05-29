package com.myrsoft.apilapuntainmobiliaria.ui.inmuebles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.modelo.Inmueble;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder> {
    private List<Inmueble> inmuebles;
    private Context contexto;
    private LayoutInflater li;

    public InmuebleAdapter(List<Inmueble> inmuebles, Context contexto, LayoutInflater li) {
        this.inmuebles = inmuebles;
        this.contexto = contexto;
        this.li = li;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_inmueble, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.direccion.setText(inmuebles.get(position).getDireccion());
        holder.precio.setText(String.valueOf(inmuebles.get(position).getPrecio()));
        String imagen = inmuebles.get(position).getImagen().replace("\\","/");
        String url = ApiClientRetrofit.URLBASE+imagen;
        Glide.with(contexto)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagen);
    }
    @Override
    public int getItemCount() {
        return inmuebles.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView direccion;
        private TextView precio;
        private ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            precio = itemView.findViewById(R.id.tvPrecio);
            imagen = itemView.findViewById(R.id.ivImagenInmueble);
            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Inmueble inmueble = inmuebles.get(getAdapterPosition());
                    bundle.putSerializable("inmueble", inmueble);
                    Navigation.findNavController((Activity) contexto, R.id.nav_host_fragment_content_menu).navigate(R.id.detalleInmuebleFragment, bundle);
                }
            });
        }
    }
}
