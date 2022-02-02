package com.daniil.courses.client.response_status;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@JsonTypeName("successes")
@Data
public class WebHookAcquireSuccessRequest extends WebHookAcquireRequest {
    private String description;
}