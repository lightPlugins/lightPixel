package io.lightplugins.economy.eco.implementer.vaulty;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.economy.eco.LightEco;
import io.lightplugins.economy.util.NumberFormatter;
import io.lightplugins.vaulty.api.economy.VaultyEconomy;
import io.lightplugins.vaulty.api.economy.VaultyResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class VaultyImplementer implements VaultyEconomy {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean hasAsyncSupport() {
        return true;
    }

    @Override
    public String getName() {
        return LightEconomy.instance.getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public boolean hasVirtualCurrencySupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return LightEco.instance.getSettingParams().defaultCurrency().fractionalDigits();
    }

    @Override
    public String currencyNamePlural() {
        return LightEco.instance.getSettingParams().defaultCurrency().currencyPluralName();
    }

    @Override
    public String currencyNameSingular() {
        return LightEco.instance.getSettingParams().defaultCurrency().currencySingularName();
    }

    @Override
    public String format(BigDecimal bigDecimal) {
        return bigDecimal.setScale(fractionalDigits(), RoundingMode.HALF_DOWN).toString();
    }

    @Override
    public HashMap<UUID, BigDecimal> getAllAccounts() {
        return null;
    }

    @Override
    public CompletableFuture<HashMap<UUID, BigDecimal>> getAllAccountsAsync() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(UUID uuid) {
        return createPlayerAccountAsync(uuid).join();
    }

    @Override
    public CompletableFuture<Boolean> createPlayerAccountAsync(UUID uuid) {
        return hasAccountAsync(uuid).thenApplyAsync(hasAccount -> {
            if (hasAccount) {
                //LightEconomy.getDebugPrinting().print("Already has account for " + uuid);
                return false;
            }
            return LightEco.instance.getQueryManager().prepareNewAccount(uuid, false).join();
        });
    }


    @Override
    public boolean hasAccount(UUID uuid) {
        return hasAccountAsync(uuid).join();
    }

    @Override
    public CompletableFuture<Boolean> hasAccountAsync(UUID uuid) {
        return LightEco.instance.getQueryManager().getAccountHolder(uuid)
                .thenApplyAsync(accountHolder -> accountHolder != null)
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return false;
                });
    }

    @Override
    public BigDecimal getBalance(UUID uuid) {
        return getBalanceAsync(uuid).join();
    }

    @Override
    public CompletableFuture<BigDecimal> getBalanceAsync(UUID uuid) {
        return LightEco.instance.getQueryManager().getAccountHolder(uuid)
                .thenApplyAsync(accountHolder -> {
                    if (accountHolder == null) {
                        LightEconomy.getDebugPrinting().print("VAULTY: No account found for " + uuid);
                        return BigDecimal.ZERO;
                    }
                    LightEconomy.getDebugPrinting().print("VAULTY: " + accountHolder.getFormattedBalance());
                    return accountHolder.getBalance();
                });
    }

    @Override
    public boolean has(UUID uuid, BigDecimal bigDecimal) {
        return hasAsync(uuid, bigDecimal).join();
    }

    @Override
    public CompletableFuture<Boolean> hasAsync(UUID uuid, BigDecimal bigDecimal) {
        return LightEco.instance.getQueryManager().getAccountHolder(uuid)
                .thenApplyAsync(accountHolder -> {
                    if (accountHolder == null) {
                        return false;
                    }
                    LightEconomy.getDebugPrinting().print("VAULTY: checking has for Player account with uuid " + uuid);
                    LightEconomy.getDebugPrinting().print("VAULTY: " + accountHolder.getFormattedBalance() + " >= " + NumberFormatter.formatDouble(accountHolder.getBalance()));
                    return accountHolder.getFormattedBalance() >= NumberFormatter.formatDouble(accountHolder.getBalance());
                });
    }

    public VaultyResponse withdrawPlayer(UUID uuid, BigDecimal bigDecimal) {
        return withdrawPlayerAsync(uuid, bigDecimal).join();
    }

    @Override
    public CompletableFuture<VaultyResponse> withdrawPlayerAsync(UUID uuid, BigDecimal bigDecimal) {
        CompletableFuture<Boolean> hasAccountFuture = hasAccountAsync(uuid);

        return hasAccountFuture.thenComposeAsync(hasAccount -> {
            if (!hasAccount) {
                return CompletableFuture.completedFuture(new VaultyResponse(0, 0,
                        VaultyResponse.ResponseType.FAILURE, "[Vaulty] Account does not exist"));
            }

            CompletableFuture<Boolean> hasBalanceFuture = hasAsync(uuid, bigDecimal);
            return hasBalanceFuture.thenComposeAsync(hasBalance -> {
                if (!hasBalance) {
                    return CompletableFuture.completedFuture(new VaultyResponse(0, 0,
                            VaultyResponse.ResponseType.FAILURE, "[Vaulty] Insufficient funds"));
                }

                return LightEco.instance.getQueryManager().withdrawFromAccount(uuid, bigDecimal)
                        .thenApplyAsync(rowsUpdated -> {
                            if (rowsUpdated) {
                                return new VaultyResponse(0, 0, VaultyResponse.ResponseType.SUCCESS,
                                        "[Vaulty] Withdrawn successfully");
                            } else {
                                return new VaultyResponse(0, 0, VaultyResponse.ResponseType.FAILURE,
                                        "[Vaulty] Withdrawn failed");
                            }
                        });
            });
        });
    }

    @Override
    public VaultyResponse depositPlayer(UUID uuid, BigDecimal bigDecimal) {
        return depositPlayerAsync(uuid, bigDecimal).join();
    }

    @Override
    public CompletableFuture<VaultyResponse> depositPlayerAsync(UUID uuid, BigDecimal bigDecimal) {

        CompletableFuture<Boolean> hasAccountFuture = hasAccountAsync(uuid);

        return hasAccountFuture.thenComposeAsync(hasAccount -> {
            if (!hasAccount) {
                return CompletableFuture.completedFuture(new VaultyResponse(0, 0,
                        VaultyResponse.ResponseType.FAILURE, "[Vaulty] Account does not exist"));
            }

            return LightEco.instance.getQueryManager().depositFromAccount(uuid, bigDecimal)
                    .thenApplyAsync(rowsUpdated -> {
                        if (rowsUpdated) {
                            return new VaultyResponse(0, 0, VaultyResponse.ResponseType.SUCCESS,
                                    "[Vaulty] Deposit successfully");
                        } else {
                            return new VaultyResponse(0, 0, VaultyResponse.ResponseType.FAILURE,
                                    "[Vaulty] Deposit failed");
                        }
                    });
        });
    }

    @Override
    public VaultyResponse createBank(UUID uuid, UUID uuid1) {
        return null;
    }

    @Override
    public CompletableFuture<VaultyResponse> createBankAsync(UUID uuid, UUID uuid1) {
        return null;
    }

    @Override
    public VaultyResponse deleteBank(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<VaultyResponse> deleteBankAsync(UUID uuid) {
        return null;
    }

    @Override
    public VaultyResponse bankHas(UUID uuid, BigDecimal bigDecimal) {
        return null;
    }

    @Override
    public CompletableFuture<VaultyResponse> bankHasAsync(UUID uuid, BigDecimal bigDecimal) {
        return null;
    }

    @Override
    public VaultyResponse bankWithdraw(UUID uuid, BigDecimal bigDecimal) {
        return null;
    }

    @Override
    public CompletableFuture<VaultyResponse> bankWithdrawAsync(UUID uuid, BigDecimal bigDecimal) {
        return null;
    }

    @Override
    public VaultyResponse bankDeposit(UUID uuid, BigDecimal bigDecimal) {
        return null;
    }

    @Override
    public CompletableFuture<VaultyResponse> bankDepositAsync(UUID uuid, BigDecimal bigDecimal) {
        return null;
    }

    @Override
    public BigDecimal bankBalance(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<BigDecimal> bankBalanceAsync(UUID uuid) {
        return null;
    }

    @Override
    public Boolean isBankOwner(UUID uuid, UUID uuid1) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> isBankOwnerAsync(UUID uuid, UUID uuid1) {
        return null;
    }

    @Override
    public Boolean isBankMember(UUID uuid, UUID uuid1) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> isBankMemberAsync(UUID uuid, UUID uuid1) {
        return null;
    }

    @Override
    public List<UUID> getBanksAsUUID() {
        return null;
    }

    @Override
    public CompletableFuture<List<UUID>> getBanksAsUUIDAsync() {
        return null;
    }

    @Override
    public List<String> virtualCurrencyGetList() {
        return null;
    }

    @Override
    public String virtualCurrencyGetName(String s) {
        return null;
    }

    @Override
    public BigDecimal virtualCurrencyBalance(UUID uuid, String s) {
        return null;
    }

    @Override
    public CompletableFuture<BigDecimal> virtualCurrencyBalanceAsync(UUID uuid, String s) {
        return null;
    }

    @Override
    public VaultyResponse virtualCurrencyDeposit(UUID uuid, String s, BigDecimal bigDecimal) {
        return null;
    }

    @Override
    public CompletableFuture<VaultyResponse> virtualCurrencyDepositAsync(UUID uuid, String s, BigDecimal bigDecimal) {
        return null;
    }

    @Override
    public VaultyResponse virtualCurrencyWithdraw(UUID uuid, String s, BigDecimal bigDecimal) {
        return null;
    }

    @Override
    public CompletableFuture<VaultyResponse> virtualCurrencyWithdrawAsync(UUID uuid, String s, BigDecimal bigDecimal) {
        return null;
    }
}
