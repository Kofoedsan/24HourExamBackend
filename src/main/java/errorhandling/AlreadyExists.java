package errorhandling;

public class AlreadyExists extends Exception {

    public AlreadyExists(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private int code;
    private String msg;


    public int getCode() {
        return code;
    }

    public String getmsg() {
        return msg;
    }
}
