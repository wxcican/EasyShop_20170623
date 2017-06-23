package com.fuicuiedu.xc.easyshop_20170623.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuicuiedu.xc.easyshop_20170623.R;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class UnLoginFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_un_login,container,false);
        return view;
    }
}
