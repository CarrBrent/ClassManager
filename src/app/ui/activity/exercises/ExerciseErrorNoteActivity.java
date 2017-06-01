package app.ui.activity.exercises;

import myclass.manager.R;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import app.ui.TitleActivity;
import app.ui.activity.setting.AboutActivity;

public class ExerciseErrorNoteActivity extends TitleActivity {

    private static final String TAG = AboutActivity.class.getSimpleName();

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
    }


    private void setupViews() {
        setContentView(R.layout.activity_exercise_errornote);
        setTitle("错题本");
        showBackwardView(R.string.button_backward, true);
     
    }

    /* (non-Javadoc)
     * @see app.ui.TitleActivity#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
                      default:
                break;
        }
    }
    /* (non-Javadoc)
     * @see app.ui.TitleActivity#onBackward(android.view.View)
     */
    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
    }

}