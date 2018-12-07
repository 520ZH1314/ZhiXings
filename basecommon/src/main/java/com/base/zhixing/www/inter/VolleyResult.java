package com.base.zhixing.www.inter;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyResult {
    public void success(JSONObject jsonObject);
    public void error(VolleyError error);
}
