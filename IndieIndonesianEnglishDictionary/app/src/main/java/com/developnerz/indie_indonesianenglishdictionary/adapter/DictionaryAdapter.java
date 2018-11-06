package com.developnerz.indie_indonesianenglishdictionary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developnerz.indie_indonesianenglishdictionary.R;
import com.developnerz.indie_indonesianenglishdictionary.model.DictionaryModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rych Emrycho on 8/31/2018 at 1:28 AM.
 * Updated by Rych Emrycho on 8/31/2018 at 1:28 AM.
 */
public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {

    private ArrayList<DictionaryModel> mData = new ArrayList<>();
    private OnClickListener onClickListener;

    public DictionaryAdapter(OnClickListener listener, ArrayList<DictionaryModel> mData) {
        onClickListener = listener;
        this.mData = mData;
    }

    public void replaceData(ArrayList<DictionaryModel> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void OnItemClickListener(DictionaryModel model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvA.setText(mData.get(i).getKeyword());
        viewHolder.layout.setOnClickListener((view) -> {
            onClickListener.OnItemClickListener(mData.get(i));
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_a) TextView tvA;
        @BindView(R.id.itemContainer) LinearLayout layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
