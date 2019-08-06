package org.daddex.springboot.bean.request;

public class EmailNotification {

    private String host;
    private String userMail;
    private String password;
    private String protocol;
    private String folder;
    private String copyTo;
    private Integer maxMessage;
    private Boolean zipAttachments;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMaxMessage() {
        return maxMessage;
    }

    public void setMaxMessage(Integer maxMessage) {
        this.maxMessage = maxMessage;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getCopyTo() {
        return copyTo;
    }

    public void setCopyTo(String copyTo) {
        this.copyTo = copyTo;
    }
    public Boolean getZipAttachments() {
        return zipAttachments;
    }

    public void setZipAttachments(Boolean zipAttachments) {
        this.zipAttachments = zipAttachments;
    }
}
