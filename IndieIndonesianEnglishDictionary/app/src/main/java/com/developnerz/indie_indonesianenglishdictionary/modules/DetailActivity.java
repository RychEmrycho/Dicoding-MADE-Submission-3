package com.developnerz.indie_indonesianenglishdictionary.modules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.developnerz.indie_indonesianenglishdictionary.R;
import com.developnerz.indie_indonesianenglishdictionary.model.DictionaryModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_MODEL = "extra_model";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvSource)
    TextView keyword;
    @BindView(R.id.tvTranslation)
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        DictionaryModel model = getIntent().getParcelableExtra(EXTRA_MODEL);

        keyword.setText(model.getKeyword());
        desc.setText(model.getDesc());
    }
}
