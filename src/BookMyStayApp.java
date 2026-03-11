public class BookMyStayApp {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;

        System.out.println("Single Room Details");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println("\nDouble Room Details");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);
    }
}