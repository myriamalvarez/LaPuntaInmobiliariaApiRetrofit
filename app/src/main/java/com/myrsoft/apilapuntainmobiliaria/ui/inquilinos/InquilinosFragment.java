package com.myrsoft.apilapuntainmobiliaria.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentInquilinosBinding;
import com.myrsoft.apilapuntainmobiliaria.modelo.Inmueble;

import java.util.List;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel vm;
    private FragmentInquilinosBinding binding;
    private InquilinoAdapter adapter;

    public static InquilinosFragment newInstance() {
        return new InquilinosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding = FragmentInquilinosBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        vm.cargarInmuebles();
        vm.getInmueblesMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                RecyclerView rv = root.findViewById(R.id.rvInquilinos);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                rv.setLayoutManager(glm);
                adapter = new InquilinoAdapter(getContext(), inmuebles, getLayoutInflater());
                rv.setAdapter(adapter);
            }
        });
        return root;
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}