package com.devanshi.tambola.coinpicker.fragments.ui.home;

import android.annotation.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.*;

import com.devanshi.tambola.coinpicker.*;
import com.devanshi.tambola.coinpicker.activity.*;
import com.devanshi.tambola.coinpicker.apis.*;
import com.devanshi.tambola.coinpicker.models.*;
import com.devanshi.tambola.coinpicker.utils.Utils;
import com.devanshi.tambola.coinpicker.utils.*;

import org.jetbrains.annotations.*;

import retrofit2.*;

public class HomeFragment extends Fragment {

    private View rootView = null;
    private Button startGame, resumeGame;
    private Preference preference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        preference = new Preference(requireActivity());
        initUI();
        return rootView;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {
        startGame = rootView.findViewById(R.id.startGame);
        resumeGame = rootView.findViewById(R.id.resumeGame);
        if (preference.getGameId() != 0 && !preference.getUserRole().equalsIgnoreCase("GST")){
            resumeGame.setVisibility(View.VISIBLE);
        } else {
            resumeGame.setVisibility(View.GONE);
        }

        startGame.setOnClickListener(v->{
            startNewGame();
            preference.setDeclaredNumberString("");
        });

        resumeGame.setOnClickListener(v->{
            startGameFragment();
        });
    }

    private void startNewGame(){
        Utils.showProgress(requireContext());

        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<StartGameModel> call;
        if(preference.getUserRole().equalsIgnoreCase("GST")){
            call = apiInterface.addGameApi("");
        } else {
            call = apiInterface.addGameApi(String.valueOf(preference.getUserId()));
        }

        call.enqueue(new Callback<StartGameModel>() {
            @Override
            public void onResponse(@NotNull Call<StartGameModel> call, @NotNull Response<StartGameModel> response) {
                Utils.hideProgress();
                if (response.body()!=null){
                    Log.e(Utils.getTag(), "onResponse: "+response.body().toString());
                    StartGameModel startGameModel = response.body();
                    if (startGameModel.getStatus()){
                        GameData data = startGameModel.getData();
                        preference.setGameId(data.getGameId());
                        startGameFragment();
                    } else {
                        Utils.showSnackBar(getView(), startGameModel.getMessage(), true, requireContext());
                    }
                    Log.v("TAG_RESPONSE","SUCCESS");
                }
            }

            @Override
            public void onFailure(@NotNull Call<StartGameModel> call, @NotNull Throwable t) {
                Utils.hideProgress();
                Utils.showSnackBar(getView(),"Failed", true, requireContext());
                t.printStackTrace();
                Log.v("TAG_RESPONSE","FAIL \n"+t.getMessage());
            }
        });
    }

    private void startGameFragment(){
        ((MainActivity)requireActivity()).onItemClick(getString(R.string.menu_resume_game));
    }
}
