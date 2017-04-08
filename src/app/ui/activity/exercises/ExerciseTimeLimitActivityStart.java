package app.ui.activity.exercises;

import android.view.View.OnClickListener;
import app.ui.TitleActivity;
import app.ui.activity.setting.AboutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import myclass.manager.R;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import app.util.BaseInfo;

public class ExerciseTimeLimitActivityStart extends TitleActivity implements OnClickListener{
	private HttpUtils http = new HttpUtils();

	private BaseInfo baseInfo;
	private String stuListLimiteTimeExercise = "stuListLimiteTimeExercise.do";

	private String stuListLimiteTimeExerciseUrl;

	private TextView time;
	private Button start;
	private String seName; 
	private String seId;
	private String cId;
	
	String questions;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_time_limit_start);
		showBackwardView(R.string.button_backward, true);

		// 设置缓存1秒,1秒内直接返回上次成功请求的结果
		http.configCurrentHttpCacheExpiry(100);
		//获取URL
		baseInfo = (BaseInfo)getApplication();
		stuListLimiteTimeExerciseUrl = baseInfo.getUrl()+stuListLimiteTimeExercise;

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		seName = bundle.getString("seName");
		seId= Integer.toString(bundle.getInt("seId"));
		cId= Integer.toString(bundle.getInt("cId"));
		setTitle(seName);
		start = (Button)findViewById(R.id.start);
		time = (TextView)findViewById(R.id.time);
		start.setOnClickListener(this);



	}
	@Override
	public void onClick(View v) {
		super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
		switch (v.getId()) {
		case R.id.start:
			stuListLimiteTimeExercise();
			
			break;
		default:
			break;
		}

	}
	public void stuListLimiteTimeExercise() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("seid",seId);
		params.addQueryStringParameter("cid",cId);
		http.send(HttpRequest.HttpMethod.GET,
				stuListLimiteTimeExerciseUrl,
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
					questions = jsonObject.toString();
					Intent intent = new Intent();
					intent.setClass(ExerciseTimeLimitActivityStart.this, ExerciseTimeLimitActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("seId",seId);
					bundle.putString("cId",cId);
					bundle.putString("questions",questions);
					bundle.putString("seName",seName);
					intent.putExtras(bundle);
					startActivity(intent);
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



}

