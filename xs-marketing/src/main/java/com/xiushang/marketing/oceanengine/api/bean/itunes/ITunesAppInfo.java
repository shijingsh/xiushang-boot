package com.xiushang.marketing.oceanengine.api.bean.itunes;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class ITunesAppInfo {
    private int resultCount;
    private List<AppInfo> results;

    @Data
    public static class AppInfo {
        private List<String> screenshoturls;
        private List<String> ipadscreenshoturls;
        private List<String> appletvscreenshoturls;
        private String artworkurl60;
        private String artworkurl512;
        private String artworkurl100;
        private String artistviewurl;
        private List<String> advisories;
        private String kind;
        private List<String> features;
        private List<String> supporteddevices;
        private boolean isgamecenterenabled;
        private int averageuserratingforcurrentversion;
        private String trackcensoredname;
        private List<String> languagecodesiso2a;
        private String filesizebytes;
        private String contentadvisoryrating;
        private int userratingcountforcurrentversion;
        private String trackviewurl;
        private String trackcontentrating;
        private List<String> genreids;
        private String sellername;
        private String releasedate;
        private boolean isvppdevicebasedlicensingenabled;
        private String currentversionreleasedate;
        private String primarygenrename;
        private int primarygenreid;
        private String currency;
        private String wrappertype;
        private String version;
        private String description;
        private int artistid;
        private String artistname;
        private List<String> genres;
        private int price;
        private String bundleid;
        private String minimumosversion;
        private String releasenotes;
        private int trackid;
        private String trackname;
        private String formattedprice;
        private int averageuserrating;
        private int userratingcount;
    }

}
