package com.learncrypto.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Objects;

public class HillCipherFragment extends Fragment {
    private View view;
    private RadioButton encrypt_radio;
    private RadioButton size_2x2_radio;
    private TextView label;
    private EditText text_input;
    private LinearLayout key_matrix;
    private int matrixSize = 2; // by default
    private Button cipher_btn;
    private TextView text_output;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hill_cipher, container, false);

        RadioGroup radio_group = view.findViewById(R.id.hill_cipher_radio_group);
        RadioGroup size_group = view.findViewById(R.id.hill_cipher_size_radio_group);
        encrypt_radio = view.findViewById(R.id.hill_cipher_encrypt_radio);
        size_2x2_radio = view.findViewById(R.id.hill_cipher_size_2x2_radio);
        label = view.findViewById(R.id.hill_cipher_input_label);
        text_input = view.findViewById(R.id.hill_cipher_text_input);
        key_matrix = view.findViewById(R.id.hill_cipher_key_matrix);
        cipher_btn = view.findViewById(R.id.hill_cipher_btn);
        text_output = view.findViewById(R.id.hill_cipher_output);
        Button copy_btn = view.findViewById(R.id.hill_cipher_copy_btn);
        Button clear_btn = view.findViewById(R.id.hill_cipher_clear_btn);

        createMatrix(matrixSize);

        radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            if(encrypt_radio.isChecked()) {
                label.setText("plain text");
                text_output.setHint("Cipher text");
                cipher_btn.setText("Encrypt");
            } else {
                label.setText("Cipher text");
                text_output.setHint("plain text");
                cipher_btn.setText("Decrypt");
            }

            text_input.setText(
                    text_output.getText().toString()
            );
            text_output.setText("");

            String key = getKeyValue();
            if(key != null){
                int[][] inverseKey = Ciphers.Hill.invertMatrix(Ciphers.Hill.getHillKeyFromString(key));

                if(inverseKey != null) {
                    setKeyValue(Ciphers.Hill.getHillKeyFromMatrix(inverseKey));
                }
            }
        });

        size_group.setOnCheckedChangeListener((group, checkedId) -> {
            matrixSize = size_2x2_radio.isChecked() ? 2 : 3;
            createMatrix(matrixSize);
        });

        cipher_btn.setOnClickListener(v -> {
            String key = getKeyValue();
            String message = text_input.getText().toString();

            if(key == null) {
                Toast.makeText(view.getContext(), "The matrix isn't complete, please fill the key matrix.", Toast.LENGTH_SHORT).show();
                text_output.setText("");
                return;
            }

            if(message.isEmpty()) {
                Toast.makeText(view.getContext(), "Please Enter the message", Toast.LENGTH_SHORT).show();
                text_output.setText("");
                return;
            }

            if(encrypt_radio.isChecked()) {
                text_output.setText(Ciphers.Hill.encrypt(message, key));
            } else {
                int[][] inverseKey = Ciphers.Hill.invertMatrix(Ciphers.Hill.getHillKeyFromString(key));
                if (inverseKey != null) {
                    text_output.setText(Ciphers.Hill.decrypt(
                            message, Ciphers.Hill.getHillKeyFromMatrix(inverseKey)));
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
            createMatrix(matrixSize);
            text_output.setText("");
        });

        return view;
    }

    void createMatrix(int size) {
        key_matrix.removeAllViews();

        for (int i = 0; i < size; i++) {
            LinearLayout row = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                rowParams.topMargin = getResources().getDimensionPixelSize(R.dimen.space_3); // Add top margin for all rows except the first
            }
            row.setLayoutParams(rowParams);
            row.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < size; j++) {
                EditText input = createStyledInput(j);
                row.addView(input);
            }

            key_matrix.addView(row);
        }
    }

    EditText createStyledInput(int order) {
        EditText input = new EditText(view.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        if (order > 0) {
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.space_5);
        }
        input.setLayoutParams(params);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setBackgroundResource(R.drawable.input_style);
        input.setPadding(
                getResources().getDimensionPixelSize(R.dimen.space_4),
                getResources().getDimensionPixelSize(R.dimen.space_2),
                getResources().getDimensionPixelSize(R.dimen.space_4),
                getResources().getDimensionPixelSize(R.dimen.space_2)
        );
        input.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_base));

        return input;
    }

    String getKeyValue() {
        StringBuilder key = new StringBuilder();

        for(int i = 0; i < key_matrix.getChildCount(); i++) {
            LinearLayout row = (LinearLayout) key_matrix.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++) {
                EditText input = (EditText) row.getChildAt(j);
                String value = input.getText().toString();

                if (value.isEmpty()) {
                    return null;
                }

                key.append(input.getText().toString()).append(" ");
            }
        }

        return key.toString().trim();
    }
    void setKeyValue(String keyString) {
        String[] bits = keyString.split(" ");
        int n = 0;
        for(int i = 0; i < key_matrix.getChildCount(); i++) {
            LinearLayout row = (LinearLayout) key_matrix.getChildAt(i);

            for(int j = 0; j < key_matrix.getChildCount(); j++) {
                EditText input = (EditText) row.getChildAt(j);
                input.setText(bits[n++]);
            }
        }
    }
}