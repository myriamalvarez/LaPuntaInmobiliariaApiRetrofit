package com.myrsoft.apilapuntainmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentPagosBinding;
import com.myrsoft.apilapuntainmobiliaria.modelo.Pago;

import java.util.List;

public class PagosFragment extends Fragment {
    private FragmentPagosBinding binding;
    private PagosViewModel vm;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm.getPagosMutable().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> listaActual) {
                RecyclerView rv = root.findViewById(R.id.rvPagos);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                rv.setLayoutManager(glm);
                PagosAdapter pad = new PagosAdapter(listaActual, getContext(), getLayoutInflater());
                rv.setAdapter(pad);
            }
        });
        vm.obtenerPagos(getArguments());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(PagosViewModel.class);
        // TODO: Use the ViewModel
    }

}