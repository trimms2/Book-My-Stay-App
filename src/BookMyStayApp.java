public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Create inventory
        RoomInventory inventory = new RoomInventory();

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Show available rooms
        searchService.searchAvailableRooms(
                inventory,
                singleRoom,
                doubleRoom,
                suiteRoom
        );
    }
}