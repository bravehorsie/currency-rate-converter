package pl.cleankod.exchange.core.usecase;

import pl.cleankod.exchange.core.domain.Account;
import pl.cleankod.exchange.core.gateway.AccountService;

import java.util.Currency;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final FindAccountAndConvertCurrencyUseCase findAccountAndConvertCurrencyUseCase;
    private final FindAccountUseCase findAccountUseCase;

    public AccountServiceImpl(FindAccountAndConvertCurrencyUseCase findAccountAndConvertCurrencyUseCase, FindAccountUseCase findAccountUseCase) {
        this.findAccountAndConvertCurrencyUseCase = findAccountAndConvertCurrencyUseCase;
        this.findAccountUseCase = findAccountUseCase;
    }

    @Override
    public Optional<Account> findAccount(Account.Id accountId, String currencyCode) {
        return Optional.ofNullable(currencyCode)
                .map(cc -> findAccountAndConvertCurrencyUseCase.execute(accountId, Currency.getInstance(cc)))
                .orElse(findAccountUseCase.execute(accountId));
    }

    @Override
    public Optional<Account> findAccount(Account.Number accountNumber, String currencyCode) {
        return Optional.ofNullable(currencyCode)
                .map(cc -> findAccountAndConvertCurrencyUseCase.execute(accountNumber, Currency.getInstance(cc)))
                .orElse(findAccountUseCase.execute(accountNumber));
    }

}
