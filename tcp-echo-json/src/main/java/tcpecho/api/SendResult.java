package tcpecho.api;

public record SendResult(Code code, String errorMessage) {
    public enum Code {
        OK,
        INVALID_MESSAGE,
        IO_ERROR
    }

    public boolean isSuccess() {
        return code == Code.OK;
    }
}