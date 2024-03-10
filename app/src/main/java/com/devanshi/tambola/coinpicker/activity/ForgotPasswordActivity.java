package com.devanshi.tambola.coinpicker.activity;

import android.os.*;
import android.util.*;

import androidx.databinding.*;

import com.devanshi.tambola.coinpicker.*;
import com.devanshi.tambola.coinpicker.apis.*;
import com.devanshi.tambola.coinpicker.databinding.*;
import com.devanshi.tambola.coinpicker.models.*;
import com.devanshi.tambola.coinpicker.utils.Utils;

import org.jetbrains.annotations.*;

import retrofit2.*;

public class ForgotPasswordActivity extends BaseActivity {

    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        setupUI(binding.rootView);

        initUI();
    }

    private void initUI() {
        binding.imgBackPress.setOnClickListener(v -> onBackPressed());

        binding.btnSubmit.setOnClickListener(v -> {
            String userName = binding.edtUsername.getText().toString();
            if (userName.isEmpty()) {
                Utils.showSnackBar(binding.rootView, "Username cannot be empty", true, getApplicationContext());
            } /*else {
                callForgotPasswordApi(userName);
            }*/
        });
    }

    private void callForgotPasswordApi(String userName) {
        Utils.showProgress(ForgotPasswordActivity.this);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<UserModel> call = apiInterface.forgotPasswordApi(userName);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                Utils.hideProgress();
                if (response.body() != null) {
                    Log.e(Utils.getTag(), "onResponse: "+response.body().toString());
                    UserModel userModel = response.body();
                    if (userModel.getStatus()){
                        Utils.customToast(ForgotPasswordActivity.this, userModel.getMessage()).show();
                    } else {
                        Utils.showSnackBar(binding.rootView, userModel.getMessage(), true, getApplicationContext());
                    }
                    Log.v("TAG_RESPONSE", "SUCCESS");
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                Utils.hideProgress();
                Utils.showSnackBar(binding.rootView, "Failed", true, getApplicationContext());
                t.printStackTrace();
                Log.v("TAG_RESPONSE", "FAIL \n" + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}