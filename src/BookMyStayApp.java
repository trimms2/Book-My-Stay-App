public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");



        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService();

        searchService.searchAvailableRooms(
                inventory,
                singleRoom,
                doubleRoom,
                suiteRoom
        );



        System.out.println("\nBooking Request Queue:");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {

            Reservation request = bookingQueue.getNextRequest();

            System.out.println(
                    request.getGuestName() +
                            " requested " +
                            request.getRoomType() +
                            " room"
            );
        }



        System.out.println("\nRoom Allocation:");


        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        RoomAllocationService allocationService = new RoomAllocationService();

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


        System.out.println("Total cost for " + res1 + ": ₹" +
                serviceManager.calculateTotalCost(res1));

        System.out.println("Total cost for " + res2 + ": ₹" +
                serviceManager.calculateTotalCost(res2));

        /* --------------------------
   Use Case 8: Booking History & Reporting
   -------------------------- */

        System.out.println("\nBooking History & Reports:");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

// Re-add booking requests again
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {

            Reservation request = bookingQueue.getNextRequest();

            allocationService.allocateRoom(request, inventory, history);
        }

// Display history
        reportService.displayAllBookings(history);

// Generate summary
        reportService.generateSummary(history);
    }
}