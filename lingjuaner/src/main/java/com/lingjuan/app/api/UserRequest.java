package com.lingjuan.app.api;

public class UserRequest {
    private String nick;

    private String mobile;

    private Long referrer;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getReferrer() {
        return referrer;
    }

    public void setReferrer(Long referrer) {
        this.referrer = referrer;
    }

    public UserRequest(String nick, String mobile, Long referrer) {
        this.nick = nick;
        this.mobile = mobile;
        this.referrer = referrer;
    }
}
