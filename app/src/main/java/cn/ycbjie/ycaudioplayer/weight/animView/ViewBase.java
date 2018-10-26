
package cn.ycbjie.ycaudioplayer.weight.animView;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.pedaily.yc.ycdialoglib.toast.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

abstract class ViewBase extends GLSurfaceView implements GLSurfaceView.Renderer {

	public ViewBase(Context context) {
		super(context);
	}

	protected String loadRawString(int rawId) throws Exception {
		InputStream is = getContext().getResources().openRawResource(rawId);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len;
		while ((len = is.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}
		return baos.toString();
	}

	protected void showError(final String errorMsg) {
		post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.showRoundRectToast(errorMsg);
			}
		});
	}

}
