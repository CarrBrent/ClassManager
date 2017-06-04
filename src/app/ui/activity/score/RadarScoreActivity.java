package app.ui.activity.score;

import myclass.manager.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import app.ui.TitleActivity;

public class RadarScoreActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_radar);
		showBackwardView(R.string.button_backward, true);

		setTitle("查看成绩");





	}

	@Override
	public void onClick(View v) {
		super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
		switch (v.getId()) {
		default:
			break;
		}

	}


	@Override
	protected void onBackward(View backwardView) {
		super.onBackward(backwardView);
	}

}
