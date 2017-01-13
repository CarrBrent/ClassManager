package app.ui.activity.evaluate;

import myclass.manager.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import app.ui.TitleActivity;
import app.ui.activity.barcode.SignInActivity;
import app.ui.activity.myclass.GroupingActivity;
import app.ui.activity.setting.AboutActivity;

public class EvaluateActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
	private int seId;
	private int cId;
	private String seName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        showBackwardView(R.string.button_backward, true);
        
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        seId = bundle.getInt("seId");
        cId = bundle.getInt("cId");
        seName = bundle.getString("seName");
        setTitle(seName);
        //为控件添加监听器
        findViewById(R.id.layout_1).setOnClickListener(this);
        findViewById(R.id.layout_2).setOnClickListener(this);
        findViewById(R.id.layout_3).setOnClickListener(this);
        
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
        switch (v.getId()) {
		case R.id.layout_1:
			Intent ingroupintent = new Intent();
			ingroupintent.setClass(EvaluateActivity.this, AboutActivity.class);
			Bundle ingroupbundle = new Bundle();
			ingroupbundle.putInt("seId",seId);
			ingroupbundle.putInt("cId",cId);
			ingroupbundle.putString("seName",seName);
			ingroupintent.putExtras(ingroupbundle);
			startActivity(ingroupintent);
			break;
		case R.id.layout_2:
			Intent outgroupintent = new Intent();
			outgroupintent.setClass(EvaluateActivity.this, AboutActivity.class);
			Bundle outgroupbundle = new Bundle();
			outgroupbundle.putInt("seId",seId);
			outgroupbundle.putInt("cId",cId);
			outgroupbundle.putString("seName",seName);
			outgroupintent.putExtras(outgroupbundle);
			startActivity(outgroupintent);
			break;
		case R.id.layout_3:
			SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
	    	String sId = sharedPreferences.getString("userId", "null");
			Intent selfintent = new Intent();
			selfintent.setClass(EvaluateActivity.this, SelfEvaluateActivity.class);
			Bundle selfbundle = new Bundle();
			selfbundle.putInt("seId",seId);
			selfbundle.putInt("sId",Integer.parseInt(sId));
			selfbundle.putInt("cId",cId);
			selfbundle.putString("seName",seName);
			selfintent.putExtras(selfbundle);
			startActivity(selfintent);
			break;

		default:
			break;
		}
        
    }
    
    
    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
    }

}
