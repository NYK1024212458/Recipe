package com.ben.recipe.config;

/**
 * 接口配置信息
 */

public class AppConfig {
    //我的KEY值
//    public static final String KEY = "87e0dab1adbe764d849798bc94ff0437";
    public static final String KEY = "cff55a8b47fd6ba7a3df397829cc0f76";
    //  public static final String KEY = "a";
    //菜名查询接口，参数id，key
    public static final String QUERY = "http://apis.juhe.cn/cook/query";

    //菜谱分类查询接口，参数id，key
    public static final String CATEGORY = "http://apis.juhe.cn/cook/category";

    //菜标签查询接口，参数cid，key
    public static final String INDEX = "http://apis.juhe.cn/cook/index";

    //菜ID接口，参数id，key
    public static final String QUERYID = "http://apis.juhe.cn/cook/queryid";
}
