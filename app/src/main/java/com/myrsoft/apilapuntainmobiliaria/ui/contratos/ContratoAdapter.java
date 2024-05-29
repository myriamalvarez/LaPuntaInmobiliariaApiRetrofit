package com.myrsoft.apilapuntainmobiliaria.ui.contratos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {
    private List<Inmueble> inmuebles;
    private Context context;
    private LayoutInflater inflater;

    public ContratoAdapter(List<Inmueble> inmuebles, Context context, LayoutInflater inflater) {
        this.inmuebles = inmuebles;
        this.context = context;
        this.inflater = inflater;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_inmueble_con_contrato, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.direccion.setText(inmuebles.get(position).getDireccion());
        holder.id.setText(String.valueOf(inmuebles.get(position).getId()));
        String imagen = inmuebles.get(position).getImagen().replace("\\","/");
        String url = ApiClientRetrofit.URLBASE +imagen;
        Glide.with(context)
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
        private EditText id;
        private ImageView imagen;
        private Button ver;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            id = itemView.findViewById(R.id.etItemContratoInmuebleId);
            ver = itemView.findViewById(R.id.btContrato);
            imagen = itemView.findViewById(R.id.ivImagenInmueble);
            ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.detalleContratoFragment, bundle);
                }
            });
        }
    }
}
