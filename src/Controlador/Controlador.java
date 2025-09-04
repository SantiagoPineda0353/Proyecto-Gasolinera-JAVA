/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.ConexionBD;
import Modelo.Gasolina;
import Modelo.Venta;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simclub01
 */
public class Controlador {
    ConexionBD conexion=new ConexionBD();
     Gasolina gasolina1=new Gasolina("Diesel");
    Gasolina gasolina2=new Gasolina("Corriente");
    Connection cn=conexion.getConexion();
    ResultSet rs=null;
    String archivo= "Reporte.csv";
    
    public  int ValidarVentaGalones(float galones,String tipoGasolina){
        if(galones<=0){
            return 1; //valor de galones invalido
        }else if(tipoGasolina=="Diesel"){
            if(galones>gasolina1.getCantidadTotalGasolina()){
                return 2;//no hay suficiente cantidad de gasolina
            }else{
                return 3;
            }
        }else if(tipoGasolina=="Corriente"){
            if(galones>gasolina2.getCantidadTotalGasolina()){
                return 2;//no hay suficiente cantidad de gasolina
            }else{
                return 3;
            }
        }
        return 3;
    }
    
    public int ValidarVentaDinero(float dinero,String tipoGasolina){
        if(dinero<gasolina1.getPrecioGalon()){
            return 1; //valor de galones invalido
        }else if(tipoGasolina=="Diesel"){
            if((gasolina1.getCantidadTotalGasolina()*gasolina1.getPrecioGalon())<dinero){
                return 2;//no hay suficiente cantidad de gasolina
            }else{
                return 3;
            }
        }else if(tipoGasolina=="Corriente"){
            if((gasolina2.getCantidadTotalGasolina()*gasolina2.getPrecioGalon())<dinero){
                return 2;//no hay suficiente cantidad de gasolina
            }else{
                return 3;
            }
        }
        return 3;
    }
    public void GenerarVentaDinero(int dinero,String tipoGasolina){
        Venta venta;
    float galones;
    if(tipoGasolina=="Diesel"){
        galones=Float.valueOf(dinero/(gasolina1.getPrecioGalon()));
        System.out.println(dinero);
        System.out.println(gasolina1.getPrecioGalon());
        System.out.println(galones);
        venta=new Venta(galones,dinero,gasolina1);
    }else{
        galones=(dinero/(gasolina2.getPrecioGalon()));
        System.out.println(galones);
        venta=new Venta(galones,dinero,gasolina2);
    }
    GenerarPDF(venta);
    IngresarVentaBD(venta);
        
    }
     public void GenerarVentaGalon(float galones,String tipoGasolina){
        Venta venta;
    int dinero;
    if(tipoGasolina=="Diesel"){
        dinero=(int) (galones*(gasolina1.getPrecioGalon()));
        System.out.println(dinero);
        venta=new Venta(galones,dinero,gasolina1);
    }else{
        dinero=(int) (galones*(gasolina1.getPrecioGalon()));
        venta=new Venta(galones,dinero,gasolina2);
    }
    GenerarPDF(venta);
    IngresarVentaBD(venta);
        
    }
     public void IngresarVentaBD(Venta venta){
         String sql=" insert into venta (Galones,Fecha,Hora,PrecioTotal,IdGasolina)"
    + " values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement insert=cn.prepareStatement(sql);
        insert.setFloat(1,venta.getGalones());
            insert.setString(2, String.valueOf(LocalDate.now()));
            insert.setString(3, String.valueOf(LocalTime.now()));
           insert.setInt(4,venta.getPrecioTotal());
           insert.setInt(5,venta.getGasolina().getIdGasolina());
           insert.execute();
            System.out.println("Se ingreso el dato a la bd");
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void TraerTablaBD(String fecha){
         String sql="SELECT * FROM venta WHERE Fecha = ?";
         String nombreArchivo;
         Date currentDate = new Date();
        SimpleDateFormat datee = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String hora_fecha = datee.format(currentDate);

      
         nombreArchivo = "Reporte_" + hora_fecha + ".csv";
        try {
            PreparedStatement insert=cn.prepareStatement(sql);
            insert.setString(1, fecha);
            rs=insert.executeQuery();
            while(rs.next()){
                String tipoGasolina;
                
                int idVenta=rs.getInt("Id");
                float galones=rs.getFloat("Galones");
                String fecha2=rs.getString("Fecha");
                String hora=rs.getString("Hora");
                int precioTotal=rs.getInt("PrecioTotal");
                int idGasolina=rs.getInt("IdGasolina");
             if(idGasolina==1){
                 tipoGasolina="Corriente";
             }else{
                 tipoGasolina="Diesel";
             }
             GenerarCSV(idVenta,fecha2,hora,tipoGasolina,galones,precioTotal,nombreArchivo);
            }
            System.out.println("Se leyo de la bd");
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
    public void GenerarCSV(int id,String fecha,String hora,String tipoGasolina,float galones,int precioTotal,String nombreArchivo){
        String[] datos = {String.valueOf(id),fecha,hora,tipoGasolina, String.valueOf(galones),String.valueOf(precioTotal)};
          Path path = Paths.get(nombreArchivo);
 
        boolean archivoExiste = Files.exists(path);
        
        try {
            
            FileWriter fileWriter = new FileWriter(nombreArchivo, true);
            if (!archivoExiste) {
                // Escribir el encabezado en el archivo si se está creando uno nuevo
                //fileWriter.append("Id,Nombre Mascota ,Especie,Nombre Dueño,Descripción,Diagnostico\n");
            }
           
            fileWriter.append(String.join(",", datos));
            fileWriter.append("\n");
            fileWriter.flush();
            fileWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
     
        
    }
     public void GenerarPDF(Venta venta){
          Document document = new Document();

        try {
            // Crear el objeto PdfWriter
            PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));

            // Abrir el documento
            document.open();

            // Cargar la imagen desde un archivo
            Image imagen = Image.getInstance("LOGO GASOLINERA.png");
            imagen.scaleToFit(100, 100); // Ajustar el tamaño de la imagen si es necesario
            imagen.setAbsolutePosition(50, PageSize.A4.getHeight() - 100); // Posición de la imagen en la parte superior izquierda
            document.add(imagen);

            // Crear el título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("THE FUEL SPOT", fontTitulo);

            // Ajustar la posición del título
            titulo.setAlignment(Element.ALIGN_RIGHT);
            titulo.setIndentationRight(30);

            // Agregar el título al documento
            document.add(titulo);

            // Agregar los párrafos
            Font fontParrafo = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
          
                Paragraph parrafo1 = new Paragraph("         " , fontParrafo);
                Paragraph parrafo2 = new Paragraph("         " , fontParrafo);
               Paragraph fecha = new Paragraph("Fecha:           "+String.valueOf(LocalDate.now()),fontParrafo);
               Paragraph hora = new Paragraph("Hora:           "+String.valueOf(LocalTime.now()),fontParrafo);
                Paragraph tipoGasolina = new Paragraph("Tipo de Gasolina:           "+venta.getGasolina().getTipoGasolina(),fontParrafo);
                Paragraph precioGalon = new Paragraph("Precio por galon:           "+venta.getGasolina().getPrecioGalon(),fontParrafo);
                Paragraph galones = new Paragraph("Galones:           "+venta.getGalones(),fontParrafo);
                Paragraph precioTotal = new Paragraph("Precio Total:           "+venta.getPrecioTotal(),fontParrafo);
                   
                
                   /* Paragraph parrafo1 = new Paragraph("" , fontParrafo);
                    parrafo1.setAlignment(Element.ALIGN_CENTER);
                    Paragraph parrafo2 = new Paragraph("" , fontParrafo);
                    parrafo2.setAlignment(Element.ALIGN_CENTER);
              */
                 parrafo1.setAlignment(Element.ALIGN_CENTER);
                 parrafo2.setAlignment(Element.ALIGN_CENTER);
                fecha.setAlignment(Element.ALIGN_CENTER);
                hora.setAlignment(Element.ALIGN_CENTER);
                 tipoGasolina.setAlignment(Element.ALIGN_CENTER);
                  precioGalon.setAlignment(Element.ALIGN_CENTER);
                   galones.setAlignment(Element.ALIGN_CENTER);
                    precioTotal.setAlignment(Element.ALIGN_CENTER);
                 
                document.add(parrafo1);
                document.add(parrafo2);
                document.add(fecha);
                document.add(hora);
                document.add(tipoGasolina);
                document.add(precioGalon);
                document.add(galones);
                document.add(precioTotal);
            

            // Cerrar el documento
            document.close();

            System.out.println("El archivo PDF con la estructura deseada se ha generado correctamente.");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
}
