
package cn.ycbjie.ycaudioplayer.weight.animView;

import android.opengl.GLES20;
import android.util.Log;

import java.util.HashMap;

public final class EffectsShader {

	private int mIdProgram = 0;
	private int mIdShaderFragment = 0;
	private int mIdShaderVertex = 0;
	private final HashMap<String, Integer> mShaderHandleMap = new HashMap<String, Integer>();


	private void deleteProgram() {
		GLES20.glDeleteShader(mIdShaderFragment);
		GLES20.glDeleteShader(mIdShaderVertex);
		GLES20.glDeleteProgram(mIdProgram);
		mIdProgram = mIdShaderVertex = mIdShaderFragment = 0;
	}

	int getHandle(String name) {
		if (mShaderHandleMap.containsKey(name)) {
			return mShaderHandleMap.get(name);
		}
		int handle = GLES20.glGetAttribLocation(mIdProgram, name);
		if (handle == -1) {
			handle = GLES20.glGetUniformLocation(mIdProgram, name);
		}
		if (handle == -1) {
			Log.d("GlslShader", "Could not get attrib location for " + name);
		} else {
			mShaderHandleMap.put(name, handle);
		}
		return handle;
	}

	public int[] getHandles(String... names) {
		int[] res = new int[names.length];
		for (int i = 0; i < names.length; ++i) {
			res[i] = getHandle(names[i]);
		}
		return res;
	}

	private int loadShader(int shaderType, String source) throws Exception {
		int shader = GLES20.glCreateShader(shaderType);
		if (shader != 0) {
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			int[] compiled = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
			if (compiled[0] == 0) {
				String error = GLES20.glGetShaderInfoLog(shader);
				GLES20.glDeleteShader(shader);
				throw new Exception(error);
			}
		}
		return shader;
	}

	void setProgram(String vertexSource, String fragmentSource) throws Exception {
		mIdShaderVertex = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
		mIdShaderFragment = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
		int program = GLES20.glCreateProgram();
		if (program != 0) {
			GLES20.glAttachShader(program, mIdShaderVertex);
			GLES20.glAttachShader(program, mIdShaderFragment);
			GLES20.glLinkProgram(program);
			int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] != GLES20.GL_TRUE) {
				String error = GLES20.glGetProgramInfoLog(program);
				deleteProgram();
				throw new Exception(error);
			}
		}
		mIdProgram = program;
		mShaderHandleMap.clear();
	}

	void useProgram() {
		GLES20.glUseProgram(mIdProgram);
	}

}
