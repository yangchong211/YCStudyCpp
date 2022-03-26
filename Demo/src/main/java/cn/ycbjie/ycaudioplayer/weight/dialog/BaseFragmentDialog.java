package cn.ycbjie.ycaudioplayer.weight.dialog;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;

import cn.ycbjie.ycaudioplayer.R;


public abstract class BaseFragmentDialog extends DialogFragment {

    private static final String TAG = "base_bottom_dialog";
    private Local local = Local.BOTTOM;
    private final View v;
    public enum Local {
        TOP, CENTER, BOTTOM
    }
    private static final float DEFAULT_DIM = 0.2f;


    public BaseFragmentDialog(Context context) {
        v = View.inflate(context,getLayoutRes(),null);
        bindView(v);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getDialog()!=null){
            //noinspection ConstantConditions
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setCanceledOnTouchOutside(getCancelOutside());
            getDialog().setCancelable(true);
            getDialog().setCanceledOnTouchOutside(true);
        }
        return v;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = getDimAmount();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        if (getHeight() > 0) {
            params.height = getHeight();
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        if (local == Local.TOP) {
            params.gravity = Gravity.TOP;
        } else if (local == Local.CENTER) {
            params.gravity = Gravity.CENTER;
        } else {
            params.gravity = Gravity.BOTTOM;
        }
        window.setAttributes(params);
    }


    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void bindView(View v);


    public int getHeight() {
        return -1;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public boolean getCancelOutside() {
        return false;
    }

    public String getFragmentTag() {
        return TAG;
    }

    public void dialogClose() {
        Dialog dialog = getDialog();
        if (dialog != null){
            dialog.cancel();
        }
    }

    public void show(FragmentManager fragmentManager) {
        if(fragmentManager!=null){
            show(fragmentManager, getFragmentTag());
        }else {
            Log.e("show","需要设置setFragmentManager");
        }
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(v!=null){
             ViewParent vp= v.getParent();
                if(vp!=null){
                    ((ViewGroup)vp).removeView(v);
                }
        }
    }
}
