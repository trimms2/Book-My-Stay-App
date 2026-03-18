import java.util.*;

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        CancellationService cancellationService = new CancellationService();

        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);

        System.out.println("\nBooking Request Queue:");

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            System.out.println(request.getGuestName() + " requested " + request.getRoomType());
        }

        System.out.println("\nRoom Allocation:");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            allocationService.allocateRoom(request, inventory);
        }

        System.out.println("\nAdd-On Services:");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        String res1 = "RES-101";
        String res2 = "RES-102";

        AddOnService wifi = new AddOnService("WiFi", 500);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService spa = new AddOnService("Spa", 1500);

        serviceManager.addService(res1, wifi);
        serviceManager.addService(res1, breakfast);
        serviceManager.addService(res2, spa);

        serviceManager.displayServices(res1);
        serviceManager.displayServices(res2);

        System.out.println("Total cost for " + res1 + ": ₹" + serviceManager.calculateTotalCost(res1));
        System.out.println("Total cost for " + res2 + ": ₹" + serviceManager.calculateTotalCost(res2));

        System.out.println("\nBooking History & Reports:");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            allocationService.allocateRoom(request, inventory, history);
        }

        reportService.displayAllBookings(history);
        reportService.generateSummary(history);

        System.out.println("\nValidation Test:");

        Reservation r4 = new Reservation("", "Single");
        Reservation r5 = new Reservation("John", "Luxury");

        bookingQueue.addRequest(r4);
        bookingQueue.addRequest(r5);

        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            allocationService.allocateRoom(request, inventory, history);
        }

        System.out.println("\nCancellation:");

        allocationService.allocateRoom(r1, inventory, history);
        cancellationService.cancelBooking(r1, inventory, history, allocationService);

        System.out.println("===== Concurrent Booking Simulation =====");

        ConcurrentBookingQueue queue = new ConcurrentBookingQueue();

        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Ram", "Single"));
        queue.addRequest(new Reservation("John", "Single"));
        queue.addRequest(new Reservation("Priya", "Single"));

        BookingProcessor t1 = new BookingProcessor("Thread-1", queue, allocationService, inventory);
        BookingProcessor t2 = new BookingProcessor("Thread-2", queue, allocationService, inventory);
        BookingProcessor t3 = new BookingProcessor("Thread-3", queue, allocationService, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("===== Data Persistence & Recovery =====");

        PersistenceService persistenceService = new PersistenceService();

        Object[] data = persistenceService.loadState();

        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (BookingHistory) data[1];
        }

        Reservation p1 = new Reservation("Abhi", "Single");
        Reservation p2 = new Reservation("Subha", "Double");

        allocationService.allocateRoom(p1, inventory, history);
        allocationService.allocateRoom(p2, inventory, history);

        System.out.println("\nCurrent Bookings:");
        for (Reservation r : history.getAllBookings()) {
            System.out.println(r.getGuestName() + " - " + r.getRoomType());
        }

        persistenceService.saveState(inventory, history);
    }
}