
package com.twoX.agit.member.model.vo;

import java.sql.Date;

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
public class Homework {
	private int boNo;
	private String tcId;
	private String classCode;
	private String hmTitle;
	private Date deadLine;
	private String hmContent;
	private String hmStuContent;
	private Date createDate;
	private String subject;
	private String status;
	private int score;
	
	private String originName;
	private String changeName;
}
