package co.com.bancolombia.api.transaction;

import co.com.bancolombia.usecase.transaction.TransactionExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;


@Component
public class ReactiveTransactionExecutor implements TransactionExecutor {

    private final TransactionalOperator transactionalOperator;

    public ReactiveTransactionExecutor(TransactionalOperator transactionalOperator) {
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public <T> Mono<T> executeTransaction(Supplier<Mono<T>> supplier) {
        return transactionalOperator.transactional(supplier.get());
    }
}
