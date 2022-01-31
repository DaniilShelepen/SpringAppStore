package com.daniil.courses.client.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(name = "Создать платеж")
@Data
@SuperBuilder
@NoArgsConstructor
@Component
public class PaymentResponse {
    @Schema(description = "Идентификатор платежа в системе клиента")
    @NotBlank
    private String externalId;
    @Schema(description = "Сумма платежа")
    @NotNull
    private Amount amount;
    @Schema(description = "Счёт клиента с которого проходит платеж")
    @NotBlank
    private String accountId;
    @NotBlank
    private String paymentConfirmationRedirectUrl;
}
