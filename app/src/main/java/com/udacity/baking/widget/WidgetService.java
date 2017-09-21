package com.udacity.baking.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.udacity.baking.R;

public class WidgetService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE = "com.udacity.baking.widget.action.update_recipe";

    public WidgetService() {
        super("WidgetService");
    }

    public static void startActionUpdateRecipe(Context context) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_UPDATE_RECIPE.equals(action)) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                        new ComponentName(this, WidgetProvider.class));

                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetIngredientsList);

                for (int appWidgetId: appWidgetIds) {
                    WidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetId);
                }
            }
        }
    }
}
