package com.techriz.paykunandroidintegration;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Objects;

public class PayInfo extends BottomSheetDialogFragment {
    //Button
    public PayInfo() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Prefs.Builder()
                .setContext(getActivity())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(Objects.requireNonNull(getActivity()).getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_info, container, false);
        EditText name = view.findViewById(R.id.name);
        EditText email = view.findViewById(R.id.email);
        EditText mobno = view.findViewById(R.id.mobileno);
        EditText productName = view.findViewById(R.id.productname);
        EditText amount = view.findViewById(R.id.amount);


        if (!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(mobno.getText().toString()) && !TextUtils.isEmpty(productName.getText().toString()) && !TextUtils.isEmpty(amount.getText().toString())) {

            Prefs.putString("name", name.getText().toString());
            Prefs.putString("email", email.getText().toString());
            Prefs.putString("mobno", mobno.getText().toString());
            Prefs.putString("product", productName.getText().toString());
            Prefs.putString("amount", amount.getText().toString());

        } else
            Toast.makeText(getContext(), "Please fill in all the necessary details.", Toast.LENGTH_LONG).show();


        return view;
    }
}
