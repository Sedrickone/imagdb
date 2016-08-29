package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.*;

public class Controller {

    @FXML
    Button btn;
    @FXML
    ImageView img;
    @FXML
    TextField name;
    @FXML
    Label lblName;

    public void OnClick(ActionEvent actionEvent) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String url = "jdbc:sqlite:db\\image.db";

        Class.forName("org.sqlite.JDBC").newInstance();
            try(Connection con=DriverManager.getConnection(url);
                Statement st = con.createStatement() ) {
                ResultSet rs = st.executeQuery("SELECT pr.images FROM person pr WHERE id=4");
                Blob blob=null;
                rs.next();
                blob=rs.getBlob(9);
                BufferedImage image= ImageIO.read(blob.getBinaryStream());
                rs.close();
                st.close();
                img.setImage(Image.impl_fromPlatformImage(image));


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
            }

    }


    public void clickadd(ActionEvent actionEvent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String url = "jdbc:sqlite:db\\image.db";

            Class.forName("org.sqlite.JDBC").newInstance();




        try(Connection con=DriverManager.getConnection(url);PreparedStatement ps=con.prepareStatement("insert into person(images,names) values (?,?)",Statement.RETURN_GENERATED_KEYS)) {

            String date="A:\\Java\\JavaSe\\ImagDb\\src\\sample\\bmw.jpg";
            File file= new File(date);
            BufferedInputStream inputStream=new BufferedInputStream(new FileInputStream(date));
            ps.setBinaryStream(1,inputStream,(int) file.length());
            ps.setString(2,name.getText());
            ps.executeUpdate();
                    }
                    catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
