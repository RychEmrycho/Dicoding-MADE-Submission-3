package com.developnerz.indie_indonesianenglishdictionary.modules.main.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developnerz.indie_indonesianenglishdictionary.R;
import com.developnerz.indie_indonesianenglishdictionary.adapter.DictionaryAdapter;
import com.developnerz.indie_indonesianenglishdictionary.modules.DetailActivity;
import com.developnerz.indie_indonesianenglishdictionary.model.DictionaryModel;

import java.util.ArrayList;

public class IdFragment extends Fragment implements DictionaryAdapter.OnClickListener {

    private DictionaryAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<DictionaryModel> dictionaryModels;
    private Context context;

    public IdFragment() {
        // Required empty public constructor
    }

    public void initializeContext(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_id, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new DictionaryAdapter(this, dictionaryModels);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void initializeModel(ArrayList<DictionaryModel> dictionaryModels){
        this.dictionaryModels = dictionaryModels;
    }

    public void addModel(ArrayList<DictionaryModel> models){
        adapter.replaceData(models);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnItemClickListener(DictionaryModel model) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MODEL, model);
        startActivity(intent);
    }
}
