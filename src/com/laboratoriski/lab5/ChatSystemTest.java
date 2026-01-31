package laboratoriski.lab5;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.*;
import java.util.Set;
import java.util.TreeSet;

class ChatRoom {
    private String name;
    private Set<String> users;

    public ChatRoom(String name) {
        this.name = name;
        this.users = new TreeSet<>();
    }

    public void addUser(String username) {
        users.add(username);
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public boolean hasUser(String username) {
        return users.contains(username);
    }

    public int numUsers() {
        return users.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");

        if (users.isEmpty()) {
            sb.append("EMPTY");
        } else {
            for (String u : users) {
                sb.append(u).append("\n");
            }
            // remove last newline (optional)
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public Set<String> getUsers() {
        return users;
    }
}

class NoSuchRoomException extends Exception {
    public NoSuchRoomException(String roomName) {
        super("No such room: " + roomName);
    }
}
class NoSuchUserException extends Exception {
    public NoSuchUserException(String username) {
        super("No such user: " + username);
    }
}



class ChatSystem {

    private TreeMap<String, ChatRoom> rooms;
    private Set<String> registeredUsers;

    public ChatSystem() {
        rooms = new TreeMap<>();
        registeredUsers = new TreeSet<>();
    }


    public void addRoom(String roomName) {
        rooms.put(roomName, new ChatRoom(roomName));
    }

    public void removeRoom(String roomName) {
        rooms.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        ChatRoom r = rooms.get(roomName);
        if (r == null) throw new NoSuchRoomException(roomName);
        return r;
    }

    public void register(String userName) {
        registeredUsers.add(userName);

        // If no rooms, do nothing
        if (rooms.isEmpty()) return;

        // Find room with fewest users
        ChatRoom best = null;
        for (ChatRoom r : rooms.values()) {
            if (best == null || r.numUsers() < best.numUsers()) {
                best = r;
            }
        }

        best.addUser(userName);
    }

    public void registerAndJoin(String userName, String roomName) throws NoSuchRoomException {
        registeredUsers.add(userName);
        ChatRoom r = getRoom(roomName);
        r.addUser(userName);
    }

    public void joinRoom(String userName, String roomName)
            throws NoSuchRoomException, NoSuchUserException {

        if (!registeredUsers.contains(userName))
            throw new NoSuchUserException(userName);

        ChatRoom r = getRoom(roomName);
        r.addUser(userName);
    }

    public void leaveRoom(String userName, String roomName)
            throws NoSuchRoomException, NoSuchUserException {

        if (!registeredUsers.contains(userName))
            throw new NoSuchUserException(userName);

        ChatRoom r = getRoom(roomName);
        r.removeUser(userName);
    }

    public void followFriend(String username, String friendUsername)
            throws NoSuchUserException {

        if (!registeredUsers.contains(username))
            throw new NoSuchUserException(username);

        if (!registeredUsers.contains(friendUsername))
            throw new NoSuchUserException(friendUsername);

        for (ChatRoom r : rooms.values()) {
            if (r.hasUser(friendUsername)) {
                r.addUser(username);
            }
        }
    }
}


public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        m.invoke(cs,params);
                    }
                }
            }
        }
    }

}
