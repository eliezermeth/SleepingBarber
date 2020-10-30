/**
 * @author Eliezer Meth
 * Start Date: 2020-10-26
 *
 * Class to imitate a barber.  Extends Thread.
 */
public class Barber extends Person
{
    private boolean loop = true;

    public Barber(String name)
    {
        super(name);
    }

    public void run()
    {
        while (loop)
        {
            // run
        }
    }

    public void setLoop(boolean bool)
    {
        loop = bool;
    }

    public void cutHair(Person customer)
    {
        System.out.println(getThreadName() + " cutting " + customer.getThreadName() + "'s hair...");
        try
        {
            Thread.sleep(Barbershop.TIME_TO_CUT_HAIR);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(getThreadName() + " finished cutting " + customer.getThreadName() + "'s hair.");
    }
}
