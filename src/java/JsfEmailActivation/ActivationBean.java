package JsfEmailActivation;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
 
@ManagedBean
@RequestScoped
public class ActivationBean implements Serializable{ 
  
    @ManagedProperty(value="#{param.uuid}")
    private String uuid;
    
    private boolean valid;
 
    @PostConstruct
    public void init() {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try 
        {       
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JsfMailActivation","root","");
            preparedStatement = connection.prepareStatement("update User set enabled=1 where temprorayUUID=?");
            preparedStatement.setString(1, uuid);
            
            preparedStatement.executeUpdate();
            
            preparedStatement =connection.prepareStatement("select enabled from User where temprorayUUID=?");
            preparedStatement.setString(1, uuid);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                valid=resultSet.getBoolean("enabled");
            }
        } 
        catch (Exception e) 
        {
            System.err.println("Hata meydana geldi!\nHata:"+e);
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
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    public boolean isValid() {
        return valid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    
}