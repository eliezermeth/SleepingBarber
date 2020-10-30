import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Eliezer Meth
 * Start Date: 2020-10-26
 *
 * Class to imitate a barbershop.
 */
public class Barbershop
{
    // Class constants
    public static int NUM_BARBERS = 2;
    public static int NUM_CUSTOMERS = 10;
    public static int NUM_WAITING_ROOM_SEATS = 4;
    public static int TIME_TO_CUT_HAIR = 1000; // 1 second

    // Class variables
    private Stack<Barber> sleepingBarber = new Stack<>();
    private Queue<Customer> customersToEnterShop = new LinkedList<>();
    private Queue<Customer> waitingRoom = new LinkedList<>(); // assume infinite as per HW specs

    public Barbershop()
    {
        initializeBarbers();
        initializeCustomers();

        while (customersToEnterShop.size() != 0 || waitingRoom.size() != 0) // while still customers needing haircut
        {
            // attempt to move customers from outside to the waiting room
            while (!customersToEnterShop.isEmpty() && waitingRoom.size() < NUM_WAITING_ROOM_SEATS)
            {
                waitingRoom.add(customersToEnterShop.poll());
            }

            // to view progress
            break;
        }

        // to view progress
        while (!waitingRoom.isEmpty())
        {
            while (!sleepingBarber.empty() && !waitingRoom.isEmpty())
            {
                Customer customer = waitingRoom.poll();

                Barber barber = sleepingBarber.pop();
                barber.cutHair(customer);
                sleepingBarber.push(barber);
            }
        }
        System.out.println("Done hair-cutting loop");
        terminateBarbers();
        System.out.println("Done");
    }

    private void initializeBarbers()
    {
        // add 2 barbers
        for (int i = 0; i < NUM_BARBERS; i++)
        {
            Barber barber = new Barber(Integer.toString(i));
            System.out.println("Adding barber: " + barber.getThreadName());
            barber.start();
            sleepingBarber.add(barber);
        }
    }

    private void initializeCustomers()
    {
        // add 10 customers
        for (int i = 0; i < NUM_CUSTOMERS; i++)
        {
            customersToEnterShop.add(new Customer(Integer.toString(i)));
        }
    }

    private void terminateBarbers()
    {
        System.out.println("Barbers: " + sleepingBarber.size());
        while (!sleepingBarber.isEmpty())
        {
            Barber barber = sleepingBarber.pop();
            System.out.println("Ending " + barber.getThreadName());
            barber.setLoop(false);
        }
    }
}
