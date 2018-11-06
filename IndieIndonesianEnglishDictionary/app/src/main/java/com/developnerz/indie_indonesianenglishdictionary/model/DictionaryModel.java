package com.developnerz.indie_indonesianenglishdictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rych Emrycho on 8/31/2018 at 12:47 AM.
 * Updated by Rych Emrycho on 8/31/2018 at 12:47 AM.
 */
public class DictionaryModel implements Parcelable {
    private int id;
    private String keyword;
    private String desc;

    public DictionaryModel() {
    }

    public DictionaryModel(String keyword, String desc) {
        this.keyword = keyword;
        this.desc = desc;
    }

    public DictionaryModel(int id, String keyword, String desc) {
        this.id = id;
        this.keyword = keyword;
        this.desc = desc;
    }

    protected DictionaryModel(Parcel in) {
        id = in.readInt();
        keyword = in.readString();
        desc = in.readString();
    }

    public static final Creator<DictionaryModel> CREATOR = new Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel in) {
            return new DictionaryModel(in);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(keyword);
        dest.writeString(desc);
    }

    @Override
    public String toString() {
        return "DictionaryModel{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
