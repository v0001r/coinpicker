package com.devanshi.tambola.coinpicker.activity;

import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;

import androidx.databinding.*;

import com.devanshi.tambola.coinpicker.*;
import com.devanshi.tambola.coinpicker.apis.*;
import com.devanshi.tambola.coinpicker.databinding.*;
import com.devanshi.tambola.coinpicker.models.*;
import com.devanshi.tambola.coinpicker.utils.Utils;
import com.devanshi.tambola.coinpicker.utils.*;

import org.jetbrains.annotations.*;

import retrofit2.*;

public class LoginSignupActivity extends BaseActivity implements View.OnClickListener {

    ActivityLoginSignupBinding binding;
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new Preference(getApplicationContext());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_signup);
        setupUI(binding.rootView);

        binding.chkboxText.setOnClickListener(v -> {
            binding.chkboxRememberMe.setChecked(!binding.chkboxRememberMe.isChecked());
            Utils.hideSoftKeyBoard(LoginSignupActivity.this,v);
        });

        binding.imgLogin.setOnClickListener(v -> {
            if (binding.clLogin.getVisibility() != View.VISIBLE){
                binding.edtLoginUsername.setText("");
                binding.edtLoginPassword.setText("");
                binding.chkboxRememberMe.setChecked(false);
                binding.clLogin.setVisibility(View.VISIBLE);
                binding.clSignup.setVisibility(View.GONE);
                binding.imgLoginBg.setVisibility(View.GONE);
                binding.imgSignupBg.setVisibility(View.VISIBLE);
            }
        });

        binding.imgSignup.setOnClickListener(v -> {
            if (binding.clSignup.getVisibility() != View.VISIBLE){
                binding.edtFname.setText("");
                binding.edtLname.setText("");
                binding.edtEmail.setText("");
                binding.edtRegistrationPassword.setText("");
                binding.edtRegistrationCpassword.setText("");
                binding.clSignup.setVisibility(View.VISIBLE);
                binding.clLogin.setVisibility(View.GONE);
                binding.imgLoginBg.setVisibility(View.VISIBLE);
                binding.imgSignupBg.setVisibility(View.GONE);
            }
        });

        binding.btnGuestLogin.setOnClickListener(v-> callLoginApi("guestuser@gmail.com", "guestuser"));

        binding.txtForgotPassword.setOnClickListener(v -> startActivity(new Intent(LoginSignupActivity.this, ForgotPasswordActivity.class)));

        binding.btnLogin.setOnClickListener(v -> {
            String loginUsername = binding.edtLoginUsername.getText().toString();
            String loginPassword = binding.edtLoginPassword.getText().toString();
            if (loginUsername.isEmpty()) {
                Utils.showSnackBar(v, "Username can't be empty", true, LoginSignupActivity.this);
            } else if (loginPassword.isEmpty()) {
                Utils.showSnackBar(v, "Password can't be empty", true, LoginSignupActivity.this);
            } else {
                callLoginApi(loginUsername, loginPassword);
            }
        });

        binding.btnRequestOtp.setOnClickListener(v -> {
            String firstName = binding.edtFname.getText().toString();
            String lastName = binding.edtLname.getText().toString();
            String email = binding.edtEmail.getText().toString();
            String password = binding.edtRegistrationPassword.getText().toString();
            String confirmPassword = binding.edtRegistrationCpassword.getText().toString();
            if (firstName.isEmpty()) {
                Utils.showSnackBar(v, "First Name can't be empty", true, LoginSignupActivity.this);
            } else if (lastName.isEmpty()) {
                Utils.showSnackBar(v, "Last Name can't be empty", true, LoginSignupActivity.this);
            } else if (email.isEmpty()) {
                Utils.showSnackBar(v, "E-mail can't be empty", true, LoginSignupActivity.this);
            } else if (password.isEmpty()) {
                Utils.showSnackBar(v, "Password number can't be empty", true, LoginSignupActivity.this);
            } else if (confirmPassword.isEmpty()) {
                Utils.showSnackBar(v, "Confirm Password can't be empty", true, LoginSignupActivity.this);
            } else if (!password.equals(confirmPassword)) {
                Utils.showSnackBar(v, "Passwords don't match", true, LoginSignupActivity.this);
            } else {
               callRegisterApi(firstName, lastName, email, password);
            }
        });
    }

    private void callLoginApi(String loginUsername, String loginPassword) {
        Utils.showProgress(LoginSignupActivity.this);

        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<UserModel> call = apiInterface.loginApi(loginUsername, loginPassword);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                Utils.hideProgress();
                if (response.body()!=null){
                    Log.e(Utils.getTag(), "onResponse: "+response.body().toString());
                    UserModel userModel=response.body();
                    if (userModel.getStatus()){
                        UserData userData =userModel.getUserData();
                        UserDetailsToPreference.setDataToPreference(getApplicationContext(), userData);
                        startActivity(new Intent(LoginSignupActivity.this, MainActivity.class));
                        finish();

                    } else {
                        Utils.showSnackBar(binding.rootView, userModel.getMessage(), true, getApplicationContext());
                    }
                    Log.v("TAG_RESPONSE","SUCCESS");
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                Utils.hideProgress();
                Utils.showSnackBar(binding.rootView,"Failed", true, getApplicationContext());
                t.printStackTrace();
                Log.v("TAG_RESPONSE","FAIL \n"+t.getMessage());
            }
        });
    }

    private void callRegisterApi(String firstName, String lastName, String email, String password) {
        Utils.showProgress(LoginSignupActivity.this);

        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<UserModel> call = apiInterface.registerApi(password, firstName, lastName, email);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                Utils.hideProgress();
                if (response.body()!=null){
                    Log.e(Utils.getTag(), "onResponse: "+response.body().toString());
                    UserModel userModel=response.body();
                    if (userModel.getStatus()){
                        String userName = userModel.getUserData().getUsername();
                        callLoginApi(userName, password);
                    } else {
                        Utils.showSnackBar(binding.rootView, userModel.getMessage(), true, getApplicationContext());
                    }
                    Log.v("TAG_RESPONSE","SUCCESS");
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                Utils.hideProgress();
                Utils.showSnackBar(binding.rootView,"Failed", true, getApplicationContext());
                t.printStackTrace();
                Log.v("TAG_RESPONSE","FAIL \n"+t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Utils.hideSoftKeyBoard(this,v);
    }
}
