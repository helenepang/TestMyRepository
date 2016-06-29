/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.mobcent.discuz.base.dispatch;

import com.mobcent.discuz.android.constant.ConfigConstant;
import com.mobcent.discuz.activity.constant.FinalConstant;
import android.content.Context;
import com.mobcent.discuz.android.model.ConfigComponentModel;
import com.mobcent.lowest.base.utils.MCResource;
import com.mobcent.discuz.application.DiscuzApplication;
import android.app.Activity;
import android.app.AlertDialog;
import com.mobcent.discuz.base.helper.DZDialogHelper;
import android.app.Dialog;
import com.mobcent.discuz.base.helper.ConfigOptHelper;
import com.mobcent.discuz.base.helper.LoginHelper;
import java.util.HashMap;
import com.mobcent.discuz.android.model.ConfigModuleModel;
import android.content.Intent;
import com.mobcent.lowest.android.ui.module.weather.activity.WeatherActivity;
import com.mobcent.discuz.android.user.helper.UserManageHelper;
import com.mobcent.discuz.android.model.SettingModel;
import com.mobcent.discuz.module.sign.SignInAsyncTask;
import android.view.View;
import com.mobcent.discuz.module.person.activity.UserHomeActivity;
import com.mobcent.discuz.module.topic.list.fragment.activity.UserTopicListActivity;
import com.mobcent.discuz.module.msg.fragment.activity.SessionListActivity;
import com.mobcent.discuz.module.topic.detail.fragment.activity.TopicDetailActivity;
import com.mobcent.discuz.module.article.fragment.activity.ArticleDetailActivity;
import com.mobcent.discuz.activity.PopComponentActivity;
import com.mobcent.discuz.activity.PopModuleActivity;
import com.mobcent.discuz.android.model.UriSkipModel;
import com.mobcent.discuz.android.constant.UriConstant;
import com.mobcent.discuz.base.helper.DraftHelper;
import com.mobcent.discuz.module.publish.fragment.activity.PublishTopicActivity;
import android.text.TextUtils;
import java.util.Map;
import com.mobcent.discuz.activity.HomeActivity;
import com.mobcent.discuz.zbar.scan.delegate.ScanCallbackDelegate;
import com.mobcent.discuz.zbar.scan.fragment.activity.ScanCaptureAtivity;
import com.mobcent.discuz.android.model.BoardChild;
import com.mobcent.lowest.base.utils.MCStringUtil;
import com.mobcent.discuz.module.topic.list.fragment.activity.TopicListActivty;
import com.mobcent.discuz.activity.WebViewFragmentActivity;
import com.mobcent.lowest.base.utils.MCLogUtil;

public class ActivityDispatchHelper implements ConfigConstant, FinalConstant {
    
    public ActivityDispatchHelper() {
    }
    
    public static void dispatchActivity(Context context, UriSkipModel skipModel) {
        if(skipModel == null) {
            return;
        }
        switch(ActivityDispatchHelper.3.$SwitchMap$com$mobcent$discuz$android$constant$UriConstant$ActionSkip[skipModel.skip.ordinal()]) {
            case 1:
            {
                startUserHomeActivity(context, skipModel.userId);
                return;
            }
            case 2:
            {
                startTopicDetailActivity(context, skipModel.boardId, skipModel.topicId, 0x0, -0x1);
                return;
            }
            case 3:
            {
                startArticleDetailActivity(context, skipModel.topicId);
                return;
            }
            case 4:
            {
                startWebActivity(context, skipModel.url);
                break;
            }
        }
    }
    
    public static void dispatchActivity(Context context, ConfigModuleModel moduleModel) {
        if(moduleModel == null) {
            return;
        }
        Intent intent = new Intent(context, PopModuleActivity.class);
        intent.putExtra("moduleModel", moduleModel);
        startActivity(context, intent);
    }
    
