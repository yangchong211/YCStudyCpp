package cn.ycbjie.ycaudioplayer.ui.advert.utils;

import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class DownLoadUtils {

    public interface DownLoadInterFace {
        /**
         * 下载完后调用该方法
         * @param savePaths         保存的路径集合
         */
        void afterDownLoad(ArrayList<String> savePaths);
    }

    public static void downLoad(String savePath, DownLoadInterFace downLoadInterFace, String... download) {
        new DownLoadTask(savePath, downLoadInterFace).execute(download);
    }

    private static class DownLoadTask extends AsyncTask<String, Integer, ArrayList<String>> {
        private String mSavePath;
        private DownLoadInterFace mDownLoadInterFace;

        private DownLoadTask(String savePath, DownLoadInterFace downLoadTask) {
            this.mSavePath = savePath;
            this.mDownLoadInterFace = downLoadTask;
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> names = new ArrayList<>();
            for (String url : params) {
                if (!TextUtils.isEmpty(url)) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        // 获得存储卡的路径
                        FileOutputStream fos = null;
                        InputStream is = null;
                        try {
                            URL downUrl = new URL(url);
                            // 创建连接
                            HttpURLConnection conn = (HttpURLConnection) downUrl.openConnection();
                            conn.connect();
                            // 创建输入流
                            is = conn.getInputStream();
                            File file = new File(mSavePath);
                            // 判断文件目录是否存在
                            if (!file.exists()) {
                                //noinspection ResultOfMethodCallIgnored
                                file.mkdirs();
                            }

                            String[] split = url.split("/");
                            String fileName = split[split.length - 1];
                            File mApkFile = new File(mSavePath, fileName);
                            names.add(mApkFile.getAbsolutePath());
                            fos = new FileOutputStream(mApkFile, false);
                            int count = 0;
                            // 缓存
                            byte buf[] = new byte[1024];
                            while (true) {
                                int read = is.read(buf);
                                if (read == -1) {
                                    break;
                                }
                                fos.write(buf, 0, read);
                                count += read;
                                publishProgress(count);
                            }
                            fos.flush();

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (is != null) {
                                    is.close();
                                }
                                if (fos != null) {
                                    fos.close();
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
            return names;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            if (mDownLoadInterFace != null) {
                mDownLoadInterFace.afterDownLoad(strings);
            }
        }
    }
}
