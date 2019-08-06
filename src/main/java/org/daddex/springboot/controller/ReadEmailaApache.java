package org.daddex.springboot.controller;

import org.apache.commons.mail.util.MimeMessageParser;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.*;
import java.util.List;
import java.util.Properties;

public class ReadEmailaApache {
    public static void main(String[] args) {
        try {
            readEmails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readEmails() throws Exception{
        // mail server connection parameters
        String host = "mbox.cert.legalmail.it";
        String user = "M2737432";
        String pwd = "qWkL9yFX";




        // connect to my pop3 inbox
        Properties properties = System.getProperties();
        Session session = Session.getDefaultInstance(properties);
        Store store = session.getStore("imaps");
        store.connect(host, user, pwd);
        Folder inbox = store.getFolder("INBOX");

        inbox.open(Folder.READ_WRITE);

        // get the list of inbox messages

        Message messages[] = inbox.search(new FlagTerm(
                new Flags(Flags.Flag.SEEN), false));

        if (messages.length == 0) System.out.println("No messages found.");

        for (int i = 0; i < messages.length; i++) {
            // stop after listing ten messages
            if (i > 1) {
                System.exit(0);
                inbox.close(true);
                store.close();
            }
            final MimeMessageParser mimeMessageParser = new MimeMessageParser((MimeMessage) messages[i]);
            mimeMessageParser.parse();
            if (mimeMessageParser.hasAttachments()) {
                List<DataSource> attachmentList = mimeMessageParser.getAttachmentList();
                System.out.println("Number of attachments: " +attachmentList.size());
                for (DataSource attachment:attachmentList
                ) {
                    System.out.println("Name: "+attachment.getName()+"  Content Type: "+attachment.getContentType());
                    if (attachment.getContentType().equalsIgnoreCase("message/rfc822")) {
                        final MimeMessage message = new MimeMessage(null,attachment.getInputStream());

                        System.out.println("Subject of the attached failure Mail:" + message.getSubject());


                        int countAtt = ((MimeMultipart) message.getContent()).getCount();
                        for (int a = 0; a < countAtt; a++) {
                            Part part = (((MimeMultipart) message.getContent()).getBodyPart(a));


                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                saveFile("C:\\davideStuff\\attachments\\new\\".concat(part.getFileName()),part.getInputStream());
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

        inbox.close(true);
        store.close();
    }
    private static void saveFile(String fileName, InputStream in) throws Exception {

        File storefile = new File( fileName);


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
            bos.close();
            bis.close();
        }
    }
}
