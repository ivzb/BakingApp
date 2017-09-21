package com.udacity.baking.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class BakingContract {

    static final String AUTHORITY = "com.udacity.baking";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    static final String PATH_INGREDIENTS = "ingredients";

    public static final class IngredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();

        static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_INGREDIENT = "ingredient";
    }
}
