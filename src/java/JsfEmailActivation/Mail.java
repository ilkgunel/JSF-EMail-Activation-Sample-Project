package JsfEmailActivation;

import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
@ManagedBean
@RequestScoped
public class Mail {
String gorus;
String name;
String mailAdresi;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGorus() {
        return gorus;
    }

    public void setGorus(String gorus) {
        this.gorus = gorus;
    }

    public String getMailAdresi() {
        return mailAdresi;
    }

    public void setMailAdresi(String mailAdresi) {
        this.mailAdresi = mailAdresi;
    }

    public  void mailAt(){
             final String username = "oturum açmak istediğiniz e-mail adresi";
             final String password = "oturum açmak istediğiniz e-mail adresinin şifresi";
             Properties properties = new Properties();
             properties.put("mail.smtp.auth", "true");
             properties.put("mail.smtp.starttls.enable", "true");
             properties.put("mail.smtp.host", "smtp.gmail.com");
             properties.put("mail.smtp.port", "587");

             Session session = Session.getInstance(properties,
               new javax.mail.Authenticator() 
               {
                   @Override
                   protected PasswordAuthentication getPasswordAuthentication() 
                   {
                        return new PasswordAuthentication(username, password);
                    }
                }); 
             try {

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("biseyler@bisey.com"));
                    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("mailin gönderilmesini istediğiniz hesap"));
                    message.setSubject("KOD5 Hakkındaki Görüşler");
                    message.setText(mailAdresi+"'den gelen mail şu şekilde:n"+name+" "+gorus); 
                    Transport.send(message);
 
             } catch (MessagingException ex) {
                    throw new RuntimeException(ex);
             }
       }
}