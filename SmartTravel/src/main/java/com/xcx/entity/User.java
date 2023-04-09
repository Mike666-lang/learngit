package com.xcx.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
   @JSONField(ordinal = 1)
   private Integer id;
   @JSONField(ordinal = 2)
   private String username;
   @JSONField(ordinal = 7)
   private String password;
   @JSONField(ordinal = 3)
   private int usertype;
   @JSONField(ordinal = 4)
   private int sex;
   @JSONField(ordinal = 5)
   private String address;
   @JSONField(ordinal = 6)
   private int garage_num;
   @JSONField(ordinal = 7)
   private int etc_num;

}
