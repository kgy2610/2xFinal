package com.twoX.agit.chat;


import com.twoX.agit.member.model.vo.AfterSchool;

import lombok.AllArgsConstructor;
import lombok.Data;
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
public class Chat {
	private String tcId;
	private String stuId;
	private String chDate;
	private String chTime;
	private String chContent;
	private String classCode;
	private String sendMessenger;
}
