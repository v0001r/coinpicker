package com.devanshi.tambola.coinpicker.utils;

import android.annotation.*;
import android.content.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;

import androidx.core.content.*;

import com.google.android.material.snackbar.*;
import com.ibm.icu.text.*;
import com.devanshi.tambola.coinpicker.*;
import com.devanshi.tambola.coinpicker.customui.*;

import java.util.*;

public class Utils {

    /**
     * Call snack bar which will disapper in 3-5 seconds
     *
     * @return Snack bar
     */
    public static Snackbar showSnackBar(final View view, String message, final boolean isError, final Context context) {
        if (view == null) {
            return null;
        }

        if (isError){
            Log.e(Utils.getTag(), "showSnackBar: error occurred");
        }

        if (TextUtils.isEmpty(message)) {
            message = "Error!!!";
        }

        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        final View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(ContextCompat.getColor(context, isError ? R.color.colorRed : R.color.colorOrangeLight));
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_game_recycle_detail_ticket_price_text));
//        final TextView textView = snackBarView.findViewById(R.id.snackbar_text);
//        textView.setMaxLines(5);
        snackbar.show();
        return snackbar;
    }

    /**
     * Hide the soft keyboard from screen for edit text only
     *
     * @param context context of current visible activity
     * @param view    clicked view
     */
    public static void hideSoftKeyBoard(final Context context, final View view) {
        try {
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1000L, "k");
        suffixes.put(1000000L, "M");
    }

    public static String formatDigitToUnitValues(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatDigitToUnitValues(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatDigitToUnitValues(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        assert e != null;
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100;
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static Toast customToast(Context context, String message){
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(context , message, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(30);
        toastTV.setTextAppearance(R.style.game_recycler_heading_textview_style_black_color);
        return toast;
    }

    private static CustomDialog customDialog;

    public static void showProgress(Context context){
        customDialog = CustomDialog.getInstance();
        customDialog.showProgress(context, false);
    }

    public static void hideProgress(){
        if (customDialog != null) {
            customDialog.hideProgress();
            customDialog = null;
        }
    }

    public static String capitalizeFirstLetter(String stringWithUnderscores){
        stringWithUnderscores = stringWithUnderscores.replace('_', ' ');
        if (TextUtils.isEmpty(stringWithUnderscores)) return "";
        String[] temp = stringWithUnderscores.split(" ");
        String claimString = temp[0].substring(0, 1).toUpperCase().concat(temp[0].substring(1)).concat(" ");
        for (int index = 1; index < temp.length; index++){
            claimString = claimString.concat(temp[index].substring(0, 1).toUpperCase()).concat(temp[index].substring(1)).concat(" ");
        }
        return claimString.substring(0, claimString.length()-1);
    }

    public static String getTag(){
        return " ";
//        return BuildConfig.APPLICATION_ID;
    }

    public static float dpTopixel(Context c, float dp) {
        float density = c.getResources().getDisplayMetrics().density;
        float pixel = dp * density;
        return pixel;
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static String convertIntoWords(int str,String language,String Country) {
        Locale local = new Locale(language, Country);
        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(local, RuleBasedNumberFormat.SPELLOUT);
        return ruleBasedNumberFormat.format(str);
    }
}
