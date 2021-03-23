package com.yincheng.game.model.vo;

import lombok.Data;

@Data
public class FacebookUserResp {
    //id,cover,email,gender,name,languages,timezone,third_party_id,updated_time,user_about_me,read_stream

    /** The public_profile permission allows apps to read the following fields: */
    private String id;
    private String first_name;
    private String last_name;
    private String middle_name;
    private String name;
    private String name_format;
    private String picture;
    private String short_name;

    /**The User's primary email address listed on their profile.
     * This field will not be returned if no valid email address is available. */
    private String email;

    /** male or female */
    private String gender;

    private String languages;

    private String about;

}