    public static void dispatchActivity(Context context, ConfigComponentModel componentModel) {
        if((ConfigOptHelper.isNeedLogin(componentModel)) && (!LoginHelper.doInterceptor(context, 0x0, 0x0))) {
            return;
        }
        if(isPublish(componentModel.getType())) {
            dispatchPublish(context, componentModel);
            return;
        }
        if("moduleRef".equals(componentModel.getType())) {
            ConfigModuleModel moduleModel = DiscuzApplication._instance.getModuleModel(componentModel.getModuleId());
            if(ConfigOptHelper.isNeedLogin(moduleModel)) {
                if(LoginHelper.doInterceptor(context, 0x0, 0x0)) {
                    dispatchActivity(context, moduleModel);
                }
                return;
            }
            dispatchActivity(context, moduleModel);
            return;
        }
        Intent intent = 0x0;
        if("weather".equals(componentModel.getType())) {
            Intent intent = new Intent(context, WeatherActivity.class);
            SettingModel settingModel = UserManageHelper.getInstance(context).getSettingModel();
            int qs = 0x0;
            if(settingModel != null) {
                qs = settingModel.getAllowCityQueryWeather();
            }
            intent.putExtra("qs", qs);
        } else if("sign".equals(componentModel.getType())) {
            new SignInAsyncTask(context, 0x0).execute(new Void[0x0]);
            return;
        }
        if("userinfo".equals(componentModel.getType())) {
            Intent intent = new Intent(context, UserHomeActivity.class);
        } else if("search".equals(componentModel.getType())) {
            Intent intent = new Intent(context, UserTopicListActivity.class);
            intent.putExtra("topicType", "search");
        } else if("messagelist".equals(componentModel.getType())) {
            Intent intent = new Intent(context, SessionListActivity.class);
        } else if("postlist".equals(componentModel.getType())) {
            Intent intent = new Intent(context, TopicDetailActivity.class);
        } else if("newsview".equals(componentModel.getType())) {
            Intent intent = new Intent(context, ArticleDetailActivity.class);
        } else {
            Intent intent = new Intent(context, PopComponentActivity.class);
        }
        if(intent != null) {
            intent.putExtra("componentModel", componentModel);
        }
        startActivity(context, intent);
    }
    
    public static void dispatchPublish(Context context, ConfigComponentModel componentModel) {
        boolean isNeedAlert = DraftHelper.isNeedAlertDialog(context, 0x1, new DraftHelper.DraftDelegate(context, componentModel) {
            
            public void onDraftAlertBack(TopicDraftModel model) {
                if(model == null) {
                    dispatchPublish(context, componentModel);
                    return;
                }
                Intent intent = getPublishIntent(context, componentModel);
                if(intent != null) {
                    intent.putExtra("topicDraftModel", model);
                }
                startActivity(context, intent);
            }
        });
        if(isNeedAlert) {
            return;
        }
        if(!alertPicSelectDialog(context, componentModel)) {
            startActivity(context, getPublishIntent(context, componentModel));
        }
    }
    
