package com.Damera.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "student_enquiries")
@Data
public class StudentEnqEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eId;
	private String studentName;
	private Long phno;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	@CreationTimestamp
	private LocalDate enquiryDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private UserDtlsEntity user;

}
