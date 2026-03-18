public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");

        // Rooms
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Core Services
        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        CancellationService cancellationService = new CancellationService();

        /* --------------------------
           Use Case 4: Room Search
        -------------------------- */
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);

        /* --------------------------
           Use Case 5: Booking Queue
        -------------------------- */
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

        /* --------------------------
           Use Case 6: Room Allocation
        -------------------------- */
        System.out.println("\nRoom Allocation:");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            allocationService.allocateRoom(request, inventory);
        }

        /* --------------------------
           Use Case 7: Add-On Services
        -------------------------- */
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

        /* --------------------------
           Use Case 8: Booking History
        -------------------------- */
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

        /* --------------------------
           Use Case 9: Validation
        -------------------------- */
        System.out.println("\nValidation Test:");

        Reservation r4 = new Reservation("", "Single");       // invalid
        Reservation r5 = new Reservation("John", "Luxury");   // invalid

        bookingQueue.addRequest(r4);
        bookingQueue.addRequest(r5);

        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            allocationService.allocateRoom(request, inventory, history);
        }


        System.out.println("\nCancellation:");

        allocationService.allocateRoom(r1, inventory, history);
        cancellationService.cancelBooking(r1, inventory, history, allocationService);
    }
}