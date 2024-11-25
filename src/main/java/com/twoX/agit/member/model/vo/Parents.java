package com.twoX.agit.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Parents {
   private String prId;
   private String prPwd;
   private String prName;
   private String phone;
   private String nickname;
   private String prQuestion;
   private String prAnswer;
}