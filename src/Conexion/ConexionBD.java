/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    private String driver="com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/gasolineria";
    private String usuario="root";
    private String contraseña="";
    public Connection conexion;

    public ConexionBD() {
        
        try{
             Class.forName(driver);
        
        conexion=DriverManager.getConnection(url, usuario, contraseña);
        System.out.println("la base de datos fue conectada");
        }catch(Exception e){
            System.out.println("Erorr al conectar la base de datos");
        }
       
    }

    public Connection getConexion() {
        return conexion;
    }

    
    
    
}
