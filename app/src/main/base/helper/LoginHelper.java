/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.mobcent.discuz.base.helper;

import android.content.Context;
import com.mobcent.discuz.android.model.BaseResult;
import com.mobcent.discuz.android.user.helper.UserManageHelper;
import java.util.HashMap;
import java.io.Serializable;
import com.mobcent.discuz.android.service.impl.UserServiceImpl;
import com.mobcent.discuz.android.service.UserService;
import android.content.Intent;
import com.mobcent.discuz.module.person.activity.UserLoginActivity;
import com.mobcent.discuz.base.dispatch.ActivityDispatchHelper;

public class LoginHelper {
    
    public static boolean checkToken(Context context, BaseResult result) {
        if((result.getRs() == 0) && ("00100001".equals(result.getErrorCode()))) {
            UserManageHelper.getInstance(context).logout();
            doInterceptor(context, 0x0, 0x0);
            return true;
        }
        return false;
    }
    
    public static void doInterceptor(Context context, Class<?> goToActicityClass, HashMap<String, Serializable> param) {
        UserServiceImpl userService = new UserServiceImpl(getApplicationContext());
        boolean isLogin = userService.isLogin();
        if(!isLogin) {
            Intent intent = new Intent(context, UserLoginActivity.class);
            intent.putExtra("goToActivityClass", goToActicityClass);
            intent.putExtra("goParam", param);
            ActivityDispatchHelper.startActivity(context, intent);
        }
        return isLogin;
    }
}