package email.emaildemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private JavaMailSender mailSender;
    public void sendSimpleMail(String sendTo, String title, String content) {
        //简单邮件发送
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailConfig.getEmailForm());
        message.setTo(sendTo);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendAttachmentMail(String sendTo, String title, String content, File file) {
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper =new MimeMessageHelper(msg,true);
            helper.setFrom(emailConfig.getEmailForm());
            helper.setTo(sendTo);
            helper.setSubject(title);
            helper.setText(content);

            FileSystemResource r =new FileSystemResource(file);
            helper.addAttachment("附件",r);


        }catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(msg);
    }
}
