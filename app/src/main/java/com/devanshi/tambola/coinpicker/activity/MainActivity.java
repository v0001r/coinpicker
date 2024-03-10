package com.devanshi.tambola.coinpicker.activity;

import android.annotation.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import androidx.databinding.*;
import androidx.fragment.app.*;
import androidx.recyclerview.widget.*;

import com.devanshi.tambola.coinpicker.*;
import com.devanshi.tambola.coinpicker.adapters.*;
import com.devanshi.tambola.coinpicker.customui.*;
import com.devanshi.tambola.coinpicker.databinding.*;
import com.devanshi.tambola.coinpicker.fragments.ui.dashboard.*;
import com.devanshi.tambola.coinpicker.fragments.ui.home.*;
import com.devanshi.tambola.coinpicker.fragments.ui.mycontest.*;
import com.devanshi.tambola.coinpicker.interfaces.*;
import com.devanshi.tambola.coinpicker.models.Menu;
import com.devanshi.tambola.coinpicker.utils.*;

import java.util.*;


public class MainActivity extends BaseActivity implements ItemClickListener {

    private ActivityMainBinding binding;
    private ArrayList<Menu> menuArrayList;
    private ItemClickListener itemClickListener;
    private MenuAdapter menuAdapter;
    private Fragment selectedFragment;
    private Preference preference;
    private boolean doubleBackToExitPressedOnce = false;
    private int selectedItem = 0;
    private String resumeFragment;


    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupUI(binding.rootView);
        preference = new Preference(getApplicationContext());
        itemClickListener = this;
        if (getIntent().hasExtra(getString(R.string.fragment_to_be_opened))) {
            resumeFragment = getIntent().getStringExtra(getString(R.string.fragment_to_be_opened));
        } else {
            resumeFragment = getString(R.string.menu_home);
        }
        initUI();
        toast = Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {

        menuArrayList = new ArrayList<>();

        Menu menu = new Menu();
        menu.setMenu_id(1);
        menu.setMenu_name(getString(R.string.menu_home));
        menu.setIcon_id(R.drawable.ic_home_active);
        menu.setIcon_path("ic_home");
        menu.setSelected(false);
        menuArrayList.add(menu);

        if (!preference.getUserRole().equalsIgnoreCase("GST")) {
            menu = new Menu();
            menu.setMenu_id(1);
            menu.setMenu_name(getString(R.string.menu_resume_game));
            menu.setIcon_id(R.drawable.ic_mycontest_inactive);
            menu.setIcon_path("ic_mycontest");
            menu.setSelected(false);
            menuArrayList.add(menu);

            menu = new Menu();
            menu.setMenu_id(1);
            menu.setMenu_name(getString(R.string.menu_change_password));
            menu.setIcon_id(R.drawable.ic_winner_inactive);
            menu.setIcon_path("ic_menu");
            menu.setSelected(false);
            menuArrayList.add(menu);
        }


        menu = new Menu();
        menu.setMenu_id(1);
        menu.setMenu_name(getString(R.string.menu_logout));
        menu.setIcon_id(R.drawable.ic_menu_inactive);
        menu.setIcon_path("turn_off");
        menu.setSelected(false);
        menuArrayList.add(menu);

        menuAdapter = new MenuAdapter(menuArrayList, itemClickListener, MainActivity.this);
        binding.menuRecyclerView.setAdapter(menuAdapter);

        SpanningLinearLayoutManager spanningLinearLayoutManager = new SpanningLinearLayoutManager(this, menuAdapter.getItemCount());
        spanningLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        spanningLinearLayoutManager.setScrollHorizontally(true);
        spanningLinearLayoutManager.setMaxItemsToShowInScreen(4);
        binding.menuRecyclerView.setLayoutManager(spanningLinearLayoutManager);

        binding.fragmentContainer.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft() {
                clickLeft();
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                clickRight();
                super.onSwipeRight();
            }
        });

        onItemClick(resumeFragment);
    }

    public void clickLeft() {
        if (selectedItem < menuArrayList.size()-1){
            onItemClick(menuArrayList.get(selectedItem+1).getMenu_name());
        }
    }

    public void clickRight() {
        if (selectedItem != 0){
            onItemClick(menuArrayList.get(selectedItem-1).getMenu_name());
        }
    }

    /**
     * Item Selected Color Change
     *
     * @param index selected item index
     */
    private void setSelected(int index) {
        if (menuArrayList != null && menuArrayList.size() > 0) {
            selectedItem = index;
            for (int i = 0; i < menuArrayList.size(); i++) {
                Menu menu = menuArrayList.get(i);
                if (i == index) {
                    menu.setSelected(true);
                } else {
                    menu.setSelected(false);
                }
                menuArrayList.set(i, menu);
            }

            if (menuAdapter != null) {
                menuAdapter.refreshList(menuArrayList);
            }
        }
    }


    @Override
    public void onItemClick(String menu_name) {
        if (menu_name.equals(getString(R.string.menu_home))){
            if (!(selectedFragment instanceof HomeFragment)) {
                openFragment(new HomeFragment(), false);
            }
            setSelected(0);
        } else if (menu_name.equals(getString(R.string.menu_resume_game))){
            if (preference.getLoginTimestamp().isEmpty()){
                startActivity(new Intent(MainActivity.this, LoginSignupActivity.class));
                finish();
            } else if (preference.getGameId() != 0){
                if (!(selectedFragment instanceof ResumeGameFragment)) {
                    openFragment(new ResumeGameFragment(), false);
                }
            }
        } else if (menu_name.equals(getString(R.string.menu_change_password))){
            if (preference.getLoginTimestamp().isEmpty()){
                startActivity(new Intent(MainActivity.this, LoginSignupActivity.class));
                finish();
            } else {
                if (!(selectedFragment instanceof ChangePasswordFragment)) {
                    openFragment(new ChangePasswordFragment(), false);
                }
                setSelected(2);
            }
        } else if (menu_name.equals(getString(R.string.menu_logout))){
            preference.clearAllPreferenceData();
            startActivity(new Intent(MainActivity.this, LoginSignupActivity.class));
            finish();
        }
    }

    /**
     * Open Fragment
     *
     * @param fragment Fragment whichever has to be opened
     * @param addToStack boolean whether fragment has to be added to back-stack
     */
    public void openFragment(Fragment fragment, boolean addToStack) {
        selectedFragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (addToStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();

    }

    public void setBottomNavigationMenu(boolean hidden){
        if (hidden){
            binding.menuRecyclerView.setVisibility(View.GONE);
        } else {
            binding.menuRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    Toast toast;
    @Override
    public void onBackPressed() {
        if (selectedFragment instanceof ResumeGameFragment){
            onItemClick(getString(R.string.menu_home));
        } else if(!doubleBackToExitPressedOnce){
            this.doubleBackToExitPressedOnce = true;
            toast.show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
        }else{
            toast.cancel();
            super.onBackPressed();
        }
    }
}
