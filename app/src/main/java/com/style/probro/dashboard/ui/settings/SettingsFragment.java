package com.style.probro.dashboard.ui.settings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.style.probro.R;
import com.style.probro.app_utils.AppRoute;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    TextView logoutTV;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        logoutTV = root.findViewById(R.id.logout_tv);
        TextView termsAndConditionsTV = root.findViewById(R.id.privacy_policy_tv);
        termsAndConditionsTV.setOnClickListener(v -> {
            AppRoute.termsAndConditionActivity(getActivity());
        });

        TextView returnPolicyTV = root.findViewById(R.id.return_policy_tv);
        returnPolicyTV.setOnClickListener(v -> {
            AppRoute.returnPolicyActivity(getActivity());
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(account == null) {
            logoutTV.setVisibility(View.GONE);
        } else {
            logoutTV.setVisibility(View.VISIBLE);
            logoutTV.setOnClickListener(v -> {
                // ToDo : Show Warning pop up for log-out.
                ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Please wait...", true, false);
                // [START auth_fui_signout]
                AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnCompleteListener(task -> {
                            dialog.dismiss();
                            if(task.isSuccessful()) {
                                logoutTV.setVisibility(View.GONE);
                            }
                        });
                // [END auth_fui_signout]
            });
        }
    }
}