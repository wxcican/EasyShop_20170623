package com.fuicuiedu.xc.easyshop_20170623;

import com.feicuiedu.apphx.model.repository.IRemoteUsersRepo;
import com.fuicuiedu.xc.easyshop_20170623.commons.CurrentUser;
import com.fuicuiedu.xc.easyshop_20170623.model.GetUsersResult;
import com.fuicuiedu.xc.easyshop_20170623.model.User;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopClient;
import com.google.gson.Gson;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

//实现环信模块定义的远程用户仓库
public class RemoteUserRepo implements IRemoteUsersRepo{
    //通过用户名查询用户
    @Override
    public List<EaseUser> queryByName(String username) throws Exception {
        Call call = EasyShopClient.getInstance().getSearchUser(username);
        //同步执行，拿到返回接口
        Response response = call.execute();

        //如果失败
        if (!response.isSuccessful()){
            throw new Exception(response.body().string());
        }

        String json = response.body().string();
        GetUsersResult result = new Gson().fromJson(json,GetUsersResult.class);

        //本地用户的类转换成环信的用户类
        List<User> users = result.getDatas();
        List<EaseUser> easeUsers = CurrentUser.convertAll(users);

        return easeUsers;
    }

    //通过环信ID查询用户信息
    @Override
    public List<EaseUser> getUsers(List<String> ids) throws Exception {
        //将好友列表存起来
        CurrentUser.setList(ids);

        Call call = EasyShopClient.getInstance().getUsers(ids);
        Response response = call.execute();

        if (!response.isSuccessful()){
            throw new Exception(response.body().string());
        }

        String json = response.body().string();
        GetUsersResult result = new Gson().fromJson(json,GetUsersResult.class);

        if (result.getCode() == 2){
            throw new Exception(result.getMessage());
        }

        //本地用户的类转换成环信的用户类
        List<User> users = result.getDatas();
        List<EaseUser> easeUsers = CurrentUser.convertAll(users);

        return easeUsers;
    }
}
