/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.mobcent.discuz.base.task;

import com.mobcent.discuz.android.model.PermissionModel;
import com.mobcent.discuz.android.model.SettingModel;

public interface abstract class RequestCalback implements BaseRequestCallback {
    
    public abstract void onPostExecute(PermissionModel p1);
    
    
    public abstract void onPostExecute(SettingModel p1);
    
}