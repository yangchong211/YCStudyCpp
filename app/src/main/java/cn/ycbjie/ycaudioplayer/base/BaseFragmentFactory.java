package cn.ycbjie.ycaudioplayer.base;

import cn.ycbjie.ycaudioplayer.ui.me.MeFragment;
import cn.ycbjie.ycaudioplayer.ui.music.MusicFragment;
import cn.ycbjie.ycaudioplayer.ui.music.cut.CutEditMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.music.local.LocalMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.OnLineMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.practise.PractiseFragment;
import cn.ycbjie.ycaudioplayer.ui.study.ui.fragment.StudyFragment;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/12/22
 * 描    述：Fragment工厂
 * 修订历史：
 *      备注：看《Android源码设计》一书，学习设计模式并运用
 * ================================================
 */
public class BaseFragmentFactory {

    private static BaseFragmentFactory mInstance;
    private StudyFragment mStudyFragment;
    private PractiseFragment mPractiseFragment;
    private MusicFragment mMusicFragment;
    private MeFragment mMeFragment;
    private LocalMusicFragment mLocalMusicFragment;
    private OnLineMusicFragment mOnLineMusicFragment;
    private CutEditMusicFragment mCutEditMusicFragment;

    private BaseFragmentFactory() {}

    public static BaseFragmentFactory getInstance() {
        if (mInstance == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mInstance == null) {
                    mInstance = new BaseFragmentFactory();
                }
            }
        }
        return mInstance;
    }


    public StudyFragment getStudyFragment() {
        if (mStudyFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mStudyFragment == null) {
                    mStudyFragment = new StudyFragment();
                }
            }
        }
        return mStudyFragment;
    }


    public PractiseFragment getPractiseFragment() {
        if (mPractiseFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mPractiseFragment == null) {
                    mPractiseFragment = new PractiseFragment();
                }
            }
        }
        return mPractiseFragment;
    }


    public MusicFragment getMusicFragment() {
        if (mMusicFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mMusicFragment == null) {
                    mMusicFragment = new MusicFragment();
                }
            }
        }
        return mMusicFragment;
    }


    public MeFragment getMeFragment() {
        if (mMeFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mMeFragment == null) {
                    mMeFragment = new MeFragment();
                }
            }
        }
        return mMeFragment;
    }

    public LocalMusicFragment getLocalMusicFragment() {
        if (mLocalMusicFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mLocalMusicFragment == null) {
                    mLocalMusicFragment = new LocalMusicFragment();
                }
            }
        }
        return mLocalMusicFragment;
    }


    public OnLineMusicFragment getOnLineMusicFragment() {
        if (mOnLineMusicFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mOnLineMusicFragment == null) {
                    mOnLineMusicFragment = new OnLineMusicFragment();
                }
            }
        }
        return mOnLineMusicFragment;
    }


    public CutEditMusicFragment getCutEditMusicFragment() {
        if (mCutEditMusicFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mCutEditMusicFragment == null) {
                    mCutEditMusicFragment = new CutEditMusicFragment();
                }
            }
        }
        return mCutEditMusicFragment;
    }


}
