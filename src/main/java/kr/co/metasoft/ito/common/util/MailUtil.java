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
        /*
         * properties.setProperty("mail.smtp.host", "smtp.office365.com");
         * properties.setProperty("mail.smtp.port", "587");
         * properties.setProperty("mail.smtp.auth", "true");
         * properties.setProperty("mail.smtp.starttls.enable", "true");
         */

        //세션 설정
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("이메일", "비밀번호");
            }
        });

        DataHandler dataHandler = null;

        //MimeMessage 생성
        MimeMessage mimeMessage = new MimeMessage(session);
        File logoEmailFile = new ClassPathResource("static/resources/lib/hkpif-crm/0.0.1/images/logo-email.png").getFile();
        DataSource imageFile = new FileDataSource(logoEmailFile);

        //발신자 셋팅,
        mimeMessage.setFrom("order@hkpif.com");

        List<String> toList = mailDto.getToList();
        if(toList != null) {
            for(int i = 0; i < toList.size(); i++) {
                //수신자셋팅
                //.TO : 외에, .CC : 참조 , .BCC : 숨은 참조
                mimeMessage.addRecipients(RecipientType.TO, toList.get(i));
            }
        }
        List<String> ccList = mailDto.getCcList();
        if(ccList != null) {
            for(int i = 0; i < ccList.size(); i++) {
                //CC : 참조
                mimeMessage.addRecipients(RecipientType.CC, ccList.get(i));
            }
        }

        //제목 셋팅
        mimeMessage.setSubject(mailDto.getSubject());
        Multipart multipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mailDto.getText(), "text/html;charset=UTF-8");
        multipart.addBodyPart(mimeBodyPart);
        mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setDataHandler(new DataHandler(imageFile));
        mimeBodyPart.setHeader("Content-ID", "<image>");
        multipart.addBodyPart(mimeBodyPart);
        if(mailDto.getFileData() != null && mailDto.getFileName() != null) {
            dataHandler = new DataHandler(mailDto.getFileData());
            mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setDataHandler(dataHandler);
            mimeBodyPart.setDisposition(Part.ATTACHMENT);
            mimeBodyPart.setFileName(mailDto.getFileName());
            multipart.addBodyPart(mimeBodyPart);
        }
        mimeMessage.setContent(multipart);

        //보내기
        Transport.send(mimeMessage);
    }
}
