package com.zhixing.tpmlib.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zhixing.tpmlib.bean.ColumnarBean;

import java.util.List;

public class ColumnarViewModel extends ViewModel {

    public MutableLiveData<List<ColumnarBean>> ColumnarValue = new MutableLiveData<>();
}
