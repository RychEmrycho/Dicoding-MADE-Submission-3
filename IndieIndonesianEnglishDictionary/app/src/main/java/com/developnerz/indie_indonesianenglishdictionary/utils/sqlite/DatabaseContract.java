package com.developnerz.indie_indonesianenglishdictionary.utils.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Rych Emrycho on 8/31/2018 at 12:25 AM.
 * Updated by Rych Emrycho on 8/31/2018 at 12:25 AM.
 */
public class DatabaseContract {
    public final static String TABLE_NAME_ID_EN = "table_id_en";
    public final static String TABLE_NAME_EN_ID = "table_en_id";

    public static final class DictionaryColumns implements BaseColumns {
        public static String KEYWORD = "keyword";
        public static String DESC = "description";
    }
}
