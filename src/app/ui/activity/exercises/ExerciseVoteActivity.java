package app.ui.activity.exercises;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import myclass.manager.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import app.ui.TitleActivity;
import app.ui.activity.barcode.SignInActivity;
import app.ui.activity.evaluate.EvaluateActivity;
import app.ui.activity.exercises.ExerciseActivity;
import app.ui.activity.myclass.GroupingActivity;
import app.ui.activity.score.ScoreActivity;
import app.ui.activity.setting.AboutActivity;
import app.util.BaseInfo;

public class ExerciseVoteActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private int seId;
	private int cId;
	private String seName;
	private String sId;
	private String voteString = "stuBeginVote.do";
	private String voteUrl;
	private HttpUtils http = new HttpUtils();
	private BaseInfo baseInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_vote);
		showBackwardView(R.string.button_backward, true);
		// 设置缓存1秒,1秒内直接返回上次成功请求的结果
		http.configCurrentHttpCacheExpiry(1000);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		seId = bundle.getInt("seId");
		cId = bundle.getInt("cId");
		seName = bundle.getString("seName");
		setTitle(seName);

		baseInfo=(BaseInfo)getApplication();
		voteUrl = baseInfo.getUrl()+voteString;
		SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
		sId = sharedPreferences.getString("userId", "0");


		//为控件添加监听器
		findViewById(R.id.layout_1).setOnClickListener(this);
		findViewById(R.id.layout_2).setOnClickListener(this);
		findViewById(R.id.layout_3).setOnClickListener(this);
		findViewById(R.id.layout_4).setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
		switch (v.getId()) {
		case R.id.layout_1:
			RequestParams params1 = new RequestParams();
			params1.addQueryStringParameter("seid",Integer.toString(seId));
			params1.addQueryStringParameter("sid",sId);
			params1.addQueryStringParameter("stuAnswer","A");
			Vote(voteUrl, params1);
			break;
		case R.id.layout_2:
			RequestParams params2 = new RequestParams();
			params2.addQueryStringParameter("seid",Integer.toString(seId));
			params2.addQueryStringParameter("sid",sId);
			params2.addQueryStringParameter("stuAnswer","C");
			Vote(voteUrl, params2);
			break;
		case R.id.layout_3:
			RequestParams params3 = new RequestParams();
			params3.addQueryStringParameter("seid",Integer.toString(seId));
			params3.addQueryStringParameter("sid",sId);
			params3.addQueryStringParameter("stuAnswer","B");
			Vote(voteUrl, params3);
			break;
		case R.id.layout_4:
			RequestParams params4 = new RequestParams();
			params4.addQueryStringParameter("seid",Integer.toString(seId));
			params4.addQueryStringParameter("sid",sId);
			params4.addQueryStringParameter("stuAnswer","D");
			Vote(voteUrl, params4);
			break;

		default:
			break;
		}

	}
	//从服务器获取数据
	private void Vote(String URL, RequestParams params){
		http.send(HttpRequest.HttpMethod.GET,
				URL,
				params,
				new RequestCallBack<String>() {

			@Override
			public void onStart() {
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(responseInfo.result);

					String flag=((Integer)jsonObject.get("flag")).toString();
					if (flag.equals("-1")) {
						Toast.makeText(ExerciseVoteActivity.this, "投票失败", 1).show();
					}else {
						Toast.makeText(ExerciseVoteActivity.this, "投票成功", 1).show();
						findViewById(R.id.layout_1).setClickable(false);
						findViewById(R.id.layout_2).setClickable(false);
						findViewById(R.id.layout_3).setClickable(false);
						findViewById(R.id.layout_4).setClickable(false);
						finish();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			@Override
			public void onFailure(HttpException error, String msg) {
			}
		});

	}
 

	@Override
	protected void onBackward(View backwardView) {
		super.onBackward(backwardView);
	}

}
