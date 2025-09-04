
import Controlador.Controlador;
import Vista.PantallaPrincipal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.LocalTime;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author simclub01
 */
public class Main {
  
    public static void main(String [] args){  
      
       Controlador controlador= new Controlador();
       
        System.out.println(String.valueOf(LocalDate.now()));
        System.out.println(String.valueOf(LocalTime.now()));
          
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run(){
               new PantallaPrincipal(controlador).setVisible(true);
            }
        });
        //}
       

    }
      
}