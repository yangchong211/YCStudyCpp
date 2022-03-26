
package cn.ycbjie.ycaudioplayer.weight.animView;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cn.ycbjie.ycaudioplayer.R;

public class ViewStars extends ViewBase {

	private ByteBuffer mBufferVertices;
	private long mLastRenderTime = -1;
	private float[] mMatrixModelViewProjection = new float[16];
	private float[] mMatrixProjection = new float[16];
	private float[] mMatrixView = new float[16];
	private boolean[] mShaderCompilerSupport = new boolean[1];
	private EffectsShader mShaderStar = new EffectsShader();
	private Star[] mStarArray = new Star[2000];

	public ViewStars(Context context) {
		super(context);
		for (int i = 0; i < mStarArray.length; ++i) {
			mStarArray[i] = new Star();
		}
		final byte[] VERTICES = { -1, 1, -1, -1, 1, 1, 1, -1 };
		mBufferVertices = ByteBuffer.allocateDirect(2 * 4);
		mBufferVertices.put(VERTICES).position(0);
		setEGLContextClientVersion(2);
		setRenderer(this);
		setRenderMode(RENDERMODE_CONTINUOUSLY);
	}


	@Override
	public void onDrawFrame(GL10 unused) {
		GLES20.glClearColor(0f, 0f, 0f, 1f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

		if (!mShaderCompilerSupport[0]) {
			return;
		}

		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		long time = SystemClock.uptimeMillis();

		if (mLastRenderTime == -1) {
			for (Star star : mStarArray) {
				star.randomize();
				star.mPosition[2] = (float) (Math.random() * 4 - 2);
			}
			mLastRenderTime = time;
		}

		float t = (time - mLastRenderTime) / 1000f;
		mLastRenderTime = time;
		mShaderStar.useProgram();
		GLES20.glUniformMatrix4fv(mShaderStar.getHandle("uModelViewProjectionM"),
				1, false, mMatrixModelViewProjection, 0);
		GLES20.glUniform1f(mShaderStar.getHandle("uSize"), .01f);
		GLES20.glVertexAttribPointer(mShaderStar.getHandle("aPosition"), 2,
				GLES20.GL_BYTE, false, 0, mBufferVertices);
		GLES20.glEnableVertexAttribArray(mShaderStar.getHandle("aPosition"));
		for (Star star : mStarArray) {
			star.mPosition[2] += star.mSpeed * t;
			if (star.mPosition[2] > 2) {
				star.randomize();
			}
		}
		Arrays.sort(mStarArray, new Comparator<Star>() {
			@Override
			public int compare(Star arg0, Star arg1) {
				return arg0.mPosition[2] < arg1.mPosition[2] ? -1 : 1;
			}
		});
		for (Star star : mStarArray) {
			GLES20.glUniform3fv(mShaderStar.getHandle("uPosition"), 1, star.mPosition, 0);
			GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
		}
		GLES20.glDisable(GLES20.GL_BLEND);
	}


	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		float aspect = (float) width / height;
		Matrix.perspectiveM(mMatrixProjection, 0, 60f, aspect, .1f, 10f);
		Matrix.setLookAtM(mMatrixView, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
		Matrix.multiplyMM(mMatrixModelViewProjection, 0, mMatrixProjection, 0, mMatrixView, 0);
		mLastRenderTime = -1;
	}


	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		GLES20.glGetBooleanv(GLES20.GL_SHADER_COMPILER, mShaderCompilerSupport, 0);
		if (!mShaderCompilerSupport[0]) {
			String msg = "OpenGL error; GLSL shader compiler not supported.";
			showError(msg);
			return;
		}

		try {
			String vertexSource = loadRawString(R.raw.star_vs);
			String fragmentSource = loadRawString(R.raw.star_fs);
			mShaderStar.setProgram(vertexSource, fragmentSource);
		} catch (Exception ex) {
			showError(ex.getMessage());
		}
	}


	private class Star {
		float[] mPosition = new float[3];
		float mSpeed;

		void randomize() {
			mPosition[0] = (float) (Math.random() * 2 - 1);
			mPosition[1] = (float) (Math.random() * 2 - 1);
			mPosition[2] = -2f;
			mSpeed = (float) (Math.random() * 0.2 + 0.2);
		}
	}

}
