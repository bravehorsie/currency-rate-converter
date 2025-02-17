package pl.cleankod.exchange.core.gateway;

import org.springframework.lang.Nullable;
import pl.cleankod.exchange.core.domain.Account;

import java.util.Optional;

public interface AccountService {
    /**
     * Find an account by id, convert currency if provided.
     * @param accountId id of an account
     * @param currencyCode account currency - nullable
     * @return Account, with balance converted to target currency
     */
    Optional<Account> findAccount(Account.Id accountId, @Nullable String currencyCode);

    /**
     * Find an account by number, convert currency if provided.
     * @param accountNumber number of an account
     * @param currencyCode account currency - nullable
     * @return Account, with balance converted to target currency
     */
    Optional<Account> findAccount(Account.Number accountNumber, @Nullable String currencyCode);
}
