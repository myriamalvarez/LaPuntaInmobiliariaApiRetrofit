package com.myrsoft.apilapuntainmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.myrsoft.apilapuntainmobiliaria.R;
import com.myrsoft.apilapuntainmobiliaria.databinding.FragmentPerfilBinding;
import com.myrsoft.apilapuntainmobiliaria.modelo.Propietario;
import com.myrsoft.apilapuntainmobiliaria.request.ApiClientRetrofit;


public class PerfilFragment extends Fragment {
    private PerfilViewModel vm;
    private FragmentPerfilBinding binding;
    Propietario propietarioActual = null;
    public static PerfilFragment newInstance(){
        return  new PerfilFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm.getPropietarioMutable().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                propietarioActual = propietario;
                binding.etPerfilId.setText(String.valueOf(propietario.getId()));
                binding.etPerfilNombre.setText(propietario.getNombre());
                binding.etPerfilApellido.setText(propietario.getApellido());
                binding.etPerfilDni.setText(String.valueOf(propietario.getDni()));
                binding.etPerfilEmail.setText(propietario.getEmail());
                binding.etPerfilPassword.setText(propietario.getPassword());
                binding.etPerfilTelefono.setText(propietario.getTelefono());
                String imagen = propietario.getAvatar().replace("\\","/");
                String url = ApiClientRetrofit.URLBASE+imagen;
                Glide.with(getActivity())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivPerfilAvatar);
            }
        });
        vm.getEditableMutable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean esEditable) {
                binding.etPerfilDni.setEnabled(esEditable);
                binding.etPerfilApellido.setEnabled(esEditable);
                binding.etPerfilNombre.setEnabled(esEditable);
                binding.etPerfilTelefono.setEnabled(esEditable);
                //binding.etPerfilEmail.setEnabled(esEditable);
                //binding.etPerfilPassword.setEnabled(esEditable);

                binding.btPerfilEditar.setVisibility(esEditable ? View.GONE : View.VISIBLE);
                binding.btPerfilGuardar.setVisibility(esEditable ? View.VISIBLE : View.GONE);
            }
        });
        binding.btPerfilGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Propietario p = new Propietario(Integer.parseInt(binding.etPerfilId.getText().toString()),
                        Long.parseLong(binding.etPerfilDni.getText().toString()),
                        binding.etPerfilNombre.getText().toString(),
                        binding.etPerfilApellido.getText().toString(),
                        binding.etPerfilEmail.getText().toString(),
                        binding.etPerfilPassword.getText().toString(),
                        binding.etPerfilTelefono.getText().toString(),
                        propietarioActual.getAvatar());
                vm.GuardarPropietario(p);
            }
        });
        binding.btPerfilEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.CambiarEstado();
            }
        });
        binding.btReseteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.cambiarClaveFragment);
            }
        });
        vm.LeerUsuario();
        return root;
    }
}