package Utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class MailSender {
  
  public void sendMail(String buyerMail, String mailMessage, String header) {
	  
      // Gönderenin e-posta adresi ve şifresi
      final String senderEmail = "ugur.selam0@gmail.com"; 
      final String senderPassword = "lmfy eipp dtwl ikil";    

      // Alıcı e-posta adresi
      final String recipientEmail = buyerMail; // Alıcı adresi

      // SMTP sunucusu ayarları
      Properties properties = new Properties();
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.port", "587");

      // Oturum oluşturma
      Session session = Session.getInstance(properties, new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(senderEmail, senderPassword);
          }
      });

      try {
          // Mesaj oluşturma
          Message message = new MimeMessage(session);
          message.setFrom(new InternetAddress(senderEmail)); // Gönderen
          message.setRecipient(Message.RecipientType.TO,// Alıcı
                  new InternetAddress(recipientEmail));
          message.setSubject(header); // Konu
          message.setContent(mailMessage, "text/html; charset=UTF-8"); // İçerik

          // Mesajı gönder
          Transport.send(message);
      } catch (MessagingException e) {
    	  MessageModals.ErrorMessage("Mail Gönderilirken Hata Oluştu!", "Hata");
          e.printStackTrace();
      }
	  
  }

}
