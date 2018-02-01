package com.dksazxv.dskf.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dksazxv.dskf.com.db.tables.OrderTable;


public class First_Single_Activity extends AppCompatActivity implements ShowPredictionsView{
    private static final String TAG = First_Single_Activity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_choice);

        findViewById(R.id.btn_choice_play).setOnClickListener(__ -> {
            openScreenGameActivity();
            Log.i(TAG, OrderTable.COLUMN_ID);
        });

        findViewById(R.id.btn_choice_feedback).setOnClickListener(__ -> {
            Log.i(TAG, OrderTable.TABLE_ORDER);
            openFeedBackActivity();
        });
    }

    private void openScreenGameActivity() {
        Log.d(TAG, "openScreenMainActivity");
        startActivity(Play_Activity.getGameActivityIntent(this));
        finish();
    }

    private void openFeedBackActivity() {
        Log.d(TAG, "openFeedBackActivity");
        startActivity(Comments_Activity.getFeedbackActivityIntent(this));
    }

    @NonNull
    public static Intent getChoiceActivityIntent(Context context) {
        return new Intent(context, First_Single_Activity.class);
    }

    @Override
    public void showPredictionsHoroscope() {
        Log.i(TAG, "showPredictionsHoroscope");
    }
}
