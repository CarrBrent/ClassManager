package app.ui.fragment;

import myclass.manager.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.inputmethodservice.Keyboard.Key;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import app.ui.BaseFragment;
import app.ui.activity.barcode.BarCodeActivity;
import app.ui.activity.exercises.ExerciseErrorNoteActivity;
import app.ui.activity.myclass.MyClassActivity;
import app.ui.activity.myclass.httptestActivity;
import app.ui.activity.note.FeedBackActivity;
import app.ui.activity.note.KeyNoteActivity;
import app.ui.activity.note.StuFeedBackActivity;
import app.ui.activity.score.RadarScoreActivity;
import app.ui.activity.setting.AboutActivity;
import app.ui.activity.setting.LoginActivity;

public class SettingFragment extends BaseFragment implements OnClickListener {


	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_about:
			startActivity(new Intent(getActivity(), AboutActivity.class));
			break;
		case R.id.layout_account:
			startActivity(new Intent(getActivity(), LoginActivity.class));
			break;
		case R.id.layout_notification:
			startActivity(new Intent(getActivity(), httptestActivity.class));
			break;
		case R.id.layout_privacy:
			startActivity(new Intent(getActivity(), BarCodeActivity.class));
			break;
		case R.id.layout_errornote:
			startActivity(new Intent(getActivity(), ExerciseErrorNoteActivity.class));
			break;
		case R.id.layout_keynote:
			startActivity(new Intent(getActivity(), KeyNoteActivity.class));
			break;
		case R.id.layout_feedback:
			startActivity(new Intent(getActivity(), FeedBackActivity.class));
			break;
		case R.id.layout_stufeedback:
			startActivity(new Intent(getActivity(), StuFeedBackActivity.class));
			break;
		case R.id.layout_radar:
			startActivity(new Intent(getActivity(), RadarScoreActivity.class));
			break;
		case R.id.layout_exit:
			SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();//获取编辑器
			editor.remove("userId");
//			editor.putString("userId","null");
			editor.commit();//提交修改

			Toast.makeText(this.getActivity().getApplicationContext(), "已退出登录", 1).show();
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_setting, container, false);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.layout_about).setOnClickListener(this);
		view.findViewById(R.id.layout_account).setOnClickListener(this);
		view.findViewById(R.id.layout_notification).setOnClickListener(this);
		view.findViewById(R.id.layout_privacy).setOnClickListener(this);
		view.findViewById(R.id.layout_exit).setOnClickListener(this);
		view.findViewById(R.id.layout_errornote).setOnClickListener(this);
		view.findViewById(R.id.layout_keynote).setOnClickListener(this);
		view.findViewById(R.id.layout_feedback).setOnClickListener(this);
		view.findViewById(R.id.layout_stufeedback).setOnClickListener(this);
		view.findViewById(R.id.layout_radar).setOnClickListener(this);
	}

}