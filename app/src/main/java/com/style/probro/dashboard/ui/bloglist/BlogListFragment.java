package com.style.probro.dashboard.ui.bloglist;

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

import com.style.probro.R;


public class BlogListFragment extends Fragment {

    private BlogListViewModel blogListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        blogListViewModel =
                new ViewModelProvider(this).get(BlogListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bloglist, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        blogListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}