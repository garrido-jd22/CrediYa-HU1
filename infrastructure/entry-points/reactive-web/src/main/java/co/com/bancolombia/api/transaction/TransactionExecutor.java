package co.com.bancolombia.api.transaction;

import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public interface TransactionExecutor {
    <T> Mono<T> executeTransaction(Supplier<Mono<T>> supplier);
}
