package com.example.androidviews.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.androidviews.R;
import android.widget.Switch;
import android.content.SharedPreferences;
import android.widget.CompoundButton;

public class SettingsFragment extends Fragment {
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_DARK_MODE = "dark_mode";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Switch switchDark = view.findViewById(R.id.switchDarkTheme);
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        boolean isDark = prefs.getBoolean(KEY_DARK_MODE, false);
        switchDark.setChecked(isDark);

        switchDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
                AppCompatDelegate.setDefaultNightMode(isChecked ?
                        AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                requireActivity().recreate();
            }
        });
    }
}
