import java.util.*;
/**
 * Created by Administrator on 2016/7/10.
 */
public class Employee {
    private String name;
    private double salary;
    private Date hireDay;

    static int NAME_SIZE = 40;
    static int RECORD_SIZE = 100;

    public Employee()
    {
        name = "";
        salary = 0;
        GregorianCalendar calendar = new GregorianCalendar();
        hireDay = calendar.getTime();
    }

    public Employee(String n, double s, int year ,int month ,int day)
    {
        name = n;
        salary = s;
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        hireDay = calendar.getTime();
    }

    public String getName()
    {
        return name;
    }

    public double getSalary()
    {
        return salary;
    }

    public Date getHireDay()
    {
        return hireDay;
    }

    public void raiseSalary(double byPercent)
    {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public String toString()
    {
        return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
    }
}
