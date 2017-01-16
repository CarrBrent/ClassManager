package app.ui.activity.myclass;

import myclass.manager.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import app.ui.TitleActivity;
import app.ui.activity.setting.AboutActivity;

public class MyClassDetailActivity extends TitleActivity implements OnClickListener{
	/* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
	private int cId;
	private String cName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclass_detail);
        showBackwardView(R.string.button_backward, true);
        
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        cId = bundle.getInt("cId");
        cName = bundle.getString("cName");
        setTitle(cName);
        //为控件添加监听器
        findViewById(R.id.layout_1).setOnClickListener(this);
        findViewById(R.id.layout_2).setOnClickListener(this);
        
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);//实现父类的onClick方法这样才可使使左上角的返回按钮生效
        switch (v.getId()) {
		case R.id.layout_1:
			Intent seminarintent = new Intent();
			seminarintent.setClass(MyClassDetailActivity.this, SeminarActivity.class);
			Bundle seminarbundle = new Bundle();
			seminarbundle.putInt("cId",cId);
			seminarbundle.putString("cName",cName);
			seminarintent.putExtras(seminarbundle);
			startActivity(seminarintent);
			break;
		case R.id.layout_2:
			Intent intent = new Intent();
			intent.setClass(MyClassDetailActivity.this, CourseSelectActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("cId",cId);
			bundle.putString("cName",cName);
			intent.putExtras(bundle);
			startActivity(intent);
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
