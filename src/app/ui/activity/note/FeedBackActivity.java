package app.ui.activity.note;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import myclass.manager.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import app.ui.TitleActivity;
import app.ui.activity.setting.AboutActivity;

public class FeedBackActivity extends TitleActivity {
	private ColumnChartView mColumnChartView;
	private ColumnChartData mColumnChartData;
	private static final int DEFAULT_DATA = 0;
	private boolean hasAxes = true;
	private boolean hasAxesNames = true;
	private boolean hasLabels = true;
	private boolean hasLabelForSelected = false;

    private static final String TAG = AboutActivity.class.getSimpleName();

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
        
        mColumnChartView = (ColumnChartView) findViewById(R.id.column);
		mColumnChartView.setOnValueTouchListener(new ValueTouchListener());
		
        Random random = new Random();
        generateDefaultData(random.nextInt(5),random.nextInt(5)+3,random.nextInt(10)+8,random.nextInt(5)+5);
    }


    private void setupViews() {
        setContentView(R.layout.activity_feedback);
        setTitle("反馈信息");
        showBackwardView(R.string.button_backward, true);
     
    }
    private void generateDefaultData(int a,int b,int c,int d) {
		int[] data = {a,b,c,d};
		int numSubcolumns = 1;//设置每个柱状图显示的颜色数量(每个柱状图显示多少块)
		int numColumns = 4;   //柱状图的数量
		// Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
		List<Column> columns = new ArrayList<Column>();
		List<SubcolumnValue> values;
		for (int i = 0; i < numColumns; ++i) {
			values = new ArrayList<SubcolumnValue>();
			for (int j = 0; j < numSubcolumns; ++j) {
				SubcolumnValue value = new SubcolumnValue((float)data[i], ChartUtils.COLOR_ORANGE);//第一个值是数值(值>0 方向朝上，值<0，方向朝下)，第二个值是颜色
				//    SubcolumnValue value = new SubcolumnValue((float) Math.random() * 50f + 5, Color.parseColor("#00000000"));//第一个值是数值，第二个值是颜色
				//    values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
				values.add(value);
			}
			Column column = new Column(values);//一个柱状图的实例
			column.setHasLabels(hasLabels);//设置是否显示每个柱状图的高度，
			column.setHasLabelsOnlyForSelected(hasLabelForSelected);//点击的时候是否显示柱状图的高度，和setHasLabels()和用的时候，setHasLabels()失效
			columns.add(column);
		}
		mColumnChartData = new ColumnChartData(columns);//表格的数据实例
		if (hasAxes) {
			Axis axisX = new Axis();
			//   axisX.setInside(true);//是否显示在里面，默认为false
			AxisValue value1 = new AxisValue(0f);//值是在哪显示 0 是指 第0个柱状图
			value1.setLabel("非常难");//设置显示的文本，默认为柱状图的位置
			AxisValue value2 = new AxisValue(1.0f);
			value2.setLabel("中等难度");
			AxisValue value3 = new AxisValue(2.0f);
			value3.setLabel("一般难度");
			AxisValue value4 = new AxisValue(3.0f);
			value4.setLabel("容易");
			
			List<AxisValue> axisValues = new ArrayList<AxisValue>();
			axisValues.add(value1);
			axisValues.add(value2);
			axisValues.add(value3);
			axisValues.add(value4);
			axisX.setValues(axisValues);
			Axis axisY = new Axis().setHasLines(true);
			if (hasAxesNames) {
				axisX.setName("难度");//设置X轴的注释
				axisY.setName("反馈学生数量");//设置Y轴的注释
			}
			mColumnChartData.setAxisXBottom(axisX);//设置X轴显示的位置
			mColumnChartData.setAxisYLeft(axisY);//设置Y轴显示的位置
		} else {
			mColumnChartData.setAxisXBottom(null);
			mColumnChartData.setAxisYLeft(null);
		}
		mColumnChartView.setColumnChartData(mColumnChartData);//为View设置数据
	}
    private class ValueTouchListener implements ColumnChartOnValueSelectListener {
		@Override
		public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
			Toast.makeText(FeedBackActivity.this, Float.toString(value.getValue()), Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onValueDeselected() {
		}
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
