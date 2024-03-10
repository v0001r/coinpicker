package com.devanshi.tambola.coinpicker.utils;

import android.content.*;

import com.devanshi.tambola.coinpicker.models.*;

public class UserDetailsToPreference {

    public static void setDataToPreference(Context context, UserData userData){
        Preference preference = new Preference(context);
        preference.setUserId(userData.getId());
        preference.setLoginTimestamp(userData.getTimestamp());
        preference.setUserName(userData.getUsername());
        preference.setUserFirstName(userData.getFname());
        preference.setUserLastName(userData.getLname());
        preference.setUserRole(userData.getRole());
        preference.setUserEmail(userData.getEmail());
        preference.setNumberOfTickets(userData.getNoTickets());
        preference.setUserStatus(userData.getStatus());
        preference.setUserRefId(userData.getRefId());
    }
}
