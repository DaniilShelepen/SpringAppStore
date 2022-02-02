package com.daniil.courses.client.response_status;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@JsonTypeName("error")
@Data
public class WebHookAcquireErrorRequest extends WebHookAcquireRequest {
    private String cause;
}