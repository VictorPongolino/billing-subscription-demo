package br.com.pongolino.infrastructure.adapters.outbound.repository;

import br.com.pongolino.infrastructure.adapters.outbound.entity.SubscriptionEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface SpringDataSubscriptionRepository extends ReactiveCrudRepository<SubscriptionEntity, Long> {

    @Query("""
            SELECT id 
            FROM Subscription 
            WHERE (
                process_state = 'NONE' 
                OR ( 
                   process_state = 'PROCESSING' 
                   AND processing_started_at < NOW() - INTERVAL ? HOUR
                )
            )
            FOR UPDATE SKIP LOCKED LIMIT ?
        """)
    Flux<SubscriptionEntity> lockSubscriptions(Integer maxTimeoutHours, Integer maxElements);

    @Modifying
    @Query("""
        UPDATE Subscription 
        SET 
            process_state = 'PROCESSING', 
            processing_started_at = NOW() 
        WHERE 
            id IN (:ids)
    """)
    Mono<Integer> markAsProcessing(@Param("ids") List<Long> ids);
}


