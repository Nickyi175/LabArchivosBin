import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public class si {
    private RandomAccessFile rcods, remps;

    public si() {
        try {
            // 1.Asegurar que el folder company exista
            File f = new File("company");
            f.mkdir();
            // 2.Instanciar las RAFs dentro company
            rcods = new RandomAccessFile("company/codigos.emp", "rw");
            remps = new RandomAccessFile("company/empleados.emp", "rw");
            // 3.Inicializar el archivo de código si es nuestro
            initCode();
        } catch (IOException e) {
            System.out.println("No debería pasar esto");
        }
    }

    private void initCode() throws IOException {
        if (rcods.length() == 0) {
            rcods.writeInt(1);
        }
    }

    private int getCode() throws IOException {
        rcods.seek(0);
        int code = rcods.readInt();
        rcods.seek(0);
        rcods.writeInt(code + 1);
        return code;
    }

    public void addEmployee(String name, double salary) throws IOException {
        /*
         * Formato codigo Nombre Salario Fecha Contratación Fecha Despido
         */
        remps.seek(remps.length());
        int code = getCode();
        remps.writeInt(code);
        remps.writeUTF(name);
        remps.writeDouble(salary);
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        remps.writeLong(0);
        // Asegurarnos sus archivos Individuales
        createEmployeeFolders(code);
    }

    private String employeeFolder(int code) {
        return "company/empleado" + code;
    }

    private void createEmployeeFolders(int code) throws IOException {
        File edir = new File(employeeFolder(code));
        edir.mkdirs();
        // crear los archivos de las ventas
        createYearSalesFileFor(code);
    }

    private RandomAccessFile salesFileFor(int code) throws IOException {
        String dirPadre = employeeFolder(code);
        int yearActual = Calendar.getInstance().get(Calendar.YEAR);
        String path = dirPadre + "/ventas" + yearActual + ".emp";
        return new RandomAccessFile(path, "rw");
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

    public void employeeList() throws IOException {
        /*
         * Imprime: Realizar una lista de empleados No despedidos con la siguientes
         * estructuras: Codigo=Nombre - Salario - Fecha Contratatacion
         */
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            String empleado = remps.readInt() + " - " + remps.readUTF() + " - " + remps.readDouble() + "$ - "
                    + new Date(remps.readLong());
            long fechadespido = remps.readLong();
            if (fechadespido == 0)
                System.out.println(empleado);
        }
    }

    private boolean isEmployeeActive(int code) throws IOException {
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int cod = remps.readInt();
            long pos = remps.getFilePointer();
            remps.readUTF();
            remps.skipBytes(16);
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
            JOptionPane.showMessageDialog(null, "Se ha completado la acción");

            return true;
        }
        JOptionPane.showMessageDialog(null, "Error, no se pudo completar la acción");
        return false;
    }

    public void addSaleToEmployee(int code, double sale) throws IOException {
        if (isEmployeeActive(code)) {
            int monthActual = Calendar.getInstance().get(Calendar.MONTH);
            long position = (monthActual * 9);
            RandomAccessFile salesFile = salesFileFor(code);
            salesFile.seek(position);

            double currentSales = salesFile.readDouble();
            double newSales = currentSales + sale;

            salesFile.seek(position);
            salesFile.writeDouble(newSales);

        } else {
            JOptionPane.showMessageDialog(null, "No se pudo porque el código no existe o fue inhabilitado");
        }

    }

    public boolean isEmployeePayed(int code) throws IOException {
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        RandomAccessFile ventas = salesFileFor(code);
        long posicion = mes * 9 + 8;
        ventas.seek(posicion);
        return ventas.readBoolean();

    }

    public double EmployeePayed(int code) throws IOException {
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        RandomAccessFile ventas = salesFileFor(code);
        long posicion = mes * 9 + 8;
        ventas.seek(posicion);
        double newSales = ventas.readDouble();
        ventas.seek(posicion);
        ventas.writeBoolean(true);
        return newSales;
    }

    private long searchEmployee(int code) throws IOException {
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int codigo = remps.readInt();
            long posempleado = remps.getFilePointer();
            remps.readUTF();
            remps.readDouble();
            remps.skipBytes(16);
            if (codigo == code) {
                return posempleado;
            }

        }
        return -1;

    }

    private RandomAccessFile billsFilefor(int codigo) throws IOException {
        String dirPadre = employeeFolder(codigo);
        return new RandomAccessFile(dirPadre + "/recibos.emp", "rw");
    }

    public void payEmployee(int code) throws IOException {
        if (isEmployeeActive(code)) {
            if (!isEmployeePayed(code)) {
                long date = Calendar.getInstance().getTimeInMillis();
                double sueldo = 0;

                long employeePosition = searchEmployee(code);
                remps.seek(employeePosition);

                String nombre = remps.readUTF();
                sueldo = remps.readDouble();
                double deduccion = sueldo * 0.035;

                int year = Calendar.getInstance().get(Calendar.YEAR);
                int mes = Calendar.getInstance().get(Calendar.MONTH);
                double bono = EmployeePayed(code);
                double pagoTotal = sueldo - deduccion + bono;

                System.out.println("Nombre del Empleado: " + nombre);
                System.out.println("Pago Total del Empleado: " + pagoTotal);

                RandomAccessFile bills = billsFilefor(code);
                bills.writeLong(date);
                bills.writeDouble(sueldo);
                bills.writeDouble(deduccion);
                bills.writeInt(year);
                bills.writeInt(mes);
                RandomAccessFile ventas = salesFileFor(code);
                long posicion = mes * 9 + 8;
                ventas.seek(posicion);
                ventas.writeBoolean(true);
            } else {
                JOptionPane.showMessageDialog(null, "Ya se le ha pagado al empleado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El código ingresado no existe o ha sido inhabilitado");
        }
    }
}
