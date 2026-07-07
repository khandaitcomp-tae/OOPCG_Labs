import java.util.Scanner;
public class HotelBookingSystem {
    public static void main(String[] args) {
        final int FLOORS = 5;
        final int ROOMS_PER_FLOOR = 4;
        boolean[][] rooms = new boolean[FLOORS][ROOMS_PER_FLOOR]; // false = available, true = booked
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("==== HOTEL ROOM BOOKING SYSTEM ====");

        do {
            System.out.println("\n1. View Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("\nRoom Status (F = Free, B = Booked):");
                    for (int i = 0; i < FLOORS; i++) {
                        System.out.print("Floor " + (i + 1) + ": ");
                        for (int j = 0; j < ROOMS_PER_FLOOR; j++) {
                            System.out.print(rooms[i][j] ? "B " : "F ");
                        }
                        System.out.println();
                    }
                    break;

                case 2:
                    System.out.print("Enter floor number (1-" + FLOORS + "): ");
                    int floor = Integer.parseInt(scanner.nextLine()) - 1;
                    System.out.print("Enter room number (1-" + ROOMS_PER_FLOOR + "): ");
                    int room = Integer.parseInt(scanner.nextLine()) - 1;

                    if (floor < 0 || floor >= FLOORS || room < 0 || room >= ROOMS_PER_FLOOR) {
                        System.out.println("Invalid floor or room number!");
                    } else if (rooms[floor][room]) {
                        System.out.println("Room is already booked.");
                    } else {
                        rooms[floor][room] = true;
                        System.out.println("Room booked successfully!");
                    }
                    break;

                case 3:
                    System.out.println("Exiting the system. Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 3);

        scanner.close();
    }
}

