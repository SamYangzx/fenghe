package com.gome.fenghe.utils;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class AudioRecordUtils {
    private static final String TAG = AudioRecordUtils.class.getSimpleName();
    static final int SAMPLE_RATE_IN_HZ = 16000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);

    private final static int[] NOISE_DIVIDES = new int[]{0, 40, 60, 70};//安静，吵，很吵，难以忍受
    private final static int[] VOLUME_DIVIDES = new int[]{3, 5, 6, 7};//安静，吵，很吵，难以忍受
    private final static int COUNT = NOISE_DIVIDES.length;

    private AudioRecord mAudioRecord;
    private boolean isGetVoiceRun;
    private Object mLock;

    public interface IVolumeListener {
        void volumeChanged(int volume);
    }

    private IVolumeListener mVolumeListener;

    public AudioRecordUtils() {
        mLock = new Object();
    }

    public void setListener(IVolumeListener listener) {
        mVolumeListener = listener;
    }

    public void getNoiseLevel() {
        if (isGetVoiceRun) {
            Log.e(TAG, "还在录着呢");
            return;
        }
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        if (mAudioRecord == null) {
            Log.e("sound", "mAudioRecord初始化失败");
        }
        isGetVoiceRun = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                short[] buffer = new short[BUFFER_SIZE];
                while (isGetVoiceRun) {
                    //r是实际读取的数据长度，一般而言r会小于buffersize  
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算  
                    for (int i = 0; i < buffer.length; i++) {
                        v += buffer[i] * buffer[i];
                    }
                    // 平方和除以数据总长度，得到音量大小。  
                    double mean = v / (double) r;
                    double volume = 10 * Math.log10(mean);
                    Log.w(TAG, "分贝值:" + volume);
                    calculateVolume((float) volume);
                    //一秒十次
                    synchronized (mLock) {
                        try {
                            mLock.wait(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
        }).start();
    }

    public void stopCalculateVolume(){
        isGetVoiceRun = false;
    }

    private int calculateVolumeIndex(float volume) {
        int volumeIndex = 0;
        for (int i = 0; i < COUNT; i++) {
            if (i < COUNT - 1) {
                if (volume >= NOISE_DIVIDES[i] && volume < NOISE_DIVIDES[i + 1]) {
                    volumeIndex = i;
                }
            } else {
                if (volume >= NOISE_DIVIDES[i]) {
                    volumeIndex = i;
                }
            }
        }
        return volumeIndex;
    }

    private void calculateVolume(float volume) {
        int volumeIndex = calculateVolumeIndex(volume);
        Log.d(TAG, "volumeIndex: " + volumeIndex);
        if (mVolumeListener != null) {
            mVolumeListener.volumeChanged(VOLUME_DIVIDES[volumeIndex]);
        }
    }

}