package cn.ycbjie.ycaudioplayer.base;

import cn.ycbjie.ycaudioplayer.ui.me.MeFragment;
import cn.ycbjie.ycaudioplayer.ui.music.MusicFragment;
import cn.ycbjie.ycaudioplayer.ui.music.local.LocalMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.music.onLine.OnLineMusicFragment;
import cn.ycbjie.ycaudioplayer.ui.practise.PractiseFragment;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.PractiseAfterFragment;
import cn.ycbjie.ycaudioplayer.ui.practise.ui.PractiseBeforeFragment;
import cn.ycbjie.ycaudioplayer.ui.study.ui.fragment.HomeFragment;
import cn.ycbjie.ycaudioplayer.ui.study.ui.fragment.InnovationFragment;
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
    private HomeFragment mHomeFragment;
    private StudyFragment mStudyFragment;
    private InnovationFragment mInnovationFragment;
    private PractiseFragment mPractiseFragment;
    private PractiseBeforeFragment mPractiseBeforeFragment;
    private PractiseAfterFragment mPractiseAfterFragment;
    private MusicFragment mMusicFragment;
    private MeFragment mMeFragment;
    private LocalMusicFragment mLocalMusicFragment;
    private OnLineMusicFragment mOnLineMusicFragment;

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

    public HomeFragment getHomeFragment() {
        if (mHomeFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
            }
        }
        return mHomeFragment;
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

    public InnovationFragment getInnovationFragment() {
        if (mInnovationFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mInnovationFragment == null) {
                    mInnovationFragment = new InnovationFragment();
                }
            }
        }
        return mInnovationFragment;
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




    public PractiseBeforeFragment getPractiseBeforeFragment() {
        if (mPractiseBeforeFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mPractiseBeforeFragment == null) {
                    mPractiseBeforeFragment = new PractiseBeforeFragment();
                }
            }
        }
        return mPractiseBeforeFragment;
    }




    public PractiseAfterFragment getPractiseAfterFragment() {
        if (mPractiseAfterFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mPractiseAfterFragment == null) {
                    mPractiseAfterFragment = new PractiseAfterFragment();
                }
            }
        }
        return mPractiseAfterFragment;
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

}
