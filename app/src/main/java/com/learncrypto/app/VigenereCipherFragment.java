package com.learncrypto.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

public class VigenereCipherFragment extends Fragment {

    private RadioGroup radio_group;
    private RadioButton encrypt_radio;
    private TextView label;
    private EditText text_input;
    private EditText key_input;
    private Button cipher_btn;
    private Button copy_btn;

    private TextView text_output;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vigenere_cipher, container, false);

        radio_group = view.findViewById(R.id.vigenere_cipher_radio_group);
        encrypt_radio = view.findViewById(R.id.vigenere_cipher_encrypt_radio);
        label = view.findViewById(R.id.vigenere_cipher_input_label);
        text_input = view.findViewById(R.id.vigenere_cipher_text_input);
        key_input = view.findViewById(R.id.vigenere_cipher_key_input);
        cipher_btn = view.findViewById(R.id.vigenere_cipher_btn);
        copy_btn = view.findViewById(R.id.vigenere_cipher_copy_btn);
        text_output = view.findViewById(R.id.vigenere_cipher_output);

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
                if(!text_input.getText().toString().isEmpty() && !key_input.getText().toString().isEmpty()) {
                    text_output.setText(Ciphers.Vigenere.encrypt(
                            text_input.getText().toString(),
                            key_input.getText().toString()
                    ));
                } else {
                    text_output.setText("");
                }
            } else {
                if(!text_input.getText().toString().isEmpty() && !key_input.getText().toString().isEmpty()) {
                    text_output.setText(Ciphers.Vigenere.decrypt(
                            text_input.getText().toString(),
                            key_input.getText().toString()
                    ));
                } else {
                    text_output.setText("");
                }
            }
        });

        copy_btn.setOnClickListener(v -> {
            if(!text_output.getText().toString().isEmpty()) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text", text_output.getText().toString());
                clipboard.setPrimaryClip(clip);
            } else {
                Toast.makeText(view.getContext(), "No Text to copy", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}