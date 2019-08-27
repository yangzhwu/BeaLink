package com.bealink.zhengwuy.bealink.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.bean.request.RegisterRequestBean;
import com.bealink.zhengwuy.bealink.bean.response.RegisterResponseBean;
import com.bealink.zhengwuy.bealink.im.ImHelper;
import com.bealink.zhengwuy.bealink.rxjava.InternetObserver;
import com.bealink.zhengwuy.bealink.utils.DialogUtil;
import com.bealink.zhengwuy.bealink.utils.DimenUtils;
import com.bealink.zhengwuy.bealink.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import org.devio.takephoto.app.TakePhotoFragment;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.TResult;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class RegisterFragment extends TakePhotoFragment {
    private ImageView mHeaderImageView;
    private BottomSheetDialog mBottomSheetDialog;
    private Context mContext;
    private LinearLayout mSelectFromAlbumLl, mSelectFromTakePhotoLl, mCancelSelectLl;
    private static final int HEADER_WIDTH_DP = 100;
    private static final int HEADER_HEIGHT_DP = 100;
    private CropOptions mCropOptions;
    private Uri mHeaderImageUri;
    private TextView mRegisterTv;
    private TextView mAccountTv, mPswTv;
    private String mAccount, mPsw;
    private SweetAlertDialog mSweetAlertDialog;
    private RegisterListener mRegisterListener;

    public void setRegisterListener(RegisterListener registerListener) {
        mRegisterListener = registerListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        initData();
        initView(rootView);
        initListener();
        return rootView;
    }

    private void initData() {
        mCropOptions = getCropOptions();
        File headerImageFile = new File(mContext.getExternalCacheDir(), "header.jpg");
        mHeaderImageUri = Uri.fromFile(headerImageFile);
    }

    private void initView(View rootView) {
        mBottomSheetDialog = new BottomSheetDialog(mContext);
        mBottomSheetDialog.setContentView(R.layout.select_photo);
        mSelectFromAlbumLl = mBottomSheetDialog.findViewById(R.id.select_from_gallery_ll);
        mSelectFromTakePhotoLl = mBottomSheetDialog.findViewById(R.id.select_from_take_photo_ll);
        mCancelSelectLl = mBottomSheetDialog.findViewById(R.id.cancel_select_ll);

        mHeaderImageView = rootView.findViewById(R.id.register_upload_head_image);
        mRegisterTv = rootView.findViewById(R.id.register_btn);
        mAccountTv = rootView.findViewById(R.id.account_tv);
        mPswTv = rootView.findViewById(R.id.psw_tv);
        mSweetAlertDialog = DialogUtil.creatCommonLoadingDialog(getActivity(), "正在注册中...");
    }

    private void initListener() {
        //弹出菜单
        mHeaderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.show();
            }
        });

        //从相册选择
        mSelectFromAlbumLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTakePhoto().onPickFromGalleryWithCrop(mHeaderImageUri, mCropOptions);
                mBottomSheetDialog.dismiss();
            }
        });

        //拍照选择
        mSelectFromTakePhotoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTakePhoto().onPickFromCaptureWithCrop(mHeaderImageUri, mCropOptions);
                mBottomSheetDialog.dismiss();

            }
        });

        //取消选择
        mCancelSelectLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        mRegisterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    mSweetAlertDialog.show();
                    RegisterRequestBean registerRequestBean = new RegisterRequestBean();
                    registerRequestBean.setAccount(mAccount);
                    registerRequestBean.setPassword(mPsw);
                    ImHelper.getInstance().register(registerRequestBean, new InternetObserver<RegisterResponseBean>() {
                        @Override
                        public void handlerError() {
                            ToastUtils.show("注册失败");
                            mSweetAlertDialog.dismiss();
                        }

                        @Override
                        public void onNext(RegisterResponseBean registerResponseBean) {
                            ToastUtils.show("注册成功");
                            mSweetAlertDialog.dismiss();
                            if (mRegisterListener != null) {
                                mRegisterListener.onRegisterSucess(registerResponseBean);
                            }
                        }
                    });
                }

            }
        });
    }

    private boolean checkInput() {
        mAccount = mAccountTv.getText().toString().trim();
        mPsw = mPswTv.getText().toString().trim();
        if (TextUtils.isEmpty(mAccount) || TextUtils.isEmpty(mPsw)) {
            ToastUtils.show("用户名和密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //选择头像成功
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Glide.with(this)
                .load(new File(result.getImage().getOriginalPath()))
                .signature(new ObjectKey(String.valueOf(SystemClock.currentThreadTimeMillis())))
                .centerCrop()
                .into(mHeaderImageView);
    }

    private CropOptions getCropOptions() {
        int height = DimenUtils.dp2px(mContext, HEADER_HEIGHT_DP);
        int width = DimenUtils.dp2px(mContext, HEADER_WIDTH_DP);
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(false);
        return builder.create();
    }



    public interface RegisterListener {
        void onRegisterSucess(RegisterResponseBean registerResponseBean);
    }

}
