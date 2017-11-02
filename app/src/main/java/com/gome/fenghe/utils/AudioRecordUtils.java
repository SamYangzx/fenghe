package com.gome.fenghe.utils;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fenghe on 2017/10/22.
 */
public class AudioRecordUtils {
    private static final String TAG = AudioRecordUtils.class.getSimpleName();
    static final int SAMPLE_RATE_IN_HZ = 16000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);

    private final static int[] NOISE_DIVIDES = new int[]{0, 45, 60, 70, 90};//安静，吵，很吵，难以忍受
    private final static int VOICE_DB_INDEX = 3;
    private final static int VOICE_DB = NOISE_DIVIDES[VOICE_DB_INDEX]; //人说话声音db,为方便计算，通过动态，调整第3个值来处理。
    private final static float NOISE_DB_PRECENT = 0.6f;
    private final static float VERY_NOISE_DB_PRECENT = 0.9f;
//    private final static float SPEAK_GAP_PRECENT = 0.2f; //人说话声音,间隙时间
//    private final static float MIN_KEEP_PRECENT = 0.3f; //要调动音量，声音应该维持的最小概率。
//    private final static int MAX_NOISE = 1000;

    private final static int[] VOLUME_DIVIDES = new int[]{2, 5, 6, 7, 7};//安静，吵，很吵，难以忍受
    private final static int VOLUME_ZONE_COUNT = NOISE_DIVIDES.length;

    //    private final static int VOLUME_LAST_MS = 1000; //ms
    private final static int COMPUTE_DB_FREQUENCY = 10; //how many times one second to compute volume db.
    private final static int SAVE_MAX_VOLUME_SECONDS = 4; //存储多长时间内的数据

    private AudioRecord mAudioRecord;
    private boolean isGetVoiceRun;
    private Object mLock;

    private int mOutInitVolume = 0;

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

    public void adjustVolume(int initVolume) {
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
        mOutInitVolume = initVolume;

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
                    int volume = (int) (10 * Math.log10(mean));
                    LogUtils.d(TAG, "volume db:" + volume);
//                    addVolumeDb(volume);
                    computeVolume(volume);
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

    public void stopCalculateVolume() {
        isGetVoiceRun = false;
    }

    private int computeVolumeIndex(int volume) {
        int volumeIndex = 0;
        for (int i = 0; i < VOLUME_ZONE_COUNT; i++) {
            if (i < VOLUME_ZONE_COUNT - 1) {
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

    /**
     * 根据声音所处噪音区间，计算音量
     *
     * @param volume
     */
    private void computeVolume(int volume) {
        if (!mHasInitVolume) {
            initVolume(volume);
            mVolumeRecordUtils.initData();
        } else {
            mVolumeRecordUtils.addVolumeDb(volume);
            int computeVolumeIndex = mVolumeRecordUtils.getVolumeZone();

            LogUtils.d(TAG, "computeVolumeIndex: " + computeVolumeIndex + " ---mPresentVolumeZone: " + mPresentVolumeZone);
            if (mVolumeListener != null && computeVolumeIndex != mPresentVolumeZone) {
                mPresentVolumeZone = computeVolumeIndex;
                mVolumeListener.volumeChanged(VOLUME_DIVIDES[computeVolumeIndex]);
            }
        }
    }

    //    private int pastTime = 0;  //noise value.
//    private int pastProbablity = 0;
//    private int presentTime = 0;
    private int mPresentVolumeZone = 0; //当前环境音所处区间
//    private int presentZoneMinDb = 0;
//    private int presentZoneMaxDb = 0;
//    private int presentProbablity = 0;
//
//    private int minVolumeDb = 1000; //init
//    private int maxVolumeDb = 0;

    private int mCount = 0;
    private static final int AVERAGR_NUMBER = 10;
    private boolean mHasInitVolume = false;

    /**
     * 初始化噪音相关参数等。等采集了10 个数据后（认为声音稳定后）开始计算。
     *
     * @param volume
     */
    private void initVolume(int volume) {
        if (!mHasInitVolume) {
            if (mCount < AVERAGR_NUMBER) {
                mCount++;
            } else {
                mPresentVolumeZone = computeVolumeIndex(volume);
                mHasInitVolume = true;
//                computeMaxMinDb(mPresentVolumeZone);
                if(mOutInitVolume != VOLUME_DIVIDES[mPresentVolumeZone]){
                    mVolumeListener.volumeChanged(VOLUME_DIVIDES[mPresentVolumeZone]);
                }
            }
        }
    }

    /**
     * 计算当前区间的噪音最大值和最小值
     *
     * @param index
     */
//    private void computeMaxMinDb(int index) {
//        if (index == VOLUME_ZONE_COUNT - 1) {
//            presentZoneMinDb = NOISE_DIVIDES[VOLUME_ZONE_COUNT - 1];
//            presentZoneMaxDb = MAX_NOISE;
//        } else {
//            presentZoneMinDb = NOISE_DIVIDES[index];
//            presentZoneMaxDb = NOISE_DIVIDES[index + 1];
//        }
//    }

    private VolumeRecordUtils mVolumeRecordUtils = new VolumeRecordUtils();

    class VolumeRecordUtils {
        private List<Integer> mQueue = new LinkedList<>();
        private final static int SAVE_VOLUME_COUNT = COMPUTE_DB_FREQUENCY * SAVE_MAX_VOLUME_SECONDS; //长时间计算，短时间计算。
        private final static int LATEST_VOLUME_COUNT = COMPUTE_DB_FREQUENCY * 2; //最近XX秒内声音响度的频率超过一定阈值了。
        private int firstVolume, firstVolumeZone;

        private int[] totalCountZone = new int[VOLUME_ZONE_COUNT];
        private int[] latestXsCountZone = new int[VOLUME_ZONE_COUNT];
        private float[] totalFreqZone = new float[VOLUME_ZONE_COUNT];//5s 内的频率统计。
        private float[] latestXsFreqZone = new float[VOLUME_ZONE_COUNT]; //最近2s的频率。

        private void addVolumeDb(int db) {
            if (mQueue.size() >= SAVE_VOLUME_COUNT) {
                firstVolume = mQueue.get(0);
//                totalVolumeValue = totalVolumeValue - firstVolume + db;
                mQueue.remove(0);
//                totalVolumeValue
            }
            mQueue.add(db);
            updateFreqZone();
        }

        void initData() {
            firstVolume = mPresentVolumeZone;
            firstVolumeZone = computeVolumeIndex(mPresentVolumeZone); //此初始值已经被计算出来。
            mQueue.clear();
            for (int i = 0; i < SAVE_VOLUME_COUNT; i++) {
                mQueue.add(firstVolume);
            }
            for (int i = 0; i < VOLUME_ZONE_COUNT; i++) {
                if (i == firstVolumeZone) {
                    totalCountZone[i] = SAVE_VOLUME_COUNT;
                    totalFreqZone[i] = 1f;

                    latestXsCountZone[i] = LATEST_VOLUME_COUNT;
                    latestXsFreqZone[i] = 1f;
                } else {
                    totalCountZone[i] = 0;
                    totalFreqZone[i] = 0f;

                    latestXsCountZone[i] = 0;
                    latestXsFreqZone[i] = 0f;
                }
            }
        }

        private void updateFreqZone() {
            int firstVolumeZone = computeVolumeIndex(firstVolume);

            if (totalCountZone[firstVolumeZone] > 0) {
                totalCountZone[firstVolumeZone]--;
                totalFreqZone[firstVolumeZone] = (float) totalCountZone[firstVolumeZone] / SAVE_VOLUME_COUNT;
            }
            int newVolumeZone = computeVolumeIndex(mQueue.get(SAVE_VOLUME_COUNT - 1));
            LogUtils.d(TAG, "newVolumeZone: " + newVolumeZone);
            if (totalCountZone[newVolumeZone] < SAVE_VOLUME_COUNT) {
                totalCountZone[newVolumeZone]++;
                totalFreqZone[newVolumeZone] = (float) totalCountZone[newVolumeZone] / SAVE_VOLUME_COUNT;
            }

            int latestXsVolumeZone = computeVolumeIndex(mQueue.get(SAVE_VOLUME_COUNT - LATEST_VOLUME_COUNT));
            if (latestXsCountZone[latestXsVolumeZone] > 0) {
                latestXsCountZone[latestXsVolumeZone]--;
                latestXsFreqZone[latestXsVolumeZone] = (float) latestXsCountZone[latestXsVolumeZone] / LATEST_VOLUME_COUNT;
            }
            if (latestXsCountZone[newVolumeZone] < LATEST_VOLUME_COUNT) {
                latestXsCountZone[newVolumeZone]++;
                latestXsFreqZone[newVolumeZone] = (float) latestXsCountZone[newVolumeZone] / LATEST_VOLUME_COUNT;
            }
            printArray(totalFreqZone);
            LogUtils.v(TAG, "start print---  latestXsFreqZone");
            printArray(latestXsFreqZone);

        }


        /**
         * 方法一：由于人说话后声音强度会轻易超过VOICE_DB, 若在一定时间段内麦克风中的声音强度低于VOICE_DB的概率达到
         * {@link #NOISE_DB_PRECENT}, 我们就认为此段时间内只包含噪音。
         * 方法二：由于人在说话的过程中，声音会有短暂的回落，若在较长一段事件内麦克风中的声音强度高于VOICE_DB的概率达到
         * {@link #VERY_NOISE_DB_PRECENT},我们认为此端时间内噪音达到了XXdb.
         *
         * @return 计算声音音量区间
         */
        private int getVolumeZone() {
            int latestVolumeZone = getMaxIndex(latestXsCountZone);
            LogUtils.v(TAG, "getVolumeZone.latestVolumeZone: " + latestVolumeZone);
            if (latestVolumeZone < VOICE_DB_INDEX) { //确定没人说话。
                if (latestXsFreqZone[latestVolumeZone] > NOISE_DB_PRECENT) {
                    return latestVolumeZone;
                }
            } else {
//                int totalMaxProb = getMaxIndex(totalCountZone);
                float totalHigherVoiceDb = 0;
                for (int i = VOICE_DB_INDEX; i < VOLUME_ZONE_COUNT; i++) {
                    totalHigherVoiceDb += totalFreqZone[i];
                }
                if (totalHigherVoiceDb >= VERY_NOISE_DB_PRECENT) {//噪音较大时，
                    return latestVolumeZone;
                }

            }

            return mPresentVolumeZone;
        }


        public int getMaxIndex(int[] arr) {
            int max = arr[0];
            int index = 0;
            for (int x = 1; x < arr.length; x++) {
                if (arr[x] > max) {
                    max = arr[x];
                    index = x;
                }

            }
            return index;
        }

    }

    private void printArray(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            LogUtils.d(TAG, "i: " + arr[i]);
        }
    }


}