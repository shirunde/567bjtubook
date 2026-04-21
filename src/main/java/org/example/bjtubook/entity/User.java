package org.example.bjtubook.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String address;
    private Integer role;
    private Integer banStatus;
    private Date createdAt;
}
