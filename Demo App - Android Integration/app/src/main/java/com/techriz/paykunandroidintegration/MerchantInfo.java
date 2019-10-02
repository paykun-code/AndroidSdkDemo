package com.techriz.paykunandroidintegration;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Objects;

public class MerchantInfo extends BottomSheetDialogFragment {
    //Button
    public MerchantInfo() {
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
        View view = inflater.inflate(R.layout.merchant_info_bottomsheet, container, false);

        EditText merchantIdView = view.findViewById(R.id.merchantid);
        EditText accessTokenView = view.findViewById(R.id.accesstoken);
        CardView okay = view.findViewById(R.id.okay);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(merchantIdView.getText().toString()) && !TextUtils.isEmpty(accessTokenView.getText().toString())) {
                    Prefs.putString("merchantid", merchantIdView.getText().toString());
                    Prefs.putString("accesstoken", accessTokenView.getText().toString());
                    startActivity(new Intent(getContext(), Dashboard.class));
                }
                else
                {
                    Toast.makeText(getContext(), "Please fill in the necessary details.", Toast.LENGTH_LONG).show();
                }

            }
        });


        return view;
    }
}
