class ChatUser extends Thread {
    private final String username;
    private final String[] messages;
    private volatile boolean paused = false;
    private volatile boolean running = true;

    public ChatUser(String name, String[] messages) {
        this.username = name;
        this.messages = messages;
    }
    public void run() {
        try {
            for (String msg : messages) {
                while (paused) {
                    Thread.sleep(100); // Wait while paused
                }

                if (!running) break; // Exit if stopped
                System.out.println(username + ": " + msg);
                Thread.sleep(500); // Simulate delay between messages
            }
        } catch (InterruptedException e) {
            System.out.println(username + " was interrupted.");
        }
    }
    public void pauseChat() {
        paused = true;
    }
    public void resumeChat() {
        paused = false;
    }
    public void stopChat() {
        running = false;
    }
}

public class MultiThreadedChat {
    public static void main(String[] args) throws InterruptedException {
        String[] aliceMsgs = {"Hi Bob!", "How are you?", "What are you working on?"};
        String[] bobMsgs = {"Hey Alice!", "I'm good.", "Working on a Java project."};
        ChatUser alice = new ChatUser("Alice", aliceMsgs);
        ChatUser bob = new ChatUser("Bob", bobMsgs);
        // Set priorities (simulate high-priority messages from Alice)
        alice.setPriority(Thread.MAX_PRIORITY);
        bob.setPriority(Thread.NORM_PRIORITY);
        System.out.println("Chat is starting...\n");
        alice.start();
        bob.start();

        // Wait for a while and then pause Bob
        Thread.sleep(1000);
        System.out.println("\n[System] Pausing Bob's chat temporarily...\n");
        bob.pauseChat();

        // Let Alice continue alone for some time
        Thread.sleep(1500);
        System.out.println("\n[System] Resuming Bob's chat...\n");
        bob.resumeChat();

        // Wait and then stop Alice early
        Thread.sleep(1000);
        System.out.println("\n[System] Stopping Alice's chat...\n");
        alice.stopChat();

        // Synchronize threads
        alice.join();
        bob.join();

        // Check status
        System.out.println("\n[System] Is Alice alive? " + alice.isAlive());
        System.out.println("[System] Is Bob alive? " + bob.isAlive());

        System.out.println("\nChat session ended.");
    }
}
