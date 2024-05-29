package com.myrsoft.apilapuntainmobiliaria.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.SupportMapFragment;
import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentInicioBinding;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding binding;
    private InicioViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InicioViewModel inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InicioViewModel.class);
        vm.getMapaMutable().observe(getActivity(), new Observer<InicioViewModel.miMapa>() {
            @Override
            public void onChanged(InicioViewModel.miMapa miMapa) {
                SupportMapFragment support = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
                support.getMapAsync(miMapa);
            }
        });
        vm.iniciarMapa();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}