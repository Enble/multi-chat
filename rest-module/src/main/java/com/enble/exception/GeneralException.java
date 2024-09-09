package com.enble.exception;

import com.enble.common.header.BaseStatus;
import com.enble.common.header.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseStatus status;

    public ReasonDto getErrorReason() {
        return this.status.getReason();
    }

    public ReasonDto getErrorReasonHttpStatus() {
        return this.status.getReasonHttpStatus();
    }

    @Override
    public String getMessage() {
        return this.status.getReason().getMessage();
    }
}
