import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Eliezer Meth
 * Start Date: 2020-10-26
 *
 * Class to imitate a barbershop.
 */
public class Barbershop extends Thread
{
    // Class constants
    public static final int NUM_BARBERS = 2;
    public static final int NUM_CUSTOMERS = 10;

    public static final long BARBER_TIME = 5000;
    private static final long CUSTOMER_TIME = 2000;
    public static final long OFFICE_CLOSE_TIME = BARBER_TIME * 2;
    public static BlockingQueue queue = new ArrayBlockingQueue(NUM_CUSTOMERS); // enough seats for all customers

    class Customer extends Thread
    {
        int id;
        BlockingQueue queue = null;

        public Customer(int i, BlockingQueue queue)
        {
            id = i;
            this.queue = queue;
        }

        public void run()
        {
            while (true) // as long as the customer is not cut he is in the queue or if not enough seats he is out
            {
                try {
                    this.queue.add(this.id);

                    this.getHaircut(); // take a seat
                } catch (IllegalStateException e) { // should be unreachable since more than enough seats
                    System.out.println("There are no free seats.  Customer " + this.id + " has left the barbershop.");
                }
                break; // customer leaves shop and thread stops running
            }
        }

        // take a seat
        public void getHaircut()
        {
            System.out.println("Customer " + this.id + " took a chair.");
        }
    }

    class Barber extends Thread
    {
        BlockingQueue queue = null;
        int id;

        public Barber(int id, BlockingQueue queue)
        {
            this.id = id;
            this.queue = queue;
        }

        public void run()
        {
            while (true)
            {
                try {
                    // attempt to pull customer from queue for certain period
                    Integer i = (Integer) this.queue.poll(OFFICE_CLOSE, TimeUnit.MILLISECONDS);

                    if (i == null) // barber slept for long time (OFFICE_CLOSE); no more clients in the queue - close office
                        break;
                    // else
                    this.cutHair(i); // cutting...
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        }

        // simulate cutting hair
        public void cutHair(Integer i)
        {
            System.out.println("Barber " + id + " is cutting hair for customer " + i);
            try {
                sleep(BARBER_TIME); // time it takes to cut hair
                System.out.println("Customer " + this.id + " is finished and has left the shop.");
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

    public static void main(String[] args)
    {
        Barbershop barbershop = new Barbershop();
        barbershop.start();
    }

    public void run()
    {
        // create barbers
        for (int i = 0; i < NUM_BARBERS; i++)
        {
            Barber barber = new Barber(i, Barbershop.queue);
            barber.start();
        }

        // create new customers
        for (int i = 0; i < NUM_CUSTOMERS; i++)
        {
            Customer customer = new Customer(i, Barbershop.queue);
            customer.start();
            try {
                sleep(CUSTOMER_TIME);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

}
