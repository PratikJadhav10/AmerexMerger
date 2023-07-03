package com.br.amerex.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "bridge_request")
public class RetrieveDataPage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bridge_request_id")
	private Long bridgeRequestId;

	@Column(name = "record_version", nullable = false)
	private int recordVersion;

	/*
	 * @Temporal(TemporalType.TIMESTAMP)
	 * 
	 * @Column(name = "date", nullable = false) private Date trade_date;
	 */

	private LocalDateTime date;

	@Column(name = "trade_ref", nullable = false)
	private String tradeRef;

	@Column(name = "source_system", nullable = false, length = 10)
	private String sourceSystem;

	@Column(name = "message_load", nullable = false, columnDefinition = "TEXT")
	private String messageLoad;

	@Column(name = "message_status", nullable = false, length = 4)
	private String messageStatus;

	@Column(name = "req_file_name", nullable = false, length = 255)
	private String reqFileName;

	@Column(name = "record_count", nullable = false)
	private String recordCount;

	public RetrieveDataPage() {

	}

	public RetrieveDataPage(Long bridgeRequestId, int recordVersion, LocalDateTime date, String tradeRef,
			String sourceSystem, String messageLoad, String messageStatus, String reqFileName, String recordCount) {
		super();
		this.bridgeRequestId = bridgeRequestId;
		this.recordVersion = recordVersion;
		this.date = date;
		this.tradeRef = tradeRef;
		this.sourceSystem = sourceSystem;
		this.messageLoad = messageLoad;
		this.messageStatus = messageStatus;
		this.reqFileName = reqFileName;
		this.recordCount = recordCount;
	}

	public Long getBridgeRequestId() {
		return bridgeRequestId;
	}

	public void setBridgeRequestId(Long bridgeRequestId) {
		this.bridgeRequestId = bridgeRequestId;
	}

	public int getRecordVersion() {
		return recordVersion;
	}

	public void setRecordVersion(int recordVersion) {
		this.recordVersion = recordVersion;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getMessageLoad() {
		return messageLoad;
	}

	public void setMessageLoad(String messageLoad) {
		this.messageLoad = messageLoad;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getReqFileName() {
		return reqFileName;
	}

	public void setReqFileName(String reqFileName) {
		this.reqFileName = reqFileName;
	}

	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	public String getTradeRef() {
		return tradeRef;
	}

	public void setTradeRef(String tradeRef) {
		this.tradeRef = tradeRef;
	}

	@Override
	public String toString() {
		return "RetrieveDataPage [bridgeRequestId=" + bridgeRequestId + ", recordVersion=" + recordVersion + ", date="
				+ date + ", tradeRef=" + tradeRef + ", sourceSystem=" + sourceSystem + ", messageLoad=" + messageLoad
				+ ", messageStatus=" + messageStatus + ", reqFileName=" + reqFileName + ", recordCount=" + recordCount
				+ "]";
	}

	// Constructors, getters, and setters
	// ...
}
