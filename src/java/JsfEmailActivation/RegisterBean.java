package JsfEmailActivation;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean
@SessionScoped
public class RegisterBean implements Serializable{
    private String nameAndSurname;
    private String email;
    private String operationResult="";
    
    private String temprorayUUID;
    
    public void registerUser(ActionEvent event)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        
        try 
        {
            temprorayUUID = UUID.randomUUID().toString();
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JsfMailActivation","root","");
            preparedStatement = connection.prepareStatement("insert into User(nameAndSurname,emailAdress,enabled,temprorayUUID) values(?,?,?,?)");
            preparedStatement.setString(1, nameAndSurname);
            preparedStatement.setString(2, email);
            preparedStatement.setBoolean(3, false);
            preparedStatement.setString(4, temprorayUUID);
            
            preparedStatement.executeUpdate();

            operationResult="Kaydınız başarılı bir şekilde yapıldı. "
                             +"Hesabınızın aktif olması için girdiğiniz e-posta adrsine gelen aktivasyon linkine tıklayınız.";
            
            sendActivationMail();
        } 
        catch (Exception e) 
        {
            System.err.println("Kayıt ekleme sırasında hata meydana geldi!\nHata:"+e);
            operationResult ="Kayıt ekleme sırasında hata meydana geldi!Bu hatayı sistem yöneticisine lüften bildirin"
                    + "\nHata:"+e;
        }
        finally
        {
            try 
            {
                if(connection!=null){
                    connection.close();
                }
                
                if (preparedStatement!= null) {
                    preparedStatement.close();
                }
            } 
            catch (Exception e) 
            {
                System.err.println("Hata meydana geldi!\nHata:"+e);
            }          
        }
    }
    
    public  void sendActivationMail()
    {
         //final String username = "oturum açmak istediğiniz e-mail adresi";
         //final String password = "oturum açmak istediğiniz e-mail adresinin şifresi";
         String username="ilkgunel93@gmail.com";
         String password="xrtyvpxejjiyalha";
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
                message.setFrom(new InternetAddress("localhost@localhost8084.com"));
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
                message.setSubject("Hesabınızı Aktifleştirin");
                message.setText("Hesabınızın aktifleştirilmesi için bu linke tıklayınız:http://www.localhost:8084/JsfEmailActivation/activation.xhtml?uuid="+temprorayUUID); 
                Transport.send(message);

         } catch (MessagingException ex) {
            throw new RuntimeException(ex);
         }
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }
}
