package com.learncrypto.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EncryptFragment extends Fragment {
    String[] ciphers = {
            "Shift Cipher",
            "Affine Cipher",
            "Substitution Cipher",
            "Permutation Cipher",
            "Vigenere Cipher",
            "Hill Cipher",
            "SPN Cipher"
    };

    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encrypt, container, false);

        CiphersListAdapter adapter = new CiphersListAdapter(view.getContext(), ciphers);

        listView = (ListView) view.findViewById(R.id.cipher_listview);
        listView.setAdapter(adapter);

        return view;
    }
}