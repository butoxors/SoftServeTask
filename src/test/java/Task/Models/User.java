package Task.Models;

public class User {
    private String username;
    private String password;

    public User(String u, String p){
        this.username = u;
        this.password = p;
    }

    @Override
    public String toString() {
        return username + "/" + password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
