package com.learncrypto.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    View view;
    Button getStartedBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        getStartedBtn = view.findViewById(R.id.get_started_btn);

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the main activity which ia the parent activity
                MainActivity mainActivity = (MainActivity)getActivity();

                // new learnFragment is needed to navigate to
                Fragment learnFragment = new LearnFragment();

                // use the updateFragment to replace fragments + use setSelected to update the bottom nav item id
                mainActivity.updateFragment(learnFragment);
                mainActivity.binding.bottomNavigationView.setSelectedItemId(R.id.navItemLearn);
            }
        });

        return view;

    }

}