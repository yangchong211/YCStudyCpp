package cn.ycbjie.ycaudioplayer.ui.news.model;

import java.util.List;

/**
 * Created by yc on 2018/3/21.
 */

public class PractiseAfterBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * 这个是banner
         */
        private List<BannerBean> banner;
        /**
         * 这个是标题
         */
        private List<NavigationListBean> navigationList;
        /**
         * 这个是内容
         */
        private List<HomePageBean> homePage;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<NavigationListBean> getNavigationList() {
            return navigationList;
        }

        public void setNavigationList(List<NavigationListBean> navigationList) {
            this.navigationList = navigationList;
        }

        public List<HomePageBean> getHomePage() {
            return homePage;
        }

        public void setHomePage(List<HomePageBean> homePage) {
            this.homePage = homePage;
        }

        public static class BannerBean{
            private String bannerUrl;

            public String getBannerUrl() {
                return bannerUrl;
            }

            public void setBannerUrl(String bannerUrl) {
                this.bannerUrl = bannerUrl;
            }
        }

        public static class NavigationListBean{
            private String modelName;

            public String getModelName() {
                return modelName;
            }

            public void setModelName(String modelName) {
                this.modelName = modelName;
            }
        }

        public static class HomePageBean{
            private String title;
            private String content;
        }

    }

}
