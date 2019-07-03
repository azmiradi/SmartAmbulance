package prog.read.abanob.prog.com.smartampl.model;

public class Hospital {
    private String  id;
    private String  name;
    private String lat;
    private String lng;
    private String distance;
    private String code;
    public Hospital(){}
    public Hospital(String id, String name, String lat, String lng, String distance, String code) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
