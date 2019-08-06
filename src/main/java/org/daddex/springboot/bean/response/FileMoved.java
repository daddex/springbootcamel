package org.daddex.springboot.bean.response;

public class FileMoved {
    public enum FileMovedCode {
        OK,
        KO
    }
    private FileMovedCode fileMovedCode;
    private String fileName;
    private String fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public FileMovedCode getFileMovedCode() {
        return fileMovedCode;
    }

    public void setFileMovedCode(FileMovedCode fileMovedCode) {
        this.fileMovedCode = fileMovedCode;
    }
}
