package com.devanshi.tambola.coinpicker.fragments.ui.dashboard;

import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.*;

import com.google.android.material.textfield.*;
import com.devanshi.tambola.coinpicker.*;
import com.devanshi.tambola.coinpicker.apis.*;
import com.devanshi.tambola.coinpicker.models.*;
import com.devanshi.tambola.coinpicker.utils.*;

import org.jetbrains.annotations.*;

import java.util.*;

import retrofit2.*;


public class ChangePasswordFragment extends Fragment {

    Preference preference;
    TextInputEditText edt_old_password, edt_new_password, edt_confirm_new_password;
    Button btn_submit;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_winner_list, container, false);
        preference = new Preference(requireContext());
        edt_old_password = root.findViewById(R.id.edt_old_password);
        edt_new_password = root.findViewById(R.id.edt_new_password);
        edt_confirm_new_password = root.findViewById(R.id.edt_confirm_new_password);
        btn_submit = root.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(v -> {
            String userName = preference.getUserName();
            String oldPassword = Objects.requireNonNull(edt_old_password.getText()).toString();
            String newPassword = Objects.requireNonNull(edt_new_password.getText()).toString();
            String confirmNewPassword = Objects.requireNonNull(edt_confirm_new_password.getText()).toString();
            if (oldPassword.isEmpty()) {
                Utils.showSnackBar(root, "Old Password cannot be empty", true, requireContext());
            } else if (newPassword.isEmpty()) {
                Utils.showSnackBar(root, "New Password cannot be empty", true, requireContext());
            } else if (confirmNewPassword.isEmpty()) {
                Utils.showSnackBar(root, "Confirm New Password cannot be empty", true, requireContext());
            } else if (!newPassword.equals(confirmNewPassword)) {
                Utils.showSnackBar(root, "Passwords don't match", true, requireContext());
            } else {
                callChangePasswordApi(userName, oldPassword, newPassword, confirmNewPassword);
            }
        });
        return root;
    }

    private void callChangePasswordApi(String userName, String oldPassword, String newPassword, String confirmNewPassword) {
        Utils.showProgress(requireContext());

        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<UserModel> call = apiInterface.changePasswordApi(userName, oldPassword, newPassword, confirmNewPassword);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                Utils.hideProgress();
                if (response.body()!=null){
                    Log.e(Utils.getTag(), "onResponse: "+response.body().toString());
                    UserModel userModel=response.body();
                    if (userModel.getStatus()){
//                        UserData userData =userModel.getUserData();
//                        UserDetailsToPreference.setDataToPreference( requireContext(), userData);
                        Utils.showSnackBar(getView(), userModel.getMessage(), true, requireContext());
                    } else {
                        Utils.showSnackBar(getView(), userModel.getMessage(), true, requireContext());
                    }
                    Log.v("TAG_RESPONSE","SUCCESS");
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                Utils.hideProgress();
                Utils.showSnackBar(getView(),"Failed", true, requireContext());
                t.printStackTrace();
                Log.v("TAG_RESPONSE","FAIL \n"+t.getMessage());
            }
        });
    }
}