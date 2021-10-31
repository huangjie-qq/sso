package com.xqm.client2.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author：球某
 * @Date：2021/10/30/23:24
 */
@Data
public class User implements Serializable {
    private String userName;
    private String password;
    private String url;
}
