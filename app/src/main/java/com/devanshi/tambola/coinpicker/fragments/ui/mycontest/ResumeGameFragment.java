package com.devanshi.tambola.coinpicker.fragments.ui.mycontest;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.speech.tts.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.*;
import androidx.core.content.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import com.devanshi.tambola.coinpicker.R;
import com.devanshi.tambola.coinpicker.activity.*;
import com.devanshi.tambola.coinpicker.adapters.*;
import com.devanshi.tambola.coinpicker.apis.*;
import com.devanshi.tambola.coinpicker.models.*;
import com.devanshi.tambola.coinpicker.utils.Utils;
import com.devanshi.tambola.coinpicker.utils.*;

import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

import retrofit2.*;


public class ResumeGameFragment extends Fragment implements TextToSpeech.OnInitListener{

    Switch boardSwitch, soundSwitch, autoSwitch;
    View bgnum1, bgnum2, bgnum3, bgnum4, bgnum5, bgcurrentDeclaredNumber, bgCurrentDeclaredNumberBig;
    TextView num1, num2, num3, num4, num5, totalDeclaredNumbers, currentDeclaredNumber, currentDeclaredNumberBig, txtClick, tvAuto, txtClickBig;
    ConstraintLayout clBig, clSmall, bottomCl;
    RecyclerView boardRecyclerView;
    CustomGrid adapter;
    ArrayList<Integer> declaredNumbers;
    static TextToSpeech t1;
    private int MY_DATA_CHECK_CODE = 0;
    private Preference preference;
    LinearLayout llTimer;
    Spinner spinner;
    List<Integer> timerList;
    Button btnShare;

