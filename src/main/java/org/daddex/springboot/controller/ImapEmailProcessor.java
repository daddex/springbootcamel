package org.daddex.springboot.controller;


import org.apache.camel.*;

import org.apache.camel.component.mail.MailMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.daddex.springboot.bean.request.EmailNotification;
import org.daddex.springboot.bean.response.EmailReport;
import org.daddex.springboot.bean.response.FileMoved;
import org.springframework.beans.factory.annotation.Value;


import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ImapEmailProcessor {
    @Value("${imap.attachment.localfolder}")
    private String localfolder;


    private EmailReport executeRetrieveMail(Exchange exchange) throws Exception {
        EmailNotification emailNotification = exchange.getIn().getBody(EmailNotification.class);
        String host = emailNotification.getHost();
        String user = emailNotification.getUserMail();
        String pwd = emailNotification.getPassword();
        Integer maxMessage = emailNotification.getMaxMessage();



        // connect to my pop3 inbox
        Properties properties = System.getProperties();
        Session session = Session.getDefaultInstance(properties);
        Store store = session.getStore(emailNotification.getProtocol());
        store.connect(host, user, pwd);
        Folder inbox = store.getFolder(emailNotification.getFolder());
        EmailReport emailReport = new EmailReport();
        ArrayList<FileMoved> fileMovedList = new ArrayList<>();
        try {


            inbox.open(Folder.READ_WRITE);

            // get the list of inbox messages

            Message messages[] = inbox.search(new FlagTerm(
                    new Flags(Flags.Flag.SEEN), false));

            if (messages.length == 0) {
                emailReport.setFileMoved(0);
                emailReport.setEmailReportCode(EmailReport.EmailReportCode.NO_EMAIL);
                return emailReport;
            }else  if(messages.length > maxMessage){
                emailReport.setFileMoved(maxMessage);
            }else {
                emailReport.setFileMoved(messages.length);
            }
            for (int i = 0; i < messages.length; i++) {
                // stop after listing ten messages
                if (i >= maxMessage) {
                    //to be implemented
                   // inbox.close(true);
                    // store.close();
                    //emailReport.setFileMovedList(fileMovedList);
                    //return emailReport;
                }
                final MimeMessageParser mimeMessageParser = new MimeMessageParser((MimeMessage) messages[i]);
                mimeMessageParser.parse();
                if (mimeMessageParser.hasAttachments()) {
                    List<DataSource> attachmentList = mimeMessageParser.getAttachmentList();
                    System.out.println("Number of attachments: " + attachmentList.size());
                    for (DataSource attachment : attachmentList
                    ) {
                        System.out.println("Name: " + attachment.getName() + "  Content Type: " + attachment.getContentType());
                        if (attachment.getContentType().equalsIgnoreCase("message/rfc822")) {
                            final MimeMessage message = new MimeMessage(null, attachment.getInputStream());

                            System.out.println("Subject of the attached failure Mail:" + message.getSubject());


                            int countAtt = ((MimeMultipart) message.getContent()).getCount();
                            for (int a = 0; a < countAtt; a++) {
                                Part part = (((MimeMultipart) message.getContent()).getBodyPart(a));


                                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                    saveFile(localfolder.concat(part.getFileName()), part.getInputStream());
                                    FileMoved fileMoved = new FileMoved();
                                    fileMoved.setFileName(part.getFileName());
                                    fileMoved.setFileSize(String.valueOf(part.getSize()));
                                    fileMoved.setFileMovedCode(FileMoved.FileMovedCode.OK);
                                    fileMovedList.add(fileMoved);
                                }

                            }

                        }
                    }
                }

                System.out.println("Message " + (i + 1));
                System.out.println("From : " + messages[i].getFrom()[0]);
                System.out.println("Subject : " + messages[i].getSubject());
                System.out.println("Sent Date : " + messages[i].getSentDate());
                System.out.println();


            }
        } finally {
            inbox.close(true);
            store.close();
        }

        emailReport.setFileMovedList(fileMovedList);
        emailReport.setEmailReportCode(EmailReport.EmailReportCode.OK);
        return emailReport;
    }

    public void retrieveEmail(Exchange exchange) {
        // mail server connection parameters
        EmailReport emailReport= new EmailReport();
        try {
            emailReport = executeRetrieveMail(exchange);
            exchange.getIn().setBody(emailReport);
            return;
        } catch (Exception e) {
            emailReport.setEmailReportCode(EmailReport.EmailReportCode.KO);
            exchange.getIn().setBody(emailReport);
            return;
        }
    }

    private void saveFile(String fileName, InputStream in) throws Exception {

        File storefile = new File(fileName);


        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storefile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();

        } finally {
            try {
                bos.close();
                bis.close();
            } catch (IOException e) {
                throw new Exception(e);
            }

        }
    }

    public void processMail(Exchange exchange) {
        try {


            MailMessage mm = ((MailMessage) exchange.getIn()).copy();
            Session ss = mm.getOriginalMessage().getSession();

 /*
            Map<String, DataHandler> attMap = exchange.getIn().getAttachments();

            MimeMessage message = new MimeMessage(ss, attMap.entrySet().stream().
                    filter(x -> {
                                if (x.getKey().equals("postacert.eml")) {
                                    return true;
                                }
                                return false;
                            }
                    ).map(map -> map.getValue()).findAny().get().getInputStream());
*/
            MimeMessage message = new MimeMessage(ss, exchange.getIn().getAttachment("postacert.eml").getInputStream());

            int countAtt = ((MimeMultipart) message.getContent()).getCount();
            for (int i = 0; i < countAtt; i++) {
                Part part = (((MimeMultipart) message.getContent()).getBodyPart(i));


                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    byte[] bytesAttac = exchange.getContext().getTypeConverter()
                            .convertTo(byte[].class, part.getInputStream());

                    OutputStream
                            os
                            = new FileOutputStream(localfolder.concat(part.getFileName()));
                    os.write(bytesAttac);

                    os.close();
                }

            }


        } catch (Exception e) {
            System.out.println("Error"
                    + e.toString());
        }
    }

}
