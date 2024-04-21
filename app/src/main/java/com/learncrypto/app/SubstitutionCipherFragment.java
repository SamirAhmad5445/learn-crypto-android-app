package com.learncrypto.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SubstitutionCipherFragment extends Fragment {
    private RadioGroup radio_group;
    private RadioButton encrypt_radio;
    private TextView label;
    private EditText text_input;
    private Button cipher_btn;
    private TextView text_output;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_substitution_cipher, container, false);

        radio_group = view.findViewById(R.id.substitution_cipher_radio_group);
        encrypt_radio = view.findViewById(R.id.substitution_cipher_encrypt_radio);
        label = view.findViewById(R.id.substitution_cipher_input_label);
        text_input = view.findViewById(R.id.substitution_cipher_text_input);
        cipher_btn = view.findViewById(R.id.substitution_cipher_btn);
        text_output = view.findViewById(R.id.substitution_cipher_output);

        radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            if(encrypt_radio.isChecked()) {
                label.setText("Plane text");
                text_output.setHint("Cipher text");
                cipher_btn.setText("Encrypt");
            } else {
                label.setText("Cipher text");
                text_output.setHint("Plane text");
                cipher_btn.setText("Decrypt");
            }

            String input_previous_value = text_input.getText().toString();
            text_input.setText(text_output.getText().toString());
            text_output.setText(input_previous_value);
        });

        cipher_btn.setOnClickListener(v -> {
            if(encrypt_radio.isChecked()) {
                if(!text_input.getText().toString().isEmpty()) {
                    text_output.setText(Ciphers.Substitution.encrypt(
                            text_input.getText().toString()
                    ));
                } else {
                    Toast.makeText(view.getContext(), "Fill the text input", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(!text_input.getText().toString().isEmpty()) {
                    text_output.setText(Ciphers.Substitution.decrypt(
                            text_input.getText().toString()
                    ));
                } else {
                    Toast.makeText(view.getContext(), "Fill the text input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}