package com.Damera.entities;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "User_Dtls")
@Data
public class UserDtlsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String name;
	private String email;
	private Long phno;
	private String password;
	private String accountStatus;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user",fetch = FetchType.EAGER)
	private List<StudentEnqEntity> lstOfEnquiries;

}
