package com.mllweb.network;

/**
 * Created by Android on 2016/5/27.
 */
public interface API {
    String DOMAIN = "http://192.168.1.191:8080/Thing";
//    String DOMAIN = "http://www.mllweb.com/Thing";
    String IMAGE="/IMAGE/";
    String SUCC = "SUCC";
    String FAIL = "FAIL";
    String FILE_UPLOAD = "/FileUpload";
    String LOGIN = "/Login";
    String Register = "/Register";
    String SELECT_TOPIC = "/SelectTopic";
    String SELECT_COMMENT = "/SelectComment";
    String SELECT_THING = "/SelectThing";
    String SELECT_MESSAGE = "/SelectMessage";
    String SELECT_MINE_THING = "/SelectMineThing";
    String SELECT_MY_PRAISE_THING = "/SelectMyPraiseThing";
    String SELECT_USERINFO_BY_ID = "/SelectUserInfoById";
    String INSERT_THING = "/InsertThing";
    String INSERT_TOPIC = "/InsertTopic";
    String INSERT_COMMENT = "/InsertComment";
    String INSERT_MESSAGE_LOG = "/InsertMessageLog";
    String INSERT_THING_PRAISE = "/InsertThingPraise";
    String INSERT_THING_DISLIKE = "/InsertThingDislike";
    String INSERT_THING_SHARE = "/InsertThingShare";
    String INSERT_THING_COMMENT = "/InsertThingComment";
    String UPDATE_USER_INFO = "/UpdateUserInfo";

}
