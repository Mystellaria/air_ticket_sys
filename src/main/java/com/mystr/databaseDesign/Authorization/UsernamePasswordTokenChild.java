package com.mystr.databaseDesign.Authorization;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class UsernamePasswordTokenChild extends UsernamePasswordToken {
    private Object msg; //这里是新加的成员变量
    // 注意构造方法是不继承的，所以需要自己写一下，用 idea 可以快速构建下面这些构造方法
    public UsernamePasswordTokenChild() {
        super();
    }

    public UsernamePasswordTokenChild(String username, char[] password) {
        super(username, password);
    }

    public UsernamePasswordTokenChild(String username, String password) {
        super(username, password);
    }

    public UsernamePasswordTokenChild(String username, char[] password, String host) {
        super(username, password, host);
    }

    public UsernamePasswordTokenChild(String username, String password, String host) {
        super(username, password, host);
    }

    public UsernamePasswordTokenChild(String username, char[] password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    public UsernamePasswordTokenChild(String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    public UsernamePasswordTokenChild(String username, char[] password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }

    public UsernamePasswordTokenChild(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }
}
