/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.mobcent.discuz.base.task;

import android.os.AsyncTask;
import android.content.Context;
import com.mobcent.discuz.android.db.SharedPreferencesDB;
import com.mobcent.lowest.base.utils.MCResource;
import com.mobcent.discuz.android.model.BaseResult;

public abstract class BaseTask extends AsyncTask {
    protected BaseRequestCallback<unknown_type> _callback;
    protected Context context;
    protected SharedPreferencesDB db;
    protected MCResource resource;
    
    public BaseTask(Context context, BaseRequestCallback<unknown_type> p2) {
        // :( Parsing error. Please contact me.
    }
    
    protected void onPreExecute() {
        // :( Parsing error. Please contact me.
    }
    
    protected void onPostExecute(BaseResult p1) {
        // :( Parsing error. Please contact me.
    }
}