package cn.ycbjie.ycaudioplayer.util.other;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Created by yc on 2018/3/6.
 * 仅仅是为了方便学习
 */

public class AudioVideoUtils {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void muxingAudioAndVideo(String mOutputVideoPath , String mVideoPath , String mAudioPath) throws IOException {
        MediaMuxer mMediaMuxer = new MediaMuxer(mOutputVideoPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        // 视频的MediaExtractor
        // MediaExtractor的接口比较简单，通过setDataSource()设置数据源，数据源可以是本地文件地址，也可以是网络地址
        MediaExtractor mVideoExtractor = new MediaExtractor();
        mVideoExtractor.setDataSource(mVideoPath);
        int videoTrackIndex = -1;
        // 然后可以通过getTrackFormat(int index)来获取各个track的MediaFormat，
        // 通过MediaFormat来获取track的详细信息，如：MimeType、分辨率、采样频率、帧率等等：
        for (int i = 0; i < mVideoExtractor.getTrackCount(); i++) {
            // 获取视频编码类型
            MediaFormat format = mVideoExtractor.getTrackFormat(i);
            if (format.getString(MediaFormat.KEY_MIME).startsWith("video/")) {
                // 获取到track的详细信息后，通过selectTrack(int index)选择指定的通道
                // 切换到视频信道
                mVideoExtractor.selectTrack(i);
                // 把MediaFormat添加到MediaMuxer后记录返回的track index，添加完所有track后调用start方法
                videoTrackIndex = mMediaMuxer.addTrack(format);
                break;
            }
        }


        // 添加完所有轨道后start mMediaMuxer.start();
        // 封装视频track
        if (-1 != videoTrackIndex) {
            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            info.presentationTimeUs = 0;
            ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
            // 指定通道之后就可以从MediaExtractor中读取数据了
            while (true) {
                int sampleSize = mVideoExtractor.readSampleData(buffer, 0);
                if (sampleSize < 0) {
                    break;
                }
                info.offset = 0;
                // info.size 必须填入数据的大小
                info.size = sampleSize;
                // info.flags 需要给出是否为同步帧/关键帧
                info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
                // info.presentationTimeUs 必须给出正确的时间戳，注意单位是 us
                info.presentationTimeUs = mVideoExtractor.getSampleTime();
                // 调用MediaMuxer.writeSampleData()向mp4文件中写入数据了。
                // 这里要注意每次只能添加一帧视频数据或者单个Sample的音频数据，并且BufferInfo对象的值一定要设置正确：
                mMediaMuxer.writeSampleData(videoTrackIndex, buffer, info);
                mVideoExtractor.advance();
            }
        }



        // 音频的MediaExtractor
        MediaExtractor mAudioExtractor = new MediaExtractor();
        mAudioExtractor.setDataSource(mAudioPath);
        int audioTrackIndex = -1;
        for (int i = 0; i < mAudioExtractor.getTrackCount(); i++) {
            MediaFormat format = mAudioExtractor.getTrackFormat(i);
            if (format.getString(MediaFormat.KEY_MIME).startsWith("audio/")) {
                mAudioExtractor.selectTrack(i);
                audioTrackIndex = mMediaMuxer.addTrack(format);
            }
        }


        // 封装音频track
        if (-1 != audioTrackIndex) {
            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            info.presentationTimeUs = 0;
            ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
            while (true) {
                int sampleSize = mAudioExtractor.readSampleData(buffer, 0);
                if (sampleSize < 0) {
                    break;
                }
                info.offset = 0;
                info.size = sampleSize;
                info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
                info.presentationTimeUs = mAudioExtractor.getSampleTime();
                mMediaMuxer.writeSampleData(audioTrackIndex, buffer, info);
                mAudioExtractor.advance();
            }
        }


        // 释放MediaExtractor
        mVideoExtractor.release();
        mAudioExtractor.release();


        // 释放MediaMuxer，结束写入后关闭以及释放资源
        mMediaMuxer.stop();
        mMediaMuxer.release();
    }


    /**
     * 音频编码
     * 比如，将录音文件进行编码得到的文件就是AAC音频文件了，一般Android系统自带的播放器都可以直接播放
     *
     * 音频解码
     * 比如，将某个音频资源解码出来，于是就可以播放了
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startAudioEncoded(){
        // 录音编码的mime
        String mime = "audio/mp4a-latm";
        // 编码的key bit rate
        int rate = 256000;
        int sampleRate = 90;
        int channelCount = 1;


        //相对于上面的音频录制，我们需要一个编码器的实例
        MediaFormat format=MediaFormat.createAudioFormat(mime ,sampleRate,channelCount);
        format.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
        format.setInteger(MediaFormat.KEY_BIT_RATE, rate);
        MediaCodec mEnc = null;
        try {
            mEnc = MediaCodec.createEncoderByType(mime);
            //设置为编码器
            mEnc.configure(format,null,null,MediaCodec.CONFIGURE_FLAG_ENCODE);
            //同样，在设置录音开始的时候，也要设置编码开始
            mEnc.start();
            //之前的音频录制是直接循环读取，然后写入文件，这里需要做编码处理再写入文件
            //这里的处理就是和之前传送带取盒子放原料的流程一样了，注意一般在子线程中循环处理
            int index=mEnc.dequeueInputBuffer(-1);
            if(index>=0){
                final ByteBuffer buffer = mEnc.getInputBuffer(index);
                buffer.clear();
//                int length = mRecorder.read(buffer,1024*10);
                int length = 10;
                if(length>0){
                    mEnc.queueInputBuffer(index,0,length,System.nanoTime()/1000,0);
                }
            }
            MediaCodec.BufferInfo mInfo=new MediaCodec.BufferInfo();


            int outIndex;
            //每次取出的时候，把所有加工好的都循环取出来
            do{
                outIndex = mEnc.dequeueOutputBuffer(mInfo,0);
                if(outIndex>=0){
                    ByteBuffer buffer= mEnc.getOutputBuffer(outIndex);
                    buffer.position(mInfo.offset);
                    //AAC编码，需要加数据头，AAC编码数据头固定为7个字节
                    byte[] temp=new byte[mInfo.size+7];
                    buffer.get(temp,7,mInfo.size);
                    addADTStoPacket(temp,temp.length);
//                    fos.write(temp);
                    mEnc.releaseOutputBuffer(outIndex,false);
                }else if(outIndex ==MediaCodec.INFO_TRY_AGAIN_LATER){

                }else if(outIndex==MediaCodec.INFO_OUTPUT_FORMAT_CHANGED){

                }
            }while (outIndex>=0);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //编码停止，发送编码结束的标志，循环结束后，停止并释放编码器
            assert mEnc != null;
            mEnc.stop();
            mEnc.release();
        }
    }


    /**
     * 给编码出的aac裸流添加adts头字段
     * @param packet        要空出前7个字节，否则会搞乱数据
     * @param packetLen     1
     */
    private void addADTStoPacket(byte[] packet, int packetLen) {
        //AAC LC
        int profile = 2;
        //44.1KHz
        int freqIdx = 4;
        //CPE
        int chanCfg = 2;
        packet[0] = (byte)0xFF;
        packet[1] = (byte)0xF9;
        packet[2] = (byte)(((profile-1)<<6) + (freqIdx<<2) +(chanCfg>>2));
        packet[3] = (byte)(((chanCfg&3)<<6) + (packetLen>>11));
        packet[4] = (byte)((packetLen&0x7FF) >> 3);
        packet[5] = (byte)(((packetLen&7)<<5) + 0x1F);
        packet[6] = (byte)0xFC;
    }


}
