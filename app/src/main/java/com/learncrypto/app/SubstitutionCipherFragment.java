package com.learncrypto.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
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

public class SubstitutionCipherFragment extends Fragment {
    private View view;
    private RadioButton encrypt_radio;
    private TextView label;
    private EditText text_input;
    private LinearLayout alpha_table;
    private LinearLayout key_table;
    private Button cipher_btn;

    private TextView text_output;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_substitution_cipher, container, false);

        RadioGroup radio_group = view.findViewById(R.id.substitution_cipher_radio_group);
        encrypt_radio = view.findViewById(R.id.substitution_cipher_encrypt_radio);
        label = view.findViewById(R.id.substitution_cipher_input_label);
        text_input = view.findViewById(R.id.substitution_cipher_text_input);
        alpha_table = view.findViewById(R.id.substitution_cipher_alpha_table);
        key_table = view.findViewById(R.id.substitution_cipher_key_table);
        cipher_btn = view.findViewById(R.id.substitution_cipher_btn);
        text_output = view.findViewById(R.id.substitution_cipher_output);
        Button copy_btn = view.findViewById(R.id.substitution_cipher_copy_btn);
        Button copy_clear = view.findViewById(R.id.substitution_cipher_clear_btn);

        createTable(alpha_table, Ciphers.Substitution.ALPHABET);
        createTable(key_table, Ciphers.Substitution.ENCRYPTION_PERMUTATION);

        radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            if(encrypt_radio.isChecked()) {
                label.setText("Plain text");
                text_output.setHint("Cipher text");
                cipher_btn.setText("Encrypt");
                createTable(key_table, Ciphers.Substitution.ENCRYPTION_PERMUTATION);
            } else {
                label.setText("Cipher text");
                text_output.setHint("Plain text");
                cipher_btn.setText("Decrypt");
                createTable(key_table, Ciphers.Substitution.DECRYPTION_PERMUTATION);
            }

            text_input.setText(
                    text_output.getText().toString()
            );
            text_output.setText("");
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

        copy_clear.setOnClickListener(v -> {
            text_input.setText("");
            text_output.setText(encrypt_radio.isChecked() ? "Cipher text" : "Plain text");
        });

        return view;
    }



    private void createTable(LinearLayout table, String[] cells) {
        table.removeAllViews();

        for (String cell : cells) {
            TextView text = createStyledTableCell(cell);
            table.addView(text);
        }
    }

    private TextView createStyledTableCell(String text) {
        TextView textView = new TextView(view.getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.space_12),
                (int) getResources().getDimension(R.dimen.space_12)
        );

        textView.setLayoutParams(params);
        textView.setBackgroundResource(R.drawable.table_cell_style);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setPadding(
                getResources().getDimensionPixelSize(R.dimen.space_2),
                getResources().getDimensionPixelSize(R.dimen.space_2),
                getResources().getDimensionPixelSize(R.dimen.space_2),
                getResources().getDimensionPixelSize(R.dimen.space_2)
        );
        textView.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_base));

        return textView;
    }
}