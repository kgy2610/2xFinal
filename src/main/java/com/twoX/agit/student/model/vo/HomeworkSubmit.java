package com.twoX.agit.student.model.vo;

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
public class HomeworkSubmit {
	private String stuId;
	private int boNo;
	private String hmStuContent;
	private int score;
	private String tcComment;
	private Date createDate;
	private String status;
	
	private String originName;
	private String changeName;
	private String filePath;
	
	private String classCode;
	private String hmTitle;
	private String subject;
	private Date deadLine;
	private String hmContent;
}
