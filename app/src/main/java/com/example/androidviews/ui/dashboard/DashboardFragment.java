package com.example.androidviews.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidviews.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        TextView tvValor = view.findViewById(R.id.tvValor);
        Button btnInc = view.findViewById(R.id.btnIncrementar);

        viewModel.count.observe(getViewLifecycleOwner(), value -> tvValor.setText(String.valueOf(value)));
        btnInc.setOnClickListener(v -> viewModel.increment());
    }
}
