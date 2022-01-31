package com.daniil.courses.payment;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@JsonTypeName("error")
@Data
public class WebHookAcquireErrorRequest extends WebHookAcquireRequest {
    private String cause;
}