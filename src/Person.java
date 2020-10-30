/**
 * @author Eliezer Meth
 * Start Date: 2020-10-26
 *
 * Class to imitate a person.  Extends Thread.  Will be extended by Barber and Customer.
 */
public class Person extends Thread
{
    public Person(String name)
    {
        super(name);
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(getThreadName()).append(" says hello...");
        return sb.toString();
    }

    public String getThreadName()
    {
        return getClass().toString().substring(6) + getName();
    }
}
