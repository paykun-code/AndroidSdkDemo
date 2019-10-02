package com.techriz.paykunandroidintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.Objects;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        if(Prefs.getString("merchantid" , null) == null){
            MerchantInfo bottomSheetFragment = new MerchantInfo();
            bottomSheetFragment.setCancelable(false);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        }
    }

    public void link(View v){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://sandbox.paykun.in/PJVHKuY")));

    }

    public void gateway(View v){
        startActivity(new Intent(Dashboard.this, Pay.class));
    }
}
