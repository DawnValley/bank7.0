package com.cx.bank.model;

import java.util.Date;

/**
 * 日志bean
 * 
 * @author RG
 *
 */
public class LogBean {
    private int logId;
    private String userName;
    private String logType; // 操作日志、安全日志、事件日志
    private String logDetail;
    private Date logTime;

    public int getLogId() {
        return logId;
    }

    public String getLogType() {
        return logType;
    }

    public String getLogDetail() {
        return logDetail;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public void setLogDetail(String logDetail) {
        this.logDetail = logDetail;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
