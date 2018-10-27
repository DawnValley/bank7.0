package com.cx.bank.model;

import java.sql.Timestamp;

/**
 * 日志bean
 * 
 * @author RaoGang
 * @version 7.0 2018/10/09
 */
public class LogBean {
    private int logId;// 日志id
    private String userName;// 操作者用户名
    private String logType; // 操作日志、安全日志、事件日志
    private String logDetail;// 日志详细信息
    private Timestamp logTime;// 日志时间

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogDetail() {
        return logDetail;
    }

    public void setLogDetail(String logDetail) {
        this.logDetail = logDetail;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

}
