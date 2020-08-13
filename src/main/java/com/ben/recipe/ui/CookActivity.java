package com.ben.recipe.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ben.recipe.R;
import com.ben.recipe.adpter.CookDetailsAdapter;
import com.ben.recipe.db.CookDB;
import com.ben.recipe.model.CookBean;
import com.ben.recipe.model.Steps;
import com.ben.recipe.utils.ToastUtil;
import com.ben.recipe.view.SmartImageView;

/**
 * 菜单步骤
 */

public class CookActivity extends AppCompatActivity {

    SmartImageView smartImageView;
    TextView intro_tv,ingredients_tv,burden_tv,cook_details_name;
    ImageView shoucan_img;
    ListView cook_details_lv;

    CookDB cookDB = new CookDB(this,"t_cook");
    CookDB cookDB2 = new CookDB(this,"t_step");
    CookBean cookBean;

    Intent intent ;

    boolean isShoucan = false;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_details_h);
        intent = getIntent();
        cookBean = (CookBean) intent.getSerializableExtra("cookbean");
        isShoucan =  cookDB.hasThisMenuID(cookBean.getId());
        initView();
        progressDialog = ProgressDialog.show(CookActivity.this,null,"请稍后...");
        initData();

    }

    public void initView(){
        View view = getLayoutInflater().inflate(R.layout.activity_cook_details_head,null);
        shoucan_img = findViewById(R.id.shoucan_btn);
        smartImageView = view.findViewById(R.id.cook_details_Img);
        cook_details_name = view.findViewById(R.id.cook_details_name);
        intro_tv = view.findViewById(R.id.cook_details_intro);
        ingredients_tv = view.findViewById(R.id.ingredients);
        burden_tv = view.findViewById(R.id.burden);
        cook_details_lv = findViewById(R.id.cook_details_lv);
        cook_details_lv.addHeaderView(view);
        if(isShoucan){
            shoucan_img.setImageResource(R.mipmap.isshoucan);
        }
    }

    /**
     * 给ui添加数据
     */
    public void initData(){
        progressDialog.dismiss();
        cook_details_name.setText(cookBean.getTitle());
        smartImageView.setImageUrl(cookBean.getAlbums().get(0));
        intro_tv.setText("\u3000\u3000"+cookBean.getImtro());
        ingredients_tv.setText(cookBean.getIngredients());
        burden_tv.setText(cookBean.getBurden());
        cook_details_lv.setAdapter(new CookDetailsAdapter(CookActivity.this,cookBean.getSteps()));
    }

    /**
     *  收藏和取消收藏
     */
    public void onShoucan(View view){

        if(isShoucan){
            cookDB.delData(cookBean.getId());
            cookDB2.delData(cookBean.getId());
            isShoucan = false;
            shoucan_img.setImageResource(R.mipmap.shoucan);
            ToastUtil.toastShow(this,"取消成功");
        }else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("cook_id",cookBean.getId());
            contentValues.put("cook_title",cookBean.getTitle());
            contentValues.put("cook_tags",cookBean.getTags());
            contentValues.put("cook_imtro",cookBean.getImtro());
            contentValues.put("cook_ingredients",cookBean.getIngredients());
            contentValues.put("cook_burden",cookBean.getBurden());
            contentValues.put("cook_albums",cookBean.getAlbums().get(0));
            contentValues.put("step_id",cookBean.getId());
            cookDB.addData(contentValues);

            ContentValues contentValues2 = new ContentValues();
            for(Steps steps : cookBean.getSteps()){
                contentValues2.put("step_id",cookBean.getId());
                contentValues2.put("step_step",steps.getStep());
                contentValues2.put("step_img",steps.getImg());
                cookDB2.addData(contentValues2);
            }
            Log.i("CookActivity", "收藏成功");
            isShoucan = true;
            shoucan_img.setImageResource(R.mipmap.isshoucan);
            ToastUtil.toastShow(this,"收藏成功");
        }
    }

    public void onBack(View view){
        finish();
    }

}
