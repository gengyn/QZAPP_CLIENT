package com.qingzhou.client;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.adapter.BaseInventoryListViewAdapter;
import com.qingzhou.client.domain.BaseInventory;


/**
 * 我的合同-基础结算清单
 * @author hihi
 *
 */
public class MyContractBaseInventoryActivity extends BaseActivity {

	private ListView baseInventoryListView;
	private ImageButton btn_back;
	private TextView base_total_price;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract_baseinventory);
		
		baseInventoryListView = (ListView) findViewById(R.id.baseInventoryList);
		base_total_price = (TextView)findViewById(R.id.base_total_price);
		List<BaseInventory> baseList = (List<BaseInventory>) getIntent().getSerializableExtra("BASEINVENTORYLIST");
		base_total_price.setText(StringUtils.formatDecimal(baseList.get(0).getPracticalPrice()));
		baseList.remove(0);
		
		baseInventoryListView.setAdapter(new BaseInventoryListViewAdapter(this,baseList));
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyContractBaseInventoryActivity.this.finish();
            }    
        });

		
	}
	
	
}
