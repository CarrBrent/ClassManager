package app.ui.activity.exercises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.view.View.OnClickListener;
import app.bean.Answer;
import app.bean.Question;
import app.ui.TitleActivity;

import org.json.JSONArray;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import app.util.BaseInfo;

public class ExerciseTimeLimitActivity extends TitleActivity implements OnClickListener{
	private HttpUtils http = new HttpUtils();

	private BaseInfo baseInfo;
	private String stuLimiteTimeExerciseSubmit = "stuLimiteTimeExerciseSubmit.do";

	private String stuLimiteTimeExerciseSubmitUrl;

	private List<Question> questionList;
	private RadioGroup mRadioGroup;
	private RadioButton[] mRadioButton = new RadioButton[4];
	private TextView title;
	private Button submit;
	private String seName; 
	private String seId;
	private String cId;
	private int count;
	private int current;

	private String questions;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_time_limit);
		showBackwardView(R.string.button_backward, true);

		// 设置缓存1秒,1秒内直接返回上次成功请求的结果
		http.configCurrentHttpCacheExpiry(100);

		//获取URL
		baseInfo = (BaseInfo)getApplication();
		stuLimiteTimeExerciseSubmitUrl = baseInfo.getUrl()+stuLimiteTimeExerciseSubmit;

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		questions = bundle.getString("questions");
		seName = bundle.getString("seName");
		seId= bundle.getString("seId");
		cId= bundle.getString("cId");
		setTitle(seName);

		title = (TextView) findViewById(R.id.tv_title);
		mRadioButton[0] = (RadioButton) findViewById(R.id.RadioA);
		mRadioButton[1] = (RadioButton) findViewById(R.id.RadioB);
		mRadioButton[2] = (RadioButton) findViewById(R.id.RadioC);
		mRadioButton[3] = (RadioButton) findViewById(R.id.RadioD);
		mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);

		questionList = getQuestions(questions);

		count = questionList.size();
		current = 0;

		Question q = questionList.get(0);
		title.setText(q.getContent());

		mRadioButton[0].setText(q.getAnswers().get(0).getAcontent());
	    mRadioButton[1].setText(q.getAnswers().get(1).getAcontent());
		mRadioButton[2].setText(q.getAnswers().get(2).getAcontent());
		mRadioButton[3].setText(q.getAnswers().get(3).getAcontent());




	}
	private static List<Question> getQuestions(String questions) {  
		List<Question> list = new ArrayList<Question>();  

		try {  
			JSONObject jsonObject = new JSONObject(questions);
			JSONArray mapsArray = jsonObject.getJSONArray("questions");  
			for (int i = 0; i < mapsArray.length(); i++) {  
				Question question = new Question();
				JSONObject jsonObject2 = mapsArray.getJSONObject(i);  
				question.setQid(jsonObject2.getInt("qid"));
				question.setCid(jsonObject2.getInt("cid"));
				question.setSeid(jsonObject2.getInt("seId"));
				question.setContent(jsonObject2.getString("content"));
				List<Answer> answers = getAnswers(jsonObject2.getString("answers"));
				question.setAnswers(answers);
				list.add(question);
				
			}  
		} catch (Exception e) {  
			// TODO: handle exception  
		}  
		return list;  
	}  




	private static List<Answer> getAnswers(String string) {
		List<Answer> answers = new ArrayList<Answer>();
		try {
			JSONArray jsonArray = new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				Answer answer = new Answer();
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);  
				answer.setAid(jsonObject2.getInt("aid"));
				answer.setAcontent(jsonObject2.getString("acontent"));
				answers.add(answer);
			}  
		} catch (Exception e) {  
			// TODO: handle exception  
		}  
		
		return answers;
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
		switch (v.getId()) {
		case R.id.submit:
			stuLimiteTimeExerciseSubmit();
			break;
		default:
			break;
		}

	}
	public void stuLimiteTimeExerciseSubmit() {
		SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
		String sId = sharedPreferences.getString("userId", "null");
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("seid",seId);
		params.addQueryStringParameter("cid",cId);
		params.addQueryStringParameter("sid",sId);
		http.send(HttpRequest.HttpMethod.GET,
				stuLimiteTimeExerciseSubmitUrl,
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

			}
			@Override
			public void onFailure(HttpException error, String msg) {
			}
		});

	}



}

