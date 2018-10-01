package es.source.code.util;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtil {
    private Logger log = Logger.getLogger("SendMailUtil");
    private static final String SENDER_ADDRESS = "569612172@qq.com";//发件箱地址
    private static final String SENDER_AUTH_CODE = "knojxqrshxrkbeac";//发件箱IMAP/SMTP授权码
    private static final String SENDER_HOST = "smtp.qq.com";//发件箱邮件服务器地址
    private static final String RECEIVER_ADDRESS = "brucexiajun@126.com";//收件箱地址
    private static final String SUBJECT = "邮件主题";
    private static final String CONTENT = "邮件内容";

    public void sendMail() throws Exception {
        MyAuth myAuth = new MyAuth(SENDER_ADDRESS, SENDER_AUTH_CODE);
        Properties properties = System.getProperties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", SENDER_HOST);
        properties.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(properties, myAuth);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER_ADDRESS));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(RECEIVER_ADDRESS));
        message.setSubject(SUBJECT);
        message.setContent(CONTENT, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        Transport.send(message);
    }

    class MyAuth extends javax.mail.Authenticator {
        private String userName;
        private String password;

        public MyAuth(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }


}
