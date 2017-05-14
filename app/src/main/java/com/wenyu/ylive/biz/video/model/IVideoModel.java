package com.wenyu.ylive.biz.video.model;

import com.wenyu.mvp.model.IMvpModel;
import com.wenyu.xmpp.XmppClient;

/**
 * Created by chan on 17/4/24.
 */

public interface IVideoModel extends IMvpModel {
    XmppClient fetchXmppClient();
}