    public static boolean alertPicSelectDialog(Context context, ConfigComponentModel componentModel) {
        if(isPublishImgComponent(componentModel.getType())) {
            MCResource resource = MCResource.getInstance(context);
            String[] names = {resource.getString("mc_forum_take_photo"),
            resource.getString("mc_forum_gallery_local_pic")};
            Activity topActivity = DiscuzApplication._instance.getTopActivity();
            AlertDialog dialog = localActivityDispatchHelper.21.setTitle(resource.getString("mc_forum_publish_choose")).setItems(names, new DialogInterface.OnClickListener(componentModel, context) {
                
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0) {
                        componentModel.setType("fastcamera");
                    } else {
                        componentModel.setType("fastimage");
                    }
                    startActivity(context, getPublishIntent(context, componentModel));
                }
            }).create();
            if(topActivity != null) {
                try {
                    dialog.show();
                    localActivityDispatchHelper.21.setTitle(resource.getString("mc_forum_publish_choose")).setItems(names, new DialogInterface.OnClickListener(componentModel, context) {
                        
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == 0) {
                                componentModel.setType("fastcamera");
                            } else {
                                componentModel.setType("fastimage");
                            }
                            startActivity(context, getPublishIntent(context, componentModel));
                        }
                    }) = 0x1;
                } catch(Exception e) {
                    DZDialogHelper.setDialogSystemAlert(dialog);
                    localActivityDispatchHelper.21.setTitle(resource.getString("mc_forum_publish_choose")).setItems(names, new DialogInterface.OnClickListener(componentModel, context) {
                        
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == 0) {
                                componentModel.setType("fastcamera");
                            } else {
                                componentModel.setType("fastimage");
                            }
                            startActivity(context, getPublishIntent(context, componentModel));
                        }
                    }) = 0x1;
                }
                DZDialogHelper.setDialogSystemAlert(dialog);
                return true;
            }
        }
        return false;
    }
    
    public static boolean isPublish(String type) {
        if(("fasttext".equals(type)) || ("fastimage".equals(type)) || ("fastcamera".equals(type)) || ("fastaudio".equals(type))) {
            return true;
        }
        return false;
    }
    
    public static Intent getPublishIntent(Context context, ConfigComponentModel componentModel) {
        Intent intent = new Intent(context, PublishTopicActivity.class);
        intent.putExtra("moduleModel", componentModel);
        intent.putExtra("tempParam", 0x2);
        if(componentModel.getType().equals("fasttext")) {
            return intent;
        }
        if(componentModel.getType().equals("fastimage")) {
            intent.putExtra("rapidPublish", 0x1);
            return intent;
        }
        if(componentModel.getType().equals("fastcamera")) {
            intent.putExtra("rapidPublish", 0x2);
            intent.putExtra("moduleModel", componentModel);
            return intent;
        }
        if(componentModel.getType().equals("fastaudio")) {
            intent.putExtra("rapidPublish", 0x3);
            intent.putExtra("moduleModel", componentModel);
        }
        return intent;
    }
    
    public static void startActivity(Context context, Intent intent) {
        if(intent == null) {
            return;
        }
        try {
            startActivity(intent);
            return;
        } catch(Exception e) {
            intent.setFlags(0x10000000);
            startActivity(intent);
        }
    }
    
    public static boolean isModuleModel(String type) {
        if(("subnav".equals(type)) || ("full".equals(type)) || ("subnav".equals(type))) {
            return true;
        }
        return false;
    }
    
    public static boolean isPublishImgComponent(String type) {
        if(TextUtils.isEmpty(type)) {
            return false;
        }
        if(("fastimage".equals(type)) || ("fastcamera".equals(type))) {
            return true;
        }
        return false;
    }
    
    public static void startUserHomeActivity(Context context, long userId) {
        Intent intent = new Intent(context, UserHomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(context, intent);
    }
    
    public static void startTopicDetailActivity(Context context, long boardId, long topicId, String style, int specil) {
        // :( Parsing error. Please contact me.
    }
    
    public static void startTopicDetailActivity(Context context, long boardId, long topicId, String boardName, String style, int specil) {
        // :( Parsing error. Please contact me.
    }
    
    public static void startTopicDetailActivity(Context context, long boardId, long topicId, String boardName, boolean requestEdit, String style, int specil) {
        if((specil == -0x1) || (TextUtils.isEmpty(skipH5Url(specil, topicId)))) {
            Intent intent = new Intent(context, TopicDetailActivity.class);
            intent.putExtra("boardId", boardId);
            intent.putExtra("topicId", topicId, topicId);
            intent.putExtra("boardName", boardName);
            if((!TextUtils.isEmpty(style)) && (!"noTitle".equals(style))) {
                String localString1 = "flat";
            }
            intent.putExtra("style", style);
            intent.putExtra("topicDetailRequestEdit", requestEdit);
            startActivity(context, intent);
            return;
        }
        startWebActivity(context, skipH5Url(specil, topicId));
    }
    
    public static void startTopicListAtivity(Context context, BoardChild boardChild, ConfigComponentModel componentModel, boolean isCard) {
        Intent intent = 0x0;
        if(MCStringUtil.isEmpty(boardChild.getForumRedirect())) {
            Intent intent = new Intent(context, TopicListActivty.class);
            intent.putExtra("boardId", boardChild.getBoardId());
            intent.putExtra("boardChild", boardChild.getBoardChild());
            intent.putExtra("boardContent", boardChild.getBoardContent());
            intent.putExtra("boardName", boardChild.getBoardName());
            intent.putExtra("componentModel", componentModel);
            if(isCard) {
                intent.putExtra("style", "card");
            } else {
                intent.putExtra("style", "flat");
            } else {
                Intent intent = new Intent(context, WebViewFragmentActivity.class);
                intent.putExtra("webViewUrl", boardChild.getForumRedirect());
            }
        }
        startActivity(intent);
    }
    
    public static String skipH5Url(int specil, long topicId) {
        if((application == null) || (application.getSettingModel() == null)) {
            return null;
        }
        SettingModel settingModel = application.getSettingModel();
        if((settingModel.getThreadTemplate() == null) || (settingModel.getThreadTemplate().isEmpty())) {
            return null;
        }
        String url = (String)settingModel.getThreadTemplate().get(Integer.valueOf(specil));
        if(TextUtils.isEmpty(url)) {
            return null;
        }
        try {
            return url;
        } catch(Exception localException1) {
            return url;
        }
    }
    
    public static void startArticleDetailActivity(Context context, long aid) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra("aid", aid);
        startActivity(context, intent);
    }
    
    public static void startWebActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewFragmentActivity.class);
        intent.putExtra("webViewUrl", url);
        MCLogUtil.e("", intent.toUri(0x1));
        startActivity(context, intent);
    }
    
    public static void startWebActivity(Context context, String url, String type, String title) {
        Intent intent = new Intent(context, WebViewFragmentActivity.class);
        intent.putExtra("webTitle", title);
        intent.putExtra("webViewUrl", url);
        intent.putExtra("webType", type);
        startActivity(context, intent);
    }
    
    public static void startScanActivity(Context context, ScanCallbackDelegate.ScanCallbackLisenter lisenter) {
        ScanCallbackDelegate.getInstance().setScanCallbackLisenter(lisenter);
        Intent intent = new Intent(context, ScanCaptureAtivity.class);
        startActivity(context, intent);
    }
    
    public static void startApp(Context context, long configId) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("configId", configId);
        startActivity(context, intent);
    }
}