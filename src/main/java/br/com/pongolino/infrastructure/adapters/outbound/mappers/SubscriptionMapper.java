package br.com.pongolino.infrastructure.adapters.outbound.mappers;

import br.com.pongolino.domain.*;
import br.com.pongolino.domain.Subscription.ProcessState;
import br.com.pongolino.infrastructure.adapters.outbound.entity.CurrencyEntity;
import br.com.pongolino.infrastructure.adapters.outbound.entity.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    @Mappings({
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "subscriberId", target = "subscriberId"),
            @Mapping(source = "processState", target = "processState"),
            @Mapping(source = "billingPeriod", target = "billingPeriod"),
            @Mapping(source = "currency", target = "currency"),
    })
    Subscription toDomain(SubscriptionEntity entity);
    SubscriptionEntity toEntity(Subscription subscription);

    default SubscriptionType map(String type) {
        return type == null ? null : SubscriptionType.of(type);
    }

    default String map(SubscriptionType type) {
        return type == null ? null : type.getType();
    }

    default SubscriberId map(Long subscriberId) {
        return  subscriberId == null ? null : SubscriberId.of(subscriberId);
    }

    default Long map(SubscriberId subscriberId) {
        return subscriberId == null ? null : subscriberId.getValue();
    }

    default ProcessState mapProcessState(String state) {
        return state == null ? null : ProcessState.valueOf(state);
    }

    default String mapProcessState(ProcessState processState) {
        return processState == null ? null : processState.toString();
    }

    default BillingPeriod mapBillingPeriod(String period) {
        return period == null ? null : BillingPeriod.of(period);
    }

    default String mapBillingPeriod(BillingPeriod billingPeriod) {
        return billingPeriod == null ? null : billingPeriod.getPeriodUnit().name();
    }

    default Money mapCurrency(CurrencyEntity currencyEntity) {
        return currencyEntity == null ? null : Money.of(currencyEntity.getAmount(), currencyEntity.getType());
    }
    default CurrencyEntity mapCurrencyEntity(Money money) {
        return money == null ? null : new CurrencyEntity(money.getAmount(), money.getCurrency());
    }
}