package org.daddex.springboot.bean.response;

import java.util.List;

public class EmailReport {
    private Integer fileMoved;
    public enum EmailReportCode {
        OK,
        KO,
        NO_EMAIL
    }
    private EmailReport.EmailReportCode emailReportCode;

    private List<FileMoved> fileMovedList;



    public Integer getFileMoved() {
        return fileMoved;
    }

    public void setFileMoved(Integer fileMoved) {
        this.fileMoved = fileMoved;
    }

    public List<FileMoved> getFileMovedList() {
        return fileMovedList;
    }

    public void setFileMovedList(List<FileMoved> fileMovedList) {
        this.fileMovedList = fileMovedList;
    }

    public EmailReportCode getEmailReportCode() {
        return emailReportCode;
    }

    public void setEmailReportCode(EmailReportCode emailReportCode) {
        this.emailReportCode = emailReportCode;
    }


}
