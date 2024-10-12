package com.microservices.otmp.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  授权异常
 * @Author lovefamily
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "unauthorized")
public class UnauthorizedException extends RuntimeException
{
    private static final String DEFAULT_MSG      = "unauthorized";

    private static final long   serialVersionUID = 3885400551304383736L;

    public UnauthorizedException(String msg)
    {
        super(msg);
    }

    public UnauthorizedException()
    {
        super(DEFAULT_MSG);
    }
}