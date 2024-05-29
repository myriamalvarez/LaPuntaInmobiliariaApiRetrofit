package com.myrsoft.apilapuntainmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.LocaleList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.myrsoft.apilapuntainmobiliaria.modelo.Contrato;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DetalleContratoFragment extends Fragment {
    private FragmentDetalleContratoBinding binding;
    private DetalleContratoViewModel vm;

    public static DetalleContratoFragment newInstance() {

        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm.getContratoMutable().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                binding.tvCodigoContrato.setText(String.valueOf(contrato.getId()));
                LocalDate fechaDesde = LocalDate.parse(contrato.getDesde(), DateTimeFormatter.ISO_DATE_TIME);
                binding.tvFechaInicio.setText(fechaDesde.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                LocalDate fechaHasta = LocalDate.parse(contrato.getHasta(), DateTimeFormatter.ISO_DATE_TIME);
                binding.tvFechaFin.setText(fechaHasta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                binding.tvMontoAqluiler.setText(String.valueOf(contrato.getValor()));
                binding.tvInquilino.setText(contrato.getInquilino().getApellido());
                binding.tvInmueble.setText(contrato.getInmueble().getDireccion());
                binding.tvInmueble.setText(contrato.getInmueble().getDireccion());
                binding.btPagosC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", contrato.getId());
                        Navigation.findNavController(v).navigate(R.id.pagosFragment, bundle);
                    }
                });
            }
        });
        vm.obtenerInquilino(getArguments());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}