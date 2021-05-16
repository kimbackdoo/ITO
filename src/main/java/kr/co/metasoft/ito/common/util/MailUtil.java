package kr.co.metasoft.ito.common.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import kr.co.metasoft.ito.api.app.dto.MailDto;

@Component
public class MailUtil {

    public void sendMail(MailDto mailDto) throws MessagingException, IOException {



        Properties properties = new Properties();

        //SMTP 서버 정보 설정
          properties.setProperty("mail.smtp.host", "smtp.cafe24.com");
          properties.setProperty("mail.smtp.port", "587");
          properties.setProperty("mail.smtp.auth", "true");
          properties.setProperty("mail.smtp.timeout", "5000");
          properties.setProperty("mail.smtp.starttls.enable", "true");


        //session 생성
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("smpark@meta-soft.co.kr", "metasoft1@");
            }
        });

        DataHandler dataHandler = null;

        //MimeMessage 생성
        MimeMessage mimeMessage = new MimeMessage(session);


        //발신자 셋팅,
        mimeMessage.setFrom(new InternetAddress("smpark@meta-soft.co.kr"));

        //수신자 셋팅
        mimeMessage.addRecipients(RecipientType.TO, mailDto.getTo());



        //제목 셋팅
        mimeMessage.setSubject(mailDto.getSubject());

        Multipart multipart = new MimeMultipart();

        MimeBodyPart mimeBodyPart = new MimeBodyPart();



        //내용 셋팅
        mimeBodyPart.setContent(mailDto.getText(), "text/html;charset=UTF-8");
        multipart.addBodyPart(mimeBodyPart);


        mimeMessage.setContent(multipart);

        //보내기
        Transport.send(mimeMessage);

        System.out.println("보내기 성공");
    }
}
