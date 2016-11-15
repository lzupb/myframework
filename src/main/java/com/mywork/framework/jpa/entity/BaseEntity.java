package com.mywork.framework.jpa.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * <p>
 * 功能描述：entity基类
 * </p>
 * 
 * @version V1.0
 */
@MappedSuperclass
public class BaseEntity extends IdTimeEntity implements Serializable {

	/**
	 * 	
	 */
	private static final long serialVersionUID = -5964221469131137019L;

	@Column(name = "CREATE_BY")
	private Long createBy;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "UPDATE_BY")
	private Long updateBy;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	@Version
	@Column(name = "VERSION")
	private Integer version;

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	protected Integer getVersion() {
		return version;
	}

	protected void setVersion(Integer version) {
		this.version = version;
	}

}
