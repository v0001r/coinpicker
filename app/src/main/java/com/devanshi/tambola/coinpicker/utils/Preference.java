package com.devanshi.tambola.coinpicker.utils;

import android.content.*;

import com.devanshi.tambola.coinpicker.*;


/**
 * Preference class to use SharedPreference class through out application. Use this class to store or retrieve data from SharedPreference.
 */
public class Preference {
    /**
     * Preference key for userId
     */
    private final String USER_NAME = "USER_NAME";
    private final String TIMESTAMP_LOGIN = "TIMESTAMP_LOGIN";
    private final String USER_FIRST_NAME = "USER_FIRST_NAME";
    private final String USER_LAST_NAME = "USER_LAST_NAME";
    private final String USER_ROLE = "USER_ROLE";
    private final String USER_EMAIL = "USER_EMAIL";
    private final String NUMBER_OF_TICKETS = "NUMBER_OF_TICKETS";
    private final String USER_STATUS = "USER_STATUS";
    private final String USER_REF_ID = "USER_REF_ID";
    private final String DECLARED_NUMBERS = "DECLARED_NUMBERS";
    private final String GAME_ID = "GAME_ID";
    private final String USER_ID = "USER_ID";

    /**
     * Shared Preference instance
     */
    private SharedPreferences sharedPreferences;


    public Preference(Context context) {
        sharedPreferences = (SharedPreferences) context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }


    /**
     * Returns the user name from Shared Preference file
     */
    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, "");
    }

    /**
     * Stores the user type into Shared Preference file if not stored
     */
    void setUserName(String userName) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    /**
     * Returns the login timestamp from Shared Preference file
     */
    public String getLoginTimestamp() {
        return sharedPreferences.getString(TIMESTAMP_LOGIN, "");
    }

    /**
     * Stores the login timestamp into Shared Preference file if not stored
     */
    void setLoginTimestamp(String loginTimestamp) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TIMESTAMP_LOGIN, loginTimestamp);
        editor.commit();
    }

    /**
     * Returns the User First Name from Shared Preference file
     */
    public String getUserFirstName() {
        return sharedPreferences.getString(USER_FIRST_NAME, "");
    }

    /**
     * Stores the User First Name into Shared Preference file
     */
    void setUserFirstName(String userFirstName) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_FIRST_NAME, userFirstName);
        editor.commit();
    }

    /**
     * Returns the User Last Name from Shared Preference file
     */
    public String getUserLastName() {
        return sharedPreferences.getString(USER_LAST_NAME, "");
    }

    /**
     * Stores the User First Name into Shared Preference file
     */
    void setUserLastName(String userLastName) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_LAST_NAME, userLastName);
        editor.commit();
    }

    /**
     * Returns the User role from Shared Preference file
     */
    public String getUserRole() {
        return sharedPreferences.getString(USER_ROLE, "");
    }

    /**
     * Stores the User role into Shared Preference file
     */
    void setUserRole(String userRole) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ROLE, userRole);
        editor.commit();
    }

    /**
     * Returns the User email from Shared Preference file
     */
    public String getUserEmail() {
        return sharedPreferences.getString(USER_EMAIL, "");
    }

    /**
     * Stores the User email into Shared Preference file
     */
    void setUserEmail(String userEmail) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL, userEmail);
        editor.commit();
    }

    /**
     * Returns the number of tickets from Shared Preference file
     */
    public int getNumberOfTickets() {
        return sharedPreferences.getInt(NUMBER_OF_TICKETS, 0);
    }

    /**
     * Stores the number of tickets into Shared Preference file
     */
    void setNumberOfTickets(int numberOfTickets) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUMBER_OF_TICKETS, numberOfTickets);
        editor.commit();
    }

    /**
     * Returns the User status from Shared Preference file
     */
    public String getUserStatus() {
        return sharedPreferences.getString(USER_STATUS, "");
    }

    /**
     * Stores the User status into Shared Preference file
     */
    void setUserStatus(String userStatus) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_STATUS, userStatus);
        editor.commit();
    }

    /**
     * Returns the userId from the Shared Preference file
     *
     * @return userId
     */
    public String getUserRefId() {
        return sharedPreferences.getString(USER_REF_ID, "");
    }

    /**
     * Stores the userId into Shared Preference file
     */
    public void setUserRefId(final String userId) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_REF_ID, userId);
        editor.commit();
    }

    /**
     * Returns the userId from the Shared Preference file
     *
     * @return userId
     */
    public String getDeclaredNumbers() {
        return sharedPreferences.getString(DECLARED_NUMBERS, "");
    }

    /**
     * Stores the userId into Shared Preference file
     */
    public void setDeclaredNumberString(final String declaredNumberString) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DECLARED_NUMBERS, declaredNumberString);
        editor.commit();
    }

    /**
     * Returns the userId from the Shared Preference file
     *
     * @return userId
     */
    public Integer getGameId() {
        return sharedPreferences.getInt(GAME_ID, 0);
    }

    /**
     * Stores the userId into Shared Preference file
     */
    public void setGameId(final Integer gameId) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(GAME_ID, gameId);
        editor.commit();
    }

    /**
     * Returns the userId from the Shared Preference file
     *
     * @return userId
     */
    public Integer getUserId() {
        return sharedPreferences.getInt(USER_ID, 0);
    }

    /**
     * Stores the userId into Shared Preference file
     */
    public void setUserId(final Integer userId) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, userId);
        editor.commit();
    }

    /**
     * Clears the all Shared Preference data
     */
    public void clearAllPreferenceData() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
