package com.example.androidviews.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.androidviews.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        TextInputEditText etUsuario = view.findViewById(R.id.etUsuario);
        TextInputEditText etContrasena = view.findViewById(R.id.etContrasena);
        Button btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String user = etUsuario.getText() != null ? etUsuario.getText().toString() : "";
            String pass = etContrasena.getText() != null ? etContrasena.getText().toString() : "";
            viewModel.login(user, pass);
        });

        viewModel.loggedIn.observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_home);
            }
        });
    }
}
