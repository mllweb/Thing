package com.mllweb.thing.ui.adapter.main.message;

import android.app.Activity;
import android.widget.ImageView;

import com.jauker.widget.BadgeView;
import com.mllweb.model.Message;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Android on 2016/5/18.
 */
public class MessageAdapter extends BaseRecyclerAdapter<Message> {

    public MessageAdapter(List<Message> mData, Activity activity) {
        super(mData, activity);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_message;
    }


    @Override
    protected void onBind(BaseHolder holder, Message thing) {
        ImageView headImage=holder.getView(R.id.iv_head_image);
        BadgeView badge=null;
        if(headImage.getTag()==null){
            badge=new BadgeView(mActivity);
            badge.setTargetView(headImage);
            headImage.setTag(badge);
        }else{
            badge= (BadgeView) headImage.getTag();
        }
        badge.setBadgeCount(holder.getPosition()-5);
    }
}
