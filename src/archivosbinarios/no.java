/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package archivosbinarios;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author pcast
 */
public class no {
      private RandomAccessFile rcods, remps;
    public no() {
        try{
            File f=new File("company");
            f.mkdir();
            rcods=new RandomAccessFile("company/codigos.emp","rw");
            remps=new RandomAccessFile("company/empleados.emp","rw");
            
        }catch(IOException e){
            System.out.println("NO DEBERIA PASAR ESTO");
        }
    }
    private void initCode() throws IOException{
        if(rcods.length()==0){
            rcods.writeInt(1);
        }
    }
    private int getCode() throws IOException{
        rcods.seek(0);
        int code=rcods.readInt();//LEE
        rcods.seek(0);//MOVER INICIO
        rcods.writeInt(code+1);
        return code;
    }
    public void addEmployes(String name, double salary) throws IOException{
       // codigo nombre  salario  fecha contratacion  sfecha despido
        remps.seek(remps.length());
        int code=getCode();//archivos para cada empleado con su folder por ventas
        remps.writeInt(code);
        remps.writeUTF(name);
        remps.writeDouble(salary);
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        remps.writeLong(0);
        //aseguramos sus archivos individuales
    } 
    private String employeeFolder(int code){//existe carpeta por cada empleado es decir raiz
        return "company/empleado"+code;
    }
    
    private void createEmployeeFolder(int code) throws IOException{
        File edir=new File(employeeFolder(code));//crear folder empleado+code
        edir.mkdir();
        //crear el archvo de las ventas
        createYearSalesFileFor(code);
    }
    private RandomAccessFile salesFileFor(int code) throws IOException{//funcion que crea los archivos de la venta que va contenr de cada mes de cada año
        String dirPadre=employeeFolder(code);
        int yearActual=Calendar.getInstance().get(Calendar.YEAR);
        String path=dirPadre+"/ventas"+yearActual+".emp";
        return new RandomAccessFile(path,"rw");//si no existe se crea, si existe usara ese
    }
    private void createYearSalesFileFor(int code) throws IOException{
        RandomAccessFile ryear= salesFileFor(code);
        if(ryear.length()==0){
            for (int m = 0; m < 12; m++) {
                ryear.writeDouble(0);
                ryear.writeBoolean(false);
            }
        }
    }
    private void listado() throws IOException{
        remps.seek(0);
        while(remps.getFilePointer()<remps.length()){
            int code=remps.readInt();
            String name=remps.readUTF();
            double sal=remps.readDouble();
            Date fc=new Date(remps.readLong());
            if(remps.readLong()==0){
                System.out.println(code+" - "+name+" - "+sal+" - "+fc);
            }
        }
    }
    private boolean isEmployeeActive(int code)throws IOException{
        remps.seek(0);
        while(remps.getFilePointer()<remps.length()){
            int cod=remps.readInt();
            long pos=remps.getFilePointer();
            remps.readUTF();
            remps.skipBytes(16);//me salta salario  y contrataccion
            if(remps.readLong()==0 && cod==code){
                remps.seek(pos);
                return true;
            }
        }
        return false;
    }
    public boolean fireEmployee(int code)throws IOException{
        if(isEmployeeActive(code)){
            String name=remps.readUTF();
            remps.skipBytes(16);
            remps.writeLong(new Date().getTime());
            System.out.println("Despidiendo a: "+name);
            return true;
        }
        return false;
    }

    public void addSaleToEmployee(int code, double sale) throws IOException{
        if(isEmployeeActive(code)){
            int mesActual=Calendar.getInstance().get(Calendar.MONTH);
            RandomAccessFile mes=salesFileFor(code);
            int bytesEstas=mesActual*12;//ubica
            mes.seek(bytesEstas);
            double salActual=remps.readDouble();
            mes.seek(bytesEstas);
            mes.writeDouble(salActual+sale);
        }
    }

}
