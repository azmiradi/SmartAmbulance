package prog.read.abanob.prog.com.smartampl.model;

public class  Blood {
   private String  id;
   private  String type;
   public Blood(){}
    public Blood(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
