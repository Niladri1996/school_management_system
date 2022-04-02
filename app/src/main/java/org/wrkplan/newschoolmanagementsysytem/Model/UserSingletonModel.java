package org.wrkplan.newschoolmanagementsysytem.Model;

public class UserSingletonModel {
    private static final UserSingletonModel userSingletonModel = new UserSingletonModel();

    public static UserSingletonModel getInstance() {
        return userSingletonModel;
    }
    public String user_type,user_id,user_name,msg;

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private UserSingletonModel() {
    }

    public static UserSingletonModel getUserSingletonModel() {
        return userSingletonModel;
    }
}
