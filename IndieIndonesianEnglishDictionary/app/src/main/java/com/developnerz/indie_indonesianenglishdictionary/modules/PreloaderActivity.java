package com.developnerz.indie_indonesianenglishdictionary.modules;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.developnerz.indie_indonesianenglishdictionary.R;
import com.developnerz.indie_indonesianenglishdictionary.model.DictionaryModel;
import com.developnerz.indie_indonesianenglishdictionary.modules.main.MainActivity;
import com.developnerz.indie_indonesianenglishdictionary.utils.DictionaryHelper;
import com.developnerz.indie_indonesianenglishdictionary.utils.SharedPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.TABLE_NAME_EN_ID;
import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.TABLE_NAME_ID_EN;

public class PreloaderActivity extends AppCompatActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);
        ButterKnife.bind(this);

        new LoadData().execute();
    }

    public ArrayList<DictionaryModel> preLoadRaw(String check) {
        ArrayList<DictionaryModel> models = new ArrayList<>();
        String line;
        BufferedReader reader;
        InputStream raw;

        try {
            Resources resources = getResources();

            if (TextUtils.equals(check, "in")) {
                raw = resources.openRawResource(R.raw.indonesia_english);
            } else {
                raw = resources.openRawResource(R.raw.english_indonesia);
            }

            reader = new BufferedReader(new InputStreamReader(raw));

            int count = 0;

            do {
                line = reader.readLine();
                String[] splitter = line.split("\t");

                DictionaryModel model;

                model = new DictionaryModel(splitter[0], splitter[1]);
                models.add(model);
                count++;
            } while (line != null);

        } catch (Exception e) {

        }

        return models;
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        DictionaryHelper dictionaryHelper;
        SharedPreference sharedPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = sharedPreference.getFirstRun();

            if (firstRun) {
                ArrayList<DictionaryModel> models = preLoadRaw("in");
                dictionaryHelper.open();
                progress = 5.0;
                publishProgress((int) progress);
                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / models.size();

                dictionaryHelper.beginTransaction();

                try {
                    for (DictionaryModel dictionaryModel : models) {
                        dictionaryHelper.insertTransaction(TABLE_NAME_ID_EN, dictionaryModel);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    models = preLoadRaw("en");
                    for (DictionaryModel dictionaryModel : models) {
                        dictionaryHelper.insertTransaction(TABLE_NAME_EN_ID, dictionaryModel);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    dictionaryHelper.setTransactionSuccess();
                } catch (Exception e) {

                }

                dictionaryHelper.endTransaction();

                dictionaryHelper.close();

                sharedPreference.setFirstRun(false);
                publishProgress((int) maxprogress);

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(PreloaderActivity.this);
            sharedPreference = new SharedPreference(PreloaderActivity.this);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(PreloaderActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
