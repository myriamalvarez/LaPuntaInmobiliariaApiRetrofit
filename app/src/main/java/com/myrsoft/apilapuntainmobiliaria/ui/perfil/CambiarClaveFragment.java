package com.myrsoft.apilapuntainmobiliaria.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentCambiarClaveBinding;

public class CambiarClaveFragment extends Fragment {
    private FragmentCambiarClaveBinding binding;
    private CambiarClaveViewModel vm;

    public static CambiarClaveFragment newInstance() {
        return new CambiarClaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCambiarClaveBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        View root = binding.getRoot();

        binding.btCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actual = binding.etClaveActual.getText().toString();
                String nueva = binding.etClaveNueva.getText().toString();
                vm.cambiarClave(actual, nueva);
            }
        });
        vm.getErrorMutable().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                binding.tvError.setText(error);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        // TODO: Use the ViewModel
    }

}