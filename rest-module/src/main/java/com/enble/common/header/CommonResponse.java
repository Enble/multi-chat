package com.enble.common.header;

import com.enble.common.filter.RequestTracingFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.MDC;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "data"})
public class CommonResponse<T> {

    private final Instant timestamp;
    private final String requestId;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    private CommonResponse(String code, String message, T data) {
        this.timestamp = Instant.now();
        this.requestId = MDC.get(RequestTracingFilter.REQUEST_ID);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResponse<T> onSuccess(T data) {
        return new CommonResponse<>(SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), data);
    }

    public static <T> CommonResponse<T> of(BaseStatus status, T data) {
        return new CommonResponse<>(status.getReason().getCode(), status.getReason().getMessage(), data);
    }

    // only use in ControllerAdvice
    public static <T> CommonResponse<T> onFailure(String code, String message, T data) {
        return new CommonResponse<>(code, message, data);
    }

}
