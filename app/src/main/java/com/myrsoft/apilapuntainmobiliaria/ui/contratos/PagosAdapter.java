package com.myrsoft.apilapuntainmobiliaria.ui.contratos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.modelo.Pago;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolder> {
    private List<Pago> pagos;
    private Context contexto;
    private LayoutInflater li;
    public PagosAdapter(List<Pago> pagos, Context contexto, LayoutInflater li) {
        this.pagos = pagos;
        this.contexto = contexto;
        this.li = li;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_pago, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.numero.setText(String.valueOf(pagos.get(position).getNumero()));
        holder.codigoContrato.setText(String.valueOf(pagos.get(position).getContrato().getId()));
        holder.importe.setText(String.valueOf(pagos.get(position).getImporte()));

        LocalDate fechaPago = LocalDate.parse(pagos.get(position).getFecha(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        holder.fecha.setText(fechaPago.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        holder.id.setText(String.valueOf(pagos.get(position).getId()));
    }
    @Override
    public int getItemCount() {
        return pagos.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id;
        private TextView numero;
        private TextView codigoContrato;
        private TextView importe;
        private TextView fecha;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tvCodigo);
            numero = itemView.findViewById(R.id.tvNumero);
            codigoContrato = itemView.findViewById(R.id.tvCodigoContrato);
            importe = itemView.findViewById(R.id.tvImporte);
            fecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
