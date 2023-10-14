package farmSystem.closeUp.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    public static <T> CommonResponse<T> success(T result) {
        return CommonResponse.<T>builder()
                .code(Result.OK.getStatus())
                .message(Result.OK.getMessage())
                .result(result)
                .build();
    }
    public static <T> CommonResponse<T> success() {
        return CommonResponse.<T>builder()
                .code(Result.OK.getStatus())
                .message(Result.OK.getMessage())
                .build();
    }
    public static <T> CommonResponse<T> fail(Result result) {
        return CommonResponse.<T>builder()
                .code(result.getStatus())
                .message(result.getMessage())
                .build();
    }


    @Builder
    public CommonResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}