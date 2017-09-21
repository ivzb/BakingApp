package com.udacity.baking.widget;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.udacity.baking.provider.BakingContract;

public class WidgetDataProvider implements RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    WidgetDataProvider(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);

        int ingredientIndex = mCursor.getColumnIndex(BakingContract.IngredientEntry.COLUMN_INGREDIENT);
        String ingredient = mCursor.getString(ingredientIndex);

        RemoteViews mView = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        mView.setTextViewText(android.R.id.text1, ingredient);
        mView.setTextColor(android.R.id.text1, Color.BLACK);

        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        if (mCursor != null) mCursor.close();

        mCursor = mContext.getContentResolver().query(
            BakingContract.IngredientEntry.CONTENT_URI,
            null,
            null,
            null,
            null);
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }
}