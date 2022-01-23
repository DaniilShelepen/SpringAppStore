package com.daniil.courses.bankApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebHookAcquireRequest {
    String id;
    String externalId;
    String description;
}
