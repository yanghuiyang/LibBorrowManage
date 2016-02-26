package com.yang.bishe.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="T_BookLocation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BookLocation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LocationId")
	private int LocationId;
	
	@Column(name="LocationName")
	private String LocationName;
	
	 @OneToMany(mappedBy="BookLocation")
	  private Set<BookSN> BookSNs;

	public int getLocationId() {
		return LocationId;
	}

	public void setLocationId(int locationId) {
		LocationId = locationId;
	}

	public String getLocationName() {
		return LocationName;
	}

	public void setLocationName(String locationName) {
		LocationName = locationName;
	}

	public Set<BookSN> getBookSNs() {
		return BookSNs;
	}

	public void setBookSNs(Set<BookSN> bookSNs) {
		BookSNs = bookSNs;
	}
}
