package com.otherhshe.niceread.rx;

import android.content.Context;
import android.widget.Toast;

import com.otherhshe.niceread.NiceReadApplication;
import com.otherhshe.niceread.utils.NetUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Author: Othershe
 * Time:  2016/8/11 17:45
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private boolean mIsShowLoading;//是否显示加载loading

    public RxSubscriber(boolean isShowLoading) {
        mContext = NiceReadApplication.getContext();
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onCompleted() {
        cancelLoading();
    }

    @Override
    public void onError(Throwable e) {
        //统一处理请求异常的情况
        if (NetUtil.isConnected(mContext) || e instanceof SocketTimeoutException || e instanceof ConnectException) {
            Toast.makeText(mContext, "网络链接异常...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        _onError();

        cancelLoading();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading();
    }

    private void showLoading() {
        if (mIsShowLoading) {

        }
    }

    private void cancelLoading() {

    }

    protected abstract void _onNext(T t);

    protected abstract void _onError();

}