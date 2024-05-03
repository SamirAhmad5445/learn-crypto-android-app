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

public class ShiftCipherFragment extends Fragment {
    private RadioButton encrypt_radio;
    private TextView label;
    private EditText text_input;
    private EditText key_input;
    private Button cipher_btn;
    private TextView text_output;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shift_cipher, container, false);

        RadioGroup radio_group = view.findViewById(R.id.shift_cipher_radio_group);
        encrypt_radio = view.findViewById(R.id.shift_cipher_encrypt_radio);
        label = view.findViewById(R.id.shift_cipher_input_label);
        text_input = view.findViewById(R.id.shift_cipher_text_input);
        key_input = view.findViewById(R.id.shift_cipher_key_input);
        cipher_btn = view.findViewById(R.id.shift_cipher_btn);
        text_output = view.findViewById(R.id.shift_cipher_output);
        Button copy_btn = view.findViewById(R.id.shift_cipher_copy_btn);
        Button clear_btn = view.findViewById(R.id.shift_cipher_clear_btn);

        radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            if(encrypt_radio.isChecked()) {
                label.setText("Plain text");
                text_output.setHint("Cipher text");
                cipher_btn.setText("Encrypt");
            } else {
                label.setText("Cipher text");
                text_output.setHint("Plain text");
                cipher_btn.setText("Decrypt");
            }

            text_input.setText(
                    text_output.getText().toString()
            );
            text_output.setText("");
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

        copy_btn.setOnClickListener(v -> {
            if(!text_output.getText().toString().isEmpty()) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text", text_output.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(view.getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "No Text to copy", Toast.LENGTH_SHORT).show();
            }
        });

        clear_btn.setOnClickListener(v -> {
            text_input.setText("");
            key_input.setText("");
            text_output.setText("");
        });

        return view;
    }

}