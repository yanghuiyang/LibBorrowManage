package com.yang.bishe.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="T_ReaderType")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ReaderType implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ReaderTypeId")
	private int ReaderTypeId;
	
	@Column(name="ReaderTypeName")
	private String ReaderTypeName;
	@Column(name="MaxBorrowNums") //���ɽ����������
	private Integer MaxBorrowNums;
	@Column(name="MaxBorrowDays")//�����ʱ��
	private Integer MaxBorrowDays;
	@Column(name="MaxRenewNum")
	private  Integer MaxRenewNum;
	
	 @OneToMany(mappedBy="ReaderType")
	  private Set<Reader> Readers;
	 
	 @Column(name="FinePerDay")
	 private  Float FinePerDay;
	 
	 @Column(name="MaxFine")
	 private Float MaxFine;

	public Float getMaxFine() {
		return MaxFine;
	}

	public void setMaxFine(Float maxFine) {
		MaxFine = maxFine;
	}

	public Float getFinePerDay() {
		return this.FinePerDay;
	}

	public void setFinePerDay(Float finePerDay) {
		this.FinePerDay = finePerDay;
	}

	public int getReaderTypeId() {
		return ReaderTypeId;
	}

	public void setReaderTypeId(int readerTypeId) {
		ReaderTypeId = readerTypeId;
	}

	public String getReaderTypeName() {
		return ReaderTypeName;
	}

	public void setReaderTypeName(String readerTypeName) {
		ReaderTypeName = readerTypeName;
	}

	public Integer getMaxBorrowNums() {
		return MaxBorrowNums;
	}

	public void setMaxBorrowNums(Integer maxBorrowNums) {
		MaxBorrowNums = maxBorrowNums;
	}

	public Integer getMaxBorrowDays() {
		return MaxBorrowDays;
	}

	public void setMaxBorrowDays(Integer maxBorrowDays) {
		MaxBorrowDays = maxBorrowDays;
	}

	public Integer getMaxRenewNum() {
		return MaxRenewNum;
	}

	public void setMaxRenewNum(Integer maxRenewNum) {
		MaxRenewNum = maxRenewNum;
	}

	public Set<Reader> getReaders() {
		return Readers;
	}

	public void setReaders(Set<Reader> readers) {
		Readers = readers;
	}


}

