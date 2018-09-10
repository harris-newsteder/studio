package studio.util;

public class UID {
    private static int nextId = 0;

    public static int generate() {
        return nextId++;
    }
}
