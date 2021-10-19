package com.xiushang.marketing.oceanengine.support.code;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public enum ArticleCategory {
    ENTERTAINMENT("娱乐"),
    SOCIETY("社会"),
    CARS("汽车"),
    INTERNATIONAL("国际"),
    HISTORY("历史"),
    SPORTS("体育"),
    HEALTH("健康"),
    MILITARY("军事"),
    EMOTION("情感"),
    FASHION("时尚"),
    PARENTING("育儿"),
    FINANCE("财经"),
    AMUSEMENT("搞笑"),
    DIGITAL("数码"),
    EXPLORE("探索"),
    TRAVEL("旅游"),
    CONSTELLATION("星座"),
    STORIES("故事"),
    TECHNOLOGY("科技"),
    GOURMET("美食"),
    CULTURE("文化"),
    HOME("家居"),
    MOVIE("电影"),
    PETS("宠物"),
    GAMES("游戏"),
    WEIGHT_LOSING("瘦身"),
    FREAK("奇葩"),
    EDUCATION("教育"),
    ESTATE("房产"),
    SCIENCE("科学"),
    PHOTOGRAPHY("摄影"),
    REGIMEN("养生"),
    ESSAY("美文"),
    COLLECTION("收藏"),
    ANIMATION("动漫"),
    COMICS("漫画"),
    TIPS("小窍门"),
    DESIGN("设计"),
    LOCAL("本地"),
    LAWS("法制"),
    GOVERNMENT("政务"),
    BUSINESS("商业"),
    WORKPLACE("职场"),
    RUMOR_CRACKER("辟谣"),
    GRADUATES("毕业生"),
    ;
    String desc;

    ArticleCategory(String s) {
        this.desc = s;
    }
}
