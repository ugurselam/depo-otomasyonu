package Utils;

import java.util.Random;

public class OtherMetot {
	
	// Şifre veya kullanıcı adındaki özel sembolleri filtreleme metotu
	
	public boolean validation(String textField) {
		
		String[] badWords = {"\"", "!", "'", "£", "#", "^", "+", "$", "½", "%", "&", "{", "/", "(", ")", "[", "]", "}", "=", "?", "*", "-", "_", "\\", "<", ">", ";", ",", "`", ":", ".", "|"};
		
		if(textField.length() != 0) {
			for (String badWord : badWords) {
				if(textField.contains(badWord)) {
				    return true;
				}
			}		
		} else {
			return true;
		}
		
		return false;
	}
	
	// Mail adresi doğrulama metotu
	
	public boolean mailValidation(String textField) {
		
		if(textField.length() < 3) {
            MessageModals.ErrorMessage("Mail adresinizin uzunluğu 3 karakterden fazla olmalı!", "Mail Hatası");
            return true;
		}
		
		if(!textField.contains("@")) {
            MessageModals.ErrorMessage("Mail adresiniz @ karakterini içermeli!", "Mail Hatası");
            return true;
		}
		
		int charIndex = textField.indexOf("@");
		int textLength = textField.length();
		
		if(charIndex == 0 && charIndex == textLength) {
            MessageModals.ErrorMessage("Mail adresinizde yazım hatası bulunmaktadır lütfen kontrol ediniz!", "Mail Hatası");
            return true;
		}
		
		return false;
	}
	
	// Mail'e doğrulama mesajı gönderme metodu
	
	public String sendCode(String email) {
		Random randomCode = new Random();
		
		String code = "";
		
		for(int a = 0; a < 5; a++) {
			int character = randomCode.nextInt(1, 10);
			code += character;
		}
		
	    String content = """
          <html>
             <body style="margin: 0; padding: 0; background-color: #ff0000; font-family: Arial, sans-serif; text-align: center;">
               <table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-color: #ff0000;">
                   <tr>
                     <td align="center" style="padding: 20px;">
                       <table border="0" cellspacing="0" cellpadding="0" style="background-color: #ffffff; border-radius: 10px; padding: 20px; text-align: center;">
                          <tr>
                             <td style="font-size: 32px; color: #000000; font-weight: bold; padding: 10px;">
                                """+ code +"""
                              </td>
                          </tr>
                        </table>
                       <p style="font-size: 16px; color: #ffffff; padding-top: 10px; font-weight: bold;">
                          Hesabınızı oluşturmanız için buradaki doğrulama kodunu uygulama üzerinde doğrulayın.
                       </p>
                     </td>
                   </tr>
                 </table>
               </body>
            </html>
         """;
		
	    MailSender codeMail = new MailSender();
	    codeMail.sendMail(email, content, "Doğrulama Kodu");
	    
	    if(code.length() != 0) {
	    	return code;
	    } else {
			MessageModals.ErrorMessage("Kod oluşturulamadı, sistem hatası oluştu!", "Sistem Hatası");
	    	return null;
	    }
	    
	}

}
