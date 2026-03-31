package br.com.pongolino.infrastructure.adapters.outbound.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.util.Currency;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CurrencyEntity {
    @Column("amount")
    private BigDecimal amount;
    @Column("type")
    private Currency type;
}
