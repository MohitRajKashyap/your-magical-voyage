package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demonstrates multithreading and synchronization using
 * a shared booking resource. Add this to your project
 * to increase multithreading marks.
 */
public class ConcurrentBookingSimulator {

    // Shared resource
    private int availableSeats = 10;

    // Synchronized method to avoid race conditions
    public synchronized boolean bookSeat(String user) {
        if (availableSeats > 0) {
            System.out.println(user + " successfully booked a seat.");
            availableSeats--;
            return true;
        } else {
            System.out.println(user + " FAILED to book. No seats left.");
            return false;
        }
    }

    // Runner task
    class BookingTask implements Runnable {
        private final String userName;
        private final ConcurrentBookingSimulator bookingSystem;

        BookingTask(String userName, ConcurrentBookingSimulator bookingSystem) {
            this.userName = userName;
            this.bookingSystem = bookingSystem;
        }

        @Override
        public void run() {
            bookingSystem.bookSeat(userName);
        }
    }

    // Method to start simulation
    public void startSimulation() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        System.out.println("===== Starting Concurrent Booking Simulation =====");

        for (int i = 1; i <= 15; i++) {
            executor.execute(new BookingTask("User-" + i, this));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}

        System.out.println("===== Simulation Completed =====");
        System.out.println("Remaining Seats: " + availableSeats);
    }

    public static void main(String[] args) {
        ConcurrentBookingSimulator simulator = new ConcurrentBookingSimulator();
        simulator.startSimulation();
    }
}
