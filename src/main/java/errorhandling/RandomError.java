package errorhandling;

public class RandomError extends Exception {

    public RandomError(int code, String msg) {
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
