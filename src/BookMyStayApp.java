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
    }
}