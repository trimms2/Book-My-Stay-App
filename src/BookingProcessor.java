public class BookingProcessor extends Thread {

    private ConcurrentBookingQueue queue;
    private RoomAllocationService allocationService;
    private RoomInventory inventory;

    public BookingProcessor(String name,
                            ConcurrentBookingQueue queue,
                            RoomAllocationService allocationService,
                            RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.allocationService = allocationService;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation request;

            synchronized (queue) {
                if (!queue.hasRequests()) break;
                request = queue.getNextRequest();
            }

            if (request != null) {
                allocationService.allocateRoomConcurrent(request, inventory);
            }
        }
    }
}