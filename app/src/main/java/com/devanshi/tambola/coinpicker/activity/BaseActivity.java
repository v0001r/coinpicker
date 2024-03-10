package com.devanshi.tambola.coinpicker.activity;

import android.os.*;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.*;

import com.devanshi.tambola.coinpicker.utils.*;

import java.util.*;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ArrayList<View> editTextArrayList = new ArrayList<>();
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                for (View view1 : editTextArrayList){
                    view1.clearFocus();
                }
                Utils.hideSoftKeyBoard(getApplicationContext(), v);
                return false;
            });
        } else {
            editTextArrayList.add(view);
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
