package com.developnerz.indie_indonesianenglishdictionary.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developnerz.indie_indonesianenglishdictionary.R;
import com.developnerz.indie_indonesianenglishdictionary.model.DictionaryModel;
import com.developnerz.indie_indonesianenglishdictionary.modules.AboutActivity;
import com.developnerz.indie_indonesianenglishdictionary.modules.main.fragment.EnFragment;
import com.developnerz.indie_indonesianenglishdictionary.modules.main.fragment.IdFragment;
import com.developnerz.indie_indonesianenglishdictionary.utils.DictionaryHelper;
import com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.TABLE_NAME_EN_ID;
import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.TABLE_NAME_ID_EN;

public class MainActivity extends AppCompatActivity {

    ArrayList<DictionaryModel> idAllModels;
    ArrayList<DictionaryModel> enAllModels;
    ArrayList<DictionaryModel> idmodels;
    ArrayList<DictionaryModel> enmodels;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tvResultFor)
    TextView resultText;
    @BindView(R.id.tvResultForText)
    TextView resultTextContent;
    @BindView(R.id.resultForContainer)
    ConstraintLayout resultTextContainer;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.imgCloseResultFor)
    ImageView imgCloseResultFor;

    private IdFragment idFragment;
    private EnFragment enFragment;

    private String tableName = TABLE_NAME_ID_EN;
    private DictionaryHelper dictionaryHelper;
    private String queryTextId;
    private String queryTextEn;
    private boolean doubleTapBackKeyPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        imgCloseResultFor.setOnClickListener(v -> {
            resultTextContainer.setVisibility(View.GONE);
            reInitializeFragment();
        });

        idFragment = new IdFragment();
        enFragment = new EnFragment();

        dictionaryHelper = new DictionaryHelper(this);

        setSupportActionBar(toolbar);
        setUpTabsWithViewPager();
        setUpMaterialSearch();
        initializeFragments();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tableName = TABLE_NAME_ID_EN;
                    if (TextUtils.isEmpty(queryTextId) && resultText.getVisibility() == View.VISIBLE) {
                        resultTextContainer.setVisibility(View.GONE);
                    } else {
                        searchView.setQuery(queryTextId, false);
                        showNoResult(idmodels);
                        resultTextContainer.setVisibility(View.VISIBLE);
                        resultTextContent.setText(queryTextId);
                    }
                } else {
                    if (TextUtils.isEmpty(queryTextEn) && resultText.getVisibility() == View.VISIBLE) {
                        resultTextContainer.setVisibility(View.GONE);
                    } else {
                        searchView.setQuery(queryTextEn, false);
                        showNoResult(enmodels);
                        resultTextContainer.setVisibility(View.VISIBLE);
                        resultTextContent.setText(queryTextEn);
                    }
                    tableName = DatabaseContract.TABLE_NAME_EN_ID;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void reInitializeFragment() {
        if (tabs.getSelectedTabPosition() == 0) {
            queryTextId = "";
            idFragment.addModel(idAllModels);
        } else {
            queryTextEn = "";
            enFragment.addModel(enAllModels);
        }
    }

    void initializeFragmentId() {
        idAllModels = dictionaryHelper.getAllData(TABLE_NAME_ID_EN);
        idFragment.initializeContext(this);
    }

    void initializeFragmentEn() {
        enAllModels = dictionaryHelper.getAllData(TABLE_NAME_EN_ID);
        enFragment.initializeContext(this);
    }

    void initializeFragments() {
        dictionaryHelper.open();
        initializeFragmentId();
        idFragment.initializeModel(idAllModels);

        initializeFragmentEn();
        enFragment.initializeModel(enAllModels);
        dictionaryHelper.close();
    }

    void showNoResult(ArrayList<DictionaryModel> models) {
        if (models == null || models.size() == 0) {
            resultText.setText(getString(R.string.no_result_for));
        } else {
            resultText.setText(getString(R.string.result_for));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem itemSearch = menu.findItem(R.id.action_search);
        MenuItem itemAbout = menu.findItem(R.id.action_about);

        itemAbout.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        });

        searchView.setMenuItem(itemSearch);
        searchView.setHint(getString(R.string.cari_kata));

        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleTapBackKeyPressedOnce) {
            super.onBackPressed();
            return;
        }
        if (searchView.isSearchOpen()) {
            if (searchView.isSearchOpen()) {
                searchView.closeSearch();
            }
        } else if (viewPager.getCurrentItem() == 0) {
            this.doubleTapBackKeyPressedOnce = true;
            Toast.makeText(this, R.string.press_back_to_exit, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleTapBackKeyPressedOnce = false;
                }
            }, 1500);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    void setUpMaterialSearch() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dictionaryHelper.open();
                resultTextContainer.setVisibility(View.VISIBLE);
                switch (tableName) {
                    case TABLE_NAME_ID_EN:
                        queryTextId = query;
                        if (tabs.getSelectedTabPosition() == 0) {
                            resultTextContent.setText(queryTextId);
                        }
                        idmodels = dictionaryHelper.getDataByKeyword(TABLE_NAME_ID_EN, query);
                        idFragment.addModel(idmodels);
                        showNoResult(idmodels);
                        break;
                    case TABLE_NAME_EN_ID:
                        queryTextEn = query;
                        if (tabs.getSelectedTabPosition() == 1) {
                            resultTextContent.setText(queryTextEn);
                        }
                        enmodels = dictionaryHelper.getDataByKeyword(TABLE_NAME_EN_ID, query);
                        enFragment.addModel(enmodels);
                        showNoResult(enmodels);
                        break;
                }
                dictionaryHelper.close();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                if (tabs.getSelectedTabPosition() == 0 && !TextUtils.isEmpty(queryTextId)) {
                    searchView.setQuery(queryTextId, false);
                } else if (tabs.getSelectedTabPosition() == 1 && !TextUtils.isEmpty(queryTextEn)) {
                    searchView.setQuery(queryTextEn, false);
                }
            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    void setUpTabsWithViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(idFragment);
        adapter.addFragment(enFragment);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setText(R.string.id_en);
        tabs.getTabAt(1).setText(R.string.en_id);
        viewPager.setCurrentItem(0);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }

}
