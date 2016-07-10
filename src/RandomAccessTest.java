import java.io.*;
import java.util.*;
/**
 * Created by Administrator on 2016/7/10.
 */
public class RandomAccessTest {
    public static void main(String[] args) throws IOException
    {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 5000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("employee.dat"))){
            for (Employee e : staff)
                writeData(out, e);
        }

        try (RandomAccessFile in = new RandomAccessFile("employee.dat", "r")){
            int n = (int)(in.length() / Employee.RECORD_SIZE);
            Employee[] newStaff = new Employee[n];

            for (int i = n - 1; i >= 0; i--)
            {
                newStaff[i] = new Employee();
                in.seek(i * Employee.RECORD_SIZE);
                newStaff[i] = readData(in);
            }

            for (Employee e : newStaff)
                System.out.println(e);
        }
    }

    public static void writeData(DataOutput out, Employee e) throws IOException
    {
        DataIO.writeFixedString(e.getName(), Employee.NAME_SIZE, out);
        out.writeDouble(e.getSalary());

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(e.getHireDay());
        out.writeInt(calendar.get(Calendar.YEAR));
        out.writeInt(calendar.get(Calendar.MONTH) + 1);
        out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static Employee readData(DataInput in) throws IOException
    {
        String name = DataIO.readFixedString(Employee.NAME_SIZE, in);
        double salary = in.readDouble();
        int y = in.readInt();
        int m = in.readInt();
        int d = in.readInt();
        return new Employee(name, salary, y ,m - 1, d);
    }
}

class DataIO {
    public static void writeFixedString(String s, int size, DataOutput out) throws IOException
    {
        for (int i = 0; i < size; i++)
        {
            char ch = 0;
            if (i < s.length()) ch = s.charAt(i);
            out.writeChar(ch);
        }
    }

    public static String readFixedString(int size, DataInput in) throws IOException
    {
        StringBuilder b = new StringBuilder(size);
        int i = 0;
        boolean more = true;
        while (more && i < size)
        {
            char ch = in.readChar();
            i++;
            if (ch == 0) more = false;
            else b.append(ch);
        }
        in.skipBytes(2 * (size - i));
        return b.toString();
    }
}