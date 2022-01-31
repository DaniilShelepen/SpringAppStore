package com.daniil.courses.payment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "result")
@JsonSubTypes({
        @JsonSubTypes.Type(value = WebHookAcquireErrorRequest.class, name = "error"),
        @JsonSubTypes.Type(value = WebHookAcquireSuccessRequest.class, name = "successes")
})
public abstract class WebHookAcquireRequest {
    String id;
    String externalId;
}
