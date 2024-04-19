package com.learncrypto.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CiphersListAdapter extends  ArrayAdapter<String> {

    public CiphersListAdapter(Context context, String[] ciphers) {
        super(context, 0, ciphers);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String cipherName = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.cipher_list_item, parent, false);
        }

        TextView cipher_list_item_name = convertView.findViewById(R.id.cipher_list_item_name);
        cipher_list_item_name.setText(cipherName);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CipherActivity.class);
            intent.putExtra("CIPHER_NAME", cipherName);
            getContext().startActivity(intent);
        });

        return convertView;
    }
}
