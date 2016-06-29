/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.mobcent.discuz.base.fragment;

import com.mobcent.discuz.base.model.TopSettingModel;
import java.util.ArrayList;
import java.util.List;
import com.mobcent.discuz.base.model.TopBtnModel;
import android.view.View;

public class DemoDetailFragment extends BaseFragment {
    private int COMMENT;
    private int SHARE;
    
    public DemoDetailFragment() {
        COMMENT = 0x1;
        SHARE = 0x2;
    }
    
    protected String getRootLayoutName() {
        return "base_demo_detail";
    }
    
    protected void initViews(View rootView) {
    }
    
    protected void initActions(View rootView) {
    }
    
    protected void componentDealTopbar() {
        TopSettingModel topSettingModel = createTopSettingModel();
        topSettingModel.isTitleClickAble = true;
        ArrayList<TopBtnModel> rightModels = new ArrayList<TopBtnModel>();
        topSettingModel.title = "\u5e16\u5b50\u8be6\u60c5";
        TopBtnModel topBtnModel = new TopBtnModel();
        topBtnModel.icon = "mc_forum_top_bar_button2";
        topBtnModel.title = "\u8bc4\u8bba";
        topBtnModel.action = COMMENT;
        rightModels.add(topBtnModel);
        TopBtnModel topBtnModel2 = new TopBtnModel();
        topBtnModel2.icon = "mc_forum_top_bar_button2";
        topBtnModel2.title = "\u5206\u4eab";
        topBtnModel2.action = SHARE;
        rightModels.add(topBtnModel2);
        topSettingModel.rightModels = rightModels;
        dealTopBar(topSettingModel);
        registerTopListener(new View.OnClickListener(this) {
            
            public void onClick(View v) {
                TopBtnModel t = (TopBtnModel)v.getTag();
                if(t.action == COMMENT) {
                    Toast.makeText(getActivity(), "\u8bc4\u8bba", 0x3e8).show();
                    return;
                }
                if(t.action == SHARE) {
                    Toast.makeText(getActivity(), "\u5206\u4eab", 0x3e8).show();
                    return;
                }
                if(t.action == -0x2) {
                    Toast.makeText(getActivity(), "\u6807\u9898\u70b9\u51fb", 0x3e8).show();
                }
            }
        });
    }
}