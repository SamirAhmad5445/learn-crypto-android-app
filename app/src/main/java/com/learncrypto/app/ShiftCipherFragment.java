package com.learncrypto.app;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ShiftCipherFragment extends Fragment {

    private RadioGroup radio_group;
    private RadioButton encrypt_radio;
    private RadioButton decrypt_radio;
    private TextView label;
    private EditText text_input;
    private EditText key_input;
    private Button cipher_btn;
    private TextView text_output;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shift_cipher, container, false);

        radio_group = view.findViewById(R.id.shift_cipher_radio_group);
        encrypt_radio = view.findViewById(R.id.shift_cipher_encrypt_radio);
        decrypt_radio = view.findViewById(R.id.shift_cipher_decrypt_radio);
        label = view.findViewById(R.id.shift_cipher_input_label);
        text_input = view.findViewById(R.id.shift_cipher_text_input);
        key_input = view.findViewById(R.id.shift_cipher_key_input);
        cipher_btn = view.findViewById(R.id.shift_cipher_btn);
        text_output = view.findViewById(R.id.shift_cipher_output);


        radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checked = view.findViewById(checkedId);

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
                if(!text_input.getText().toString().isEmpty() && !key_input.getText().toString().isEmpty()) {
                    text_output.setText(Ciphers.Shift.encrypt(
                            text_input.getText().toString(),
                            key_input.getText().toString().charAt(0)
                    ));
                } else {
                    text_output.setText("");
                }
            } else {
                if(!text_input.getText().toString().isEmpty() && !key_input.getText().toString().isEmpty()) {
                    text_output.setText(Ciphers.Shift.decrypt(
                            text_input.getText().toString(),
                            key_input.getText().toString().charAt(0)
                    ));
                } else {
                    text_output.setText("");
                }
            }
        });


        return view;
    }
}