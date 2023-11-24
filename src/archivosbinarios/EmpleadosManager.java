package archivosbinarios;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public class EmpleadosManager {

    private RandomAccessFile rcods, remps;

    public EmpleadosManager() {
        try {
            File f = new File("company");
            f.mkdir();
            rcods = new RandomAccessFile("company/codigos.emp", "rw");
            remps = new RandomAccessFile("company/empleados.emp", "rw");
            initCode();
        } catch (IOException e) {
            System.out.println("NO DEBERIA PASAR ESTO");
        }
    }

    private void initCode() throws IOException {
        if (rcods.length() == 0) {
            rcods.writeInt(1);
        }
    }

    private int getCode() throws IOException {
        rcods.seek(0);
        int code = rcods.readInt();//LEE
        rcods.seek(0);//MOVER INICIO
        rcods.writeInt(code + 1);
        return code;
    }

    public void addEmployes(String name, double salary) throws IOException {
        // codigo nombre  salario  fecha contratacion  sfecha despido
        remps.seek(remps.length());
        int code = getCode();//archivos para cada empleado con su folder por ventas
        remps.writeInt(code);
        remps.writeUTF(name);
        remps.writeDouble(salary);
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        remps.writeLong(0);
        JOptionPane.showMessageDialog(null, "Se agregó el empleado " + name + " con el código " + code + "\nSu salario es de $" + salary);
        //aseguramos sus archivos individuales
        createEmployeeFolder(code);

    }

    private String employeeFolder(int code) {//existe carpeta por cada empleado es decir raiz
        return "company/empleado" + code;
    }

    private void createEmployeeFolder(int code) throws IOException {
        File edir = new File(employeeFolder(code));//crear folder empleado+code
        edir.mkdir();
        //crear el archvo de las ventas
        createYearSalesFileFor(code);
    }

    private RandomAccessFile salesFileFor(int code) throws IOException {//funcion que crea los archivos de la venta que va contenr de cada mes de cada año
        String dirPadre = employeeFolder(code);
        int yearActual = Calendar.getInstance().get(Calendar.YEAR);
        String path = dirPadre + "/ventas" + yearActual + ".emp";
        return new RandomAccessFile(path, "rw");//si no existe se crea, si existe usara ese
    }

    private void createYearSalesFileFor(int code) throws IOException {
        RandomAccessFile ryear = salesFileFor(code);
        if (ryear.length() == 0) {
            for (int m = 0; m < 12; m++) {
                ryear.writeDouble(0);
                ryear.writeBoolean(false);
            }
        }
    }

    public String listado() throws IOException {
        String todo = "";
        remps.seek(0);
        System.out.println("Code:     Name:    Salario:    Fecha C:");
        todo += "Code:     Name:    Salario:    Fecha C: \n";
        while (remps.getFilePointer() < remps.length()) {
            int code = remps.readInt();
            String name = remps.readUTF();
            double sal = remps.readDouble();
            Date fc = new Date(remps.readLong());

            if (remps.readLong() == 0) {
                System.out.println(code + " - " + name + " - " + sal + " - " + fc);
                todo += code + "\t" + name + "\t" + sal + "\t" + fc + "\n";
            }
        }
        return todo;
    }

    private boolean isEmployeeActive(int code) throws IOException {
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int cod = remps.readInt();
            long pos = remps.getFilePointer();
            remps.readUTF();
            remps.skipBytes(16);//me salta salario  y contrataccion
            if (remps.readLong() == 0 && cod == code) {
                remps.seek(pos);
                return true;
            }
        }
        return false;
    }

    public boolean fireEmployee(int code) throws IOException {
        if (isEmployeeActive(code)) {
            String name = remps.readUTF();
            remps.skipBytes(16);
            remps.writeLong(new Date().getTime());
            System.out.println("Despidiendo a: " + name);
            return true;
        }
        return false;
    }

    public void addSaleToEmployee(int code, double sale) throws IOException {
//        if(isEmployeeActive(code)){
//            int mesActual=Calendar.getInstance().get(Calendar.MONTH);
//            RandomAccessFile mes=salesFileFor(code);
//            int bytesEstas=mesActual*9;//ubica
//            mes.seek(bytesEstas);
//            double salActual=remps.readDouble();
//            boolean pagado=mes.readBoolean();
//            if(!pagado){
//            mes.seek(mes.getFilePointer()-8);
//            mes.writeDouble(salActual+sale);
//                System.out.println(salActual+sale);
//            }
//            JOptionPane.showMessageDialog(null, "Añadida");
//        }else{
//            JOptionPane.showMessageDialog(null, "No existe");
//        }
        if (isEmployeeActive(code)) {
            int mesActual = Calendar.getInstance().get(Calendar.MONTH);
            RandomAccessFile mes = salesFileFor(code);
            int bytesEstas = mesActual * 9; // Cambiado a 9 bytes por venta (8 bytes para double y 1 byte para boolean)

            // Ubica el puntero en la posición correcta
            mes.seek(bytesEstas);

            double salActual = mes.readDouble();
            boolean pagado = mes.readBoolean();

            if (!pagado) {
                // Retrocede el puntero para sobrescribir la venta anterior
                mes.seek(mes.getFilePointer() - 9); // Cambiado a 9 bytes por venta

                // Escribe la nueva venta
                mes.writeDouble(salActual + sale);
                mes.writeBoolean(true); // Marca como pagado

                System.out.println(salActual + sale);
                JOptionPane.showMessageDialog(null, "Venta añadida correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Ya se pagó la venta para este mes");
            }

            mes.close(); // Cierra el archivo después de su uso
        } else {
            JOptionPane.showMessageDialog(null, "No existe el empleado");
        }
    }

    private RandomAccessFile billsFilefor(int code) throws IOException {
        String dirPa = employeeFolder(code);
        String path = dirPa + "/recibos.emp";
        return new RandomAccessFile(path, "rw");
    }

    public boolean isEmployeePayed(int code) throws IOException {
        RandomAccessFile recibo = salesFileFor(code);
        int cM = Calendar.getInstance().get(Calendar.MONTH);
//        recibo.seek(cM * 9);
//        recibo.skipBytes(8);
        long posicion=cM*9+8;
        recibo.seek(posicion);
        return recibo.readBoolean();
    }

    public void payEmployee(int code) throws IOException {
//        if (isEmployeeActive(code)) {
//            if (isEmployeePayed(code)) {
//                System.out.println("ya se le pago");
//                JOptionPane.showMessageDialog(null, "Ya se le pago");
//            } else {
//                System.out.println("entro al else");
//                System.out.println(code);
//                RandomAccessFile raf = salesFileFor(code);
//                double sal = raf.readDouble();
//                System.out.println(sal);
//                int cM = Calendar.getInstance().get(Calendar.MONTH);
//                System.out.println(cM);
//                raf.seek(cM * 9);
//                String n = raf.readUTF();
//                System.out.println(n);
//                double ventas = raf.readDouble();
//                System.out.println(ventas);
//                double sueldo = sal + (ventas * 0.10);
//                System.out.println(sueldo);
//                double total = sueldo + (sueldo * 0.035);
//                System.out.println(total);
//                JOptionPane.showMessageDialog(null, "Se le pago a: " + n + "Lps. " + total);
//                raf.writeBoolean(true);
//                //System.out.println(ventas);
//                RandomAccessFile fact = billsFilefor(code);
//                fact.seek(fact.length());
//                fact.writeLong(Calendar.getInstance().getTimeInMillis());
//                fact.writeDouble(sal + (ventas * 0.10));
//                fact.writeDouble(sueldo * 0.035);
//                fact.writeShort(Calendar.getInstance().get(Calendar.YEAR));
//                fact.writeByte(cM);
//
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "No activo");
//        }
        if (isEmployeeActive(code)) {
            if (isEmployeePayed(code)) {
                System.out.println("Ya se le pagó");
                JOptionPane.showMessageDialog(null, "Ya se le pagó ");
            } else {
                RandomAccessFile raf = salesFileFor(code);
                
                int cM = Calendar.getInstance().get(Calendar.MONTH);
                raf.seek(cM * 9 + 8); 
                double sal = raf.readDouble();
                String n = raf.readUTF(); 
                double ventas = raf.readDouble();
                double sueldo = sal + (ventas * 0.10);
                double comision = sueldo * 0.03;
                double total = sueldo + comision;

                JOptionPane.showMessageDialog(null, "Se le pagó a: " + n + " Lps. " + total);

                // Marcar como pagado en el archivo de ventas
                raf.seek(cM * 9); 
                raf.writeBoolean(true);

                // Registrar la factura en el archivo de facturas
                RandomAccessFile fact = billsFilefor(code);
                fact.seek(fact.length());

                // Escribe la fecha actual
                fact.writeLong(Calendar.getInstance().getTimeInMillis());

                // Escribe el sueldo base sin comisión
                fact.writeDouble(sal + (ventas * 0.10));

                // Escribe la comisión
                fact.writeDouble(comision);

                // Escribe el año y el mes de la factura
                fact.writeShort(Calendar.getInstance().get(Calendar.YEAR));
                fact.writeByte(cM);

                fact.close(); // Cierra el archivo después de su uso
            }
        } else {
            JOptionPane.showMessageDialog(null, "Empleado no activo");
        }
    }
}
