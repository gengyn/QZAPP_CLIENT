package com.qingzhou.client;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.adapter.MaterialInventoryListViewAdapter;
import com.qingzhou.client.domain.BaseInventory;
import com.qingzhou.client.domain.MaterialInventory;


/**
 * 我的合同-主材结算清单
 * @author hihi
 *
 */
public class MyContractMaterialInventoryActivity extends BaseActivity {

	private ListView materialInventoryListView;
	private ImageButton btn_back;
	private TextView material_total_price;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycontract_materialinventory);
		
		materialInventoryListView = (ListView) findViewById(R.id.materialInventoryList);
		material_total_price = (TextView)findViewById(R.id.material_total_price);
		List<MaterialInventory> materialList = (List<MaterialInventory>) getIntent().getSerializableExtra("MATERIALINVENTORYLIST");
		material_total_price.setText(StringUtils.formatDecimal(materialList.get(0).getPracticalPrice()));
		materialList.remove(0);
		
		materialInventoryListView.setAdapter(new MaterialInventoryListViewAdapter(this,materialList));
		
		//close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	MyContractMaterialInventoryActivity.this.finish();
            }    
        });

		
	}
	
	
}
