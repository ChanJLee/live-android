package com.wenyu.ylive.biz.home.nav.model;

import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.ylive.common.bean.User;

/**
 * Created by chan on 17/4/4.
 */

public interface IHomeNavModel extends IMvpModel {
    User getCurrentUser();
}