    private ArrayList<TicketNumberObj> numbers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_contest, container, false);
        ((MainActivity)requireActivity()).setBottomNavigationMenu(true);
        preference = new Preference(requireContext());
        clBig = root.findViewById(R.id.clBig);
        clSmall = root.findViewById(R.id.clSmall);
        boardSwitch = root.findViewById(R.id.boardSwitch);
        soundSwitch = root.findViewById(R.id.soundSwitch);
        autoSwitch = root.findViewById(R.id.autoSwitch);
        tvAuto = root.findViewById(R.id.tvAuto);
        num1 = root.findViewById(R.id.num1);
        num2 = root.findViewById(R.id.num2);
        num3 = root.findViewById(R.id.num3);
        num4 = root.findViewById(R.id.num4);
        num5 = root.findViewById(R.id.num5);
        bgnum1 = root.findViewById(R.id.bgnum1);
        bgnum2 = root.findViewById(R.id.bgnum2);
        bgnum3 = root.findViewById(R.id.bgnum3);
        bgnum4 = root.findViewById(R.id.bgnum4);
        bgnum5 = root.findViewById(R.id.bgnum5);
        bgcurrentDeclaredNumber = root.findViewById(R.id.bgCurrentDeclaredNumber);
        bgCurrentDeclaredNumberBig = root.findViewById(R.id.bgCurrentDeclaredNumberBig);
        totalDeclaredNumbers = root.findViewById(R.id.totalDeclaredNumbers);
        currentDeclaredNumber = root.findViewById(R.id.currentDeclaredNumber);
        currentDeclaredNumberBig = root.findViewById(R.id.currentDeclaredNumberBig);
        txtClick = root.findViewById(R.id.txtClick);
        txtClickBig = root.findViewById(R.id.txtClickBig);
        boardRecyclerView = root.findViewById(R.id.boardRecyclerView);
        spinner = root.findViewById(R.id.timerSpinner);
        llTimer = root.findViewById(R.id.llTimer);
        bottomCl = root.findViewById(R.id.bottom_cl);
        btnShare = root.findViewById(R.id.btnShare);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Niraj", "onViewCreated: ");
        boardSwitch.setChecked(true);
        declaredNumbers = new ArrayList<>();
        numbers = new ArrayList<>();
        numbers.clear();
        String declaredNumbersString = preference.getDeclaredNumbers();
        if (declaredNumbersString.contains(",")){
            String[] splits = declaredNumbersString.split(", ");
            for (String numberString: splits){
                declaredNumbers.add(Integer.parseInt(numberString));
            }
        }
        TicketNumberObj ticketNumberObj;
        for (int i = 1; i < 91; i++) {
            ticketNumberObj = new TicketNumberObj();
            ticketNumberObj.setValue(String.valueOf(i));
            ticketNumberObj.setId(String.valueOf(i));
            if (declaredNumbers.contains(i)) {
                ticketNumberObj.setMetaChecked(1);
            } else {
                ticketNumberObj.setMetaChecked(0);
            }
            numbers.add(ticketNumberObj);
        }
        boardRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 10));
        adapter = new CustomGrid(requireContext(), numbers);
        boardRecyclerView.setAdapter(adapter);
        if (declaredNumbers.size()>1){
            setNumberFromPreference(declaredNumbers.get(declaredNumbers.size()-1));
        }

        timerList = new ArrayList<>();
        timerList.add(10);
        timerList.add(15);
        timerList.add(20);
        timerList.add(25);
        timerList.add(30);
        timerList.add(35);
        timerList.add(40);
        timerList.add(45);
        timerList.add(50);
        timerList.add(55);
        timerList.add(60);

        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(requireContext(), R.layout.color_spinner_layout, timerList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);


        initListeners();
    }

    private void initListeners() {
        boardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /**
             * Called when the checked state of a compound button has changed.
             *
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clBig.setVisibility(View.GONE);
                    clSmall.setVisibility(View.VISIBLE);
                } else {
                    clBig.setVisibility(View.VISIBLE);
                    clSmall.setVisibility(View.GONE);
                }
            }
        });

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /**
             * Called when the checked state of a compound button has changed.
             *
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkTTS();
                } else {
                    stopTTS();
                }
            }
        });

        autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /**
             * Called when the checked state of a compound button has changed.
             *
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  The new checked state of buttonView.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (declaredNumbers.size() != 90) {
                        startAutoNumberCalling();
                        llTimer.setVisibility(View.VISIBLE);
                        txtClick.setText("Auto timer is running...");
                        txtClickBig.setText("Auto timer is running...");
                    } else {
                        autoSwitch.setChecked(false);
                        llTimer.setVisibility(View.GONE);
                        txtClick.setText("Game over...");
                        txtClickBig.setText("Game over...");
                    }
                } else {
                    stopAutoNumberCalling();
                    llTimer.setVisibility(View.GONE);
                    txtClick.setText("Touch to pick next coin!");
                    txtClickBig.setText("Touch to pick next coin!");
                }
            }
        });

        currentDeclaredNumber.setOnClickListener(v -> {
            if (!autoSwitch.isChecked() && declaredNumbers.size() != 90) {
                getDeclaredNumber();
            }
        });

        currentDeclaredNumberBig.setOnClickListener(v -> {
            if (!autoSwitch.isChecked() && declaredNumbers.size() != 90) {
                getDeclaredNumber();
            }
        });

        tvAuto.setOnClickListener(v -> {
            if (preference.getUserRole().equalsIgnoreCase("adm") && declaredNumbers.size() != 90) {
                autoSwitch.setChecked(false);
                llTimer.setVisibility(View.GONE);
                stopAutoNumberCalling();
                showNumberDeclaringDialogByAdmin();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                delay = timerList.get(position) * 1000;
                stopAutoNumberCalling();
                startAutoNumberCalling();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                delay = timerList.get(0) * 1000;
                stopAutoNumberCalling();
                startAutoNumberCalling();
            }
        });

        btnShare.setOnClickListener(v -> {
            Bitmap returnedBitmap = Bitmap.createBitmap(bottomCl.getMeasuredWidth(), bottomCl.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(returnedBitmap);
            Drawable bgDrawable = bottomCl.getBackground();
            if (bgDrawable != null){
                bgDrawable.draw(canvas);
            } else {
                canvas.drawColor(Color.WHITE);
            }
            bottomCl.draw(canvas);

            File pictureFileDir = new File(requireActivity().getCacheDir(),"images");
            if (!pictureFileDir.exists()){
                boolean isDirectoryCreated = pictureFileDir.mkdirs();
                if (!isDirectoryCreated){
                    Log.i("TCP ResumeGame", "initListeners: Can't create directory");
                    return;
                }
            }
            File pictureFile = new File(pictureFileDir, "declared.png");
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                returnedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uri imageUri = FileProvider.getUriForFile(requireContext(), "com.devanshi.tambola.coinpicker.provider",
                   pictureFile);
            Intent sharingIntent = new Intent(Intent.ACTION_SEND).setData(imageUri);
            List<ResolveInfo> resolveInfoList = requireActivity().getPackageManager().queryIntentActivities(sharingIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resolveInfoList){
                String packageName = resolveInfo.activityInfo.packageName;
                requireActivity().grantUriPermission(packageName, imageUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            }
            if (declaredNumbers.size() > 0) {
                sharingIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(declaredNumbers.get(declaredNumbers.size()-1)).concat(" is the last declared number"));
            } else {
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "No numbers declared");
            }
            sharingIntent.setType("image/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(sharingIntent,"Share using"));
        });
    }

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10 * 1000;
    boolean firstTime = true;

    private void startAutoNumberCalling() {
        /*if (firstTime) {
            getDeclaredNumber();
            firstTime = false;
        }*/
        handler.postDelayed(runnable = () -> {
            getDeclaredNumber();
            handler.postDelayed(runnable, delay);
        }, delay);
    }

    TicketNumberObj ticketNumberObj;

    private void getDeclaredNumber(){
        Utils.showProgress(requireContext());
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<DeclaredNumberModel> call = apiInterface.getDeclaredNumbersApi(preference.getGameId(), "auto");

        call.enqueue(new Callback<DeclaredNumberModel>() {
            @Override
            public void onResponse(@NotNull Call<DeclaredNumberModel> call, @NotNull Response<DeclaredNumberModel> response) {
                Utils.hideProgress();
                if (response.body()!=null){
                    Log.e(Utils.getTag(), "onResponse: "+response.body().toString());
                    DeclaredNumberModel declaredNumberModel = response.body();
                    if (declaredNumberModel.getStatus()){
                        DeclaredNumberData declaredNumberData =declaredNumberModel.getData();
                        setNumberSelected(Integer.parseInt(declaredNumberData.getCurrent()));
                    } else {
                        Utils.showSnackBar(getView(), declaredNumberModel.getMessage(), true, requireContext());
                    }
                    Log.v("TAG_RESPONSE","SUCCESS");
                }
            }

            @Override
            public void onFailure(@NotNull Call<DeclaredNumberModel> call, @NotNull Throwable t) {
                Utils.hideProgress();
                Utils.showSnackBar(getView(),"Failed", true, requireContext());
                t.printStackTrace();
                Log.v("TAG_RESPONSE","FAIL \n"+t.getMessage());
            }
        });
    }

    private void callNumberDeclaredByAdmin(String numberByAdmin){
        Utils.showProgress(requireContext());
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<DeclaredNumberModel> call = apiInterface.numberByAdminApi(preference.getGameId(),"manual", numberByAdmin);

        call.enqueue(new Callback<DeclaredNumberModel>() {
            @Override
            public void onResponse(@NotNull Call<DeclaredNumberModel> call, @NotNull Response<DeclaredNumberModel> response) {
                Utils.hideProgress();
                if (response.body()!=null){
                    Log.e(Utils.getTag(), "onResponse: "+response.body().toString());
                    DeclaredNumberModel declaredNumberModel = response.body();
                    if (declaredNumberModel.getStatus()){
                        DeclaredNumberData declaredNumberData =declaredNumberModel.getData();
                        setNumberSelected(Integer.parseInt(declaredNumberData.getCurrent()));
                    } else {
                        Utils.showSnackBar(getView(), declaredNumberModel.getMessage(), true, requireContext());
                    }
                    Log.v("TAG_RESPONSE","SUCCESS");
                }
            }

            @Override
            public void onFailure(@NotNull Call<DeclaredNumberModel> call, @NotNull Throwable t) {
                Utils.hideProgress();
                Utils.showSnackBar(getView(),"Failed", true, requireContext());
                t.printStackTrace();
                Log.v("TAG_RESPONSE","FAIL \n"+t.getMessage());
            }
        });
    }

    private void setNumberFromPreference(int num){
        if (num < 1 || num > 90){
            if (t1 != null){
                stringToSpeak("Invalid Number");
                return;
            }
        }
        totalDeclaredNumbers.setText(String.valueOf(declaredNumbers.size()).concat("/90"));
        currentDeclaredNumber.setText(String.valueOf(num));
        currentDeclaredNumberBig.setText(String.valueOf(num));
        bgcurrentDeclaredNumber.setVisibility(View.VISIBLE);
        bgCurrentDeclaredNumberBig.setVisibility(View.VISIBLE);
        ticketNumberObj = new TicketNumberObj();
        ticketNumberObj.setId(String.valueOf(num));
        ticketNumberObj.setValue(String.valueOf(num));
        ticketNumberObj.setMetaChecked(1);
        adapter.refreshItem(ticketNumberObj, num - 1);
        if (declaredNumbers.size() == 2){
            num1.setText(String.valueOf(declaredNumbers.get(0)));
            bgnum1.setVisibility(View.VISIBLE);
        } else if (declaredNumbers.size() == 3){
            num1.setText(String.valueOf(declaredNumbers.get(1)));
            num2.setText(String.valueOf(declaredNumbers.get(0)));
            bgnum1.setVisibility(View.VISIBLE);
            bgnum2.setVisibility(View.VISIBLE);
        } else if (declaredNumbers.size() == 4){
            num1.setText(String.valueOf(declaredNumbers.get(2)));
            num2.setText(String.valueOf(declaredNumbers.get(1)));
            num3.setText(String.valueOf(declaredNumbers.get(0)));
            bgnum1.setVisibility(View.VISIBLE);
            bgnum2.setVisibility(View.VISIBLE);
            bgnum3.setVisibility(View.VISIBLE);
        } else if (declaredNumbers.size() == 5){
            num1.setText(String.valueOf(declaredNumbers.get(3)));
            num2.setText(String.valueOf(declaredNumbers.get(2)));
            num3.setText(String.valueOf(declaredNumbers.get(1)));
            num4.setText(String.valueOf(declaredNumbers.get(0)));
            bgnum1.setVisibility(View.VISIBLE);
            bgnum2.setVisibility(View.VISIBLE);
            bgnum3.setVisibility(View.VISIBLE);
            bgnum4.setVisibility(View.VISIBLE);
        }  else if (declaredNumbers.size() >= 6){
            num1.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-2)));
            num2.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-3)));
            num3.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-4)));
            num4.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-5)));
            num5.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-6)));
            bgnum1.setVisibility(View.VISIBLE);
            bgnum2.setVisibility(View.VISIBLE);
            bgnum3.setVisibility(View.VISIBLE);
            bgnum4.setVisibility(View.VISIBLE);
            bgnum5.setVisibility(View.VISIBLE);
        }
        if (declaredNumbers.size()==90){
            if (t1 != null) {
                new Handler().postDelayed(() -> {
                    stringToSpeak("Game Over Thank you");
                    autoSwitch.setChecked(false);
                }, 2000);
                new Handler().postDelayed(this::stopTTS, 3000);
            }
            txtClick.setText("Game over...");
            txtClickBig.setText("Game over...");
            stopAutoNumberCalling();
        }
    }

    private void setNumberSelected(int num) {
        if (num < 1 || num > 90){
            if (t1 != null){
                stringToSpeak("Invalid Number");
                return;
            }
        }
        if (!declaredNumbers.contains(num)) {
            declaredNumbers.add(num);
            String arrayString = Arrays.toString(declaredNumbers.toArray());
            String stringForPreference = arrayString.substring(1, arrayString.length()-1);
            preference.setDeclaredNumberString(stringForPreference);
        } else {
            if (t1 != null){
                stringToSpeak("Number is already declared");
                return;
            }
        }
        totalDeclaredNumbers.setText(String.valueOf(declaredNumbers.size()).concat("/90"));
        currentDeclaredNumber.setText(String.valueOf(num));
        currentDeclaredNumberBig.setText(String.valueOf(num));
        bgcurrentDeclaredNumber.setVisibility(View.VISIBLE);
        bgCurrentDeclaredNumberBig.setVisibility(View.VISIBLE);
        ticketNumberObj = new TicketNumberObj();
        ticketNumberObj.setId(String.valueOf(num));
        ticketNumberObj.setValue(String.valueOf(num));
        ticketNumberObj.setMetaChecked(1);
        adapter.refreshItem(ticketNumberObj, num - 1);
        if (declaredNumbers.size() == 2){
            num1.setText(String.valueOf(declaredNumbers.get(0)));
            bgnum1.setVisibility(View.VISIBLE);
        } else if (declaredNumbers.size() == 3){
            num1.setText(String.valueOf(declaredNumbers.get(1)));
            num2.setText(String.valueOf(declaredNumbers.get(0)));
            bgnum1.setVisibility(View.VISIBLE);
            bgnum2.setVisibility(View.VISIBLE);
        } else if (declaredNumbers.size() == 4){
            num1.setText(String.valueOf(declaredNumbers.get(2)));
            num2.setText(String.valueOf(declaredNumbers.get(1)));
            num3.setText(String.valueOf(declaredNumbers.get(0)));
            bgnum1.setVisibility(View.VISIBLE);
            bgnum2.setVisibility(View.VISIBLE);
            bgnum3.setVisibility(View.VISIBLE);
        } else if (declaredNumbers.size() == 5){
            num1.setText(String.valueOf(declaredNumbers.get(3)));
            num2.setText(String.valueOf(declaredNumbers.get(2)));
            num3.setText(String.valueOf(declaredNumbers.get(1)));
            num4.setText(String.valueOf(declaredNumbers.get(0)));
            bgnum1.setVisibility(View.VISIBLE);
            bgnum2.setVisibility(View.VISIBLE);
            bgnum3.setVisibility(View.VISIBLE);
            bgnum4.setVisibility(View.VISIBLE);
        }  else if (declaredNumbers.size() >= 6){
            num1.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-2)));
            num2.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-3)));
            num3.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-4)));
            num4.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-5)));
            num5.setText(String.valueOf(declaredNumbers.get(declaredNumbers.size()-6)));
            bgnum1.setVisibility(View.VISIBLE);
            bgnum2.setVisibility(View.VISIBLE);
            bgnum3.setVisibility(View.VISIBLE);
            bgnum4.setVisibility(View.VISIBLE);
            bgnum5.setVisibility(View.VISIBLE);
        }
        if (t1 != null){
            if (num<10) {
                stringToSpeak("Only number "+num);
            } else {
                String toSpeak = String.valueOf((int)(num/10)).concat(" "+Utils.convertIntoWords(num%10, "en", "US")).concat(" "+Utils.convertIntoWords(num, "en", "US"));
                stringToSpeak(toSpeak);
            }
        }
        if (declaredNumbers.size()==90){
            if (t1 != null) {
                new Handler().postDelayed(() -> {
                    stringToSpeak("Game Over Thank you");
                    autoSwitch.setChecked(false);
                }, 2000);
                new Handler().postDelayed(this::stopTTS, 3000);
            }
            txtClick.setText("Game over...");
            txtClickBig.setText("Game over...");
            stopAutoNumberCalling();
        }
    }

    private void stringToSpeak(String string) {
        if (t1 != null){
            t1.speak(string, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void stopAutoNumberCalling(){
        if (runnable != null && handler != null) {
            Log.d(Utils.getTag(), "onBackPressed: handler removed");
            handler.removeCallbacks(runnable);
            runnable = null;
        }
    }

    private void stopTTS(){
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        soundSwitch.setChecked(false);
    }

    void checkTTS() {
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                t1 = new TextToSpeech(requireContext(), this);
            } else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    /**
     * Called to signal the completion of the TextToSpeech engine initialization.
     *
     * @param status {@link TextToSpeech#SUCCESS} or {@link TextToSpeech#ERROR}.
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            if (t1.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                t1.setLanguage(Locale.US);
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(requireContext(), "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    private void showNumberDeclaringDialogByAdmin() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.manual_number_dialog_view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        EditText edtNumber = dialog.findViewById(R.id.edtNumber);
        TextView buttonOk = dialog.findViewById(R.id.buttonOk);
        TextView error = dialog.findViewById(R.id.error);
        TextView buttonCancel = dialog.findViewById(R.id.buttonCancel);

        edtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(edtNumber.getText().toString()) && Integer.parseInt(edtNumber.getText().toString()) == 0) {
                    error.setVisibility(View.VISIBLE);
                } else {
                    error.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonOk.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(edtNumber.getText().toString()) && Integer.parseInt(edtNumber.getText().toString()) > 0) {
                callNumberDeclaredByAdmin(edtNumber.getText().toString());
                dialog.dismiss();
            } else {
                error.setVisibility(View.VISIBLE);
            }
        });

        buttonCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)requireActivity()).onItemClick(requireContext().getString(R.string.menu_home));
        ((MainActivity)requireActivity()).setBottomNavigationMenu(false);
        stopTTS();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (autoSwitch.isChecked()) {
            stopAutoNumberCalling();
            startAutoNumberCalling();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoNumberCalling();
    }
}
