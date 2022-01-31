package com.daniil.courses.client.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;

@Schema(name = "Создать платеж")
@Data
@SuperBuilder
@NoArgsConstructor
public class PaymentResponce {
    @Schema(description = "Идентификатор платежа в системе клиента")
    @NotBlank
    private String externalId;
    @Schema(description = "Сумма платежа")
    @NotNull
    private Amount amount;
    @Schema(description = "Счёт клиента с которого проходит платеж")
    @NotBlank
    private String accountId;
    @Schema(description = "Назначение платежа")
    @NotBlank
    private String purpose;
    @Schema(description = "Url для web hook на стороне магазина для уведомления о совершения платежа(платеж может принят успешно или отвергнут)")
    private String acquireWebHook;
}
