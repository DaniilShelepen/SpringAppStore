package com.daniil.courses.payment;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@JsonTypeName("successes")
@Data
public class WebHookAcquireSuccessRequest extends WebHookAcquireRequest {
    private String description;
}