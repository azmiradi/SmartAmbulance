package prog.read.abanob.prog.com.smartampl.model;

public class User {
     private String accessToken;
     private String msg;
     private String message;
    private String email;
    private String name;
    private int id;
   public User(){}

    public User(String accessToken, String msg, String message, String email, String name, int id) {

        this.accessToken = accessToken;
        this.msg = msg;
        this.message = message;
        this.email = email;
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
