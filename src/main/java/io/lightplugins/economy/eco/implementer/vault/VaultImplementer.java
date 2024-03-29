package io.lightplugins.economy.eco.implementer.vault;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.economy.eco.LightEco;
import io.lightplugins.light.Light;
import io.lightplugins.light.api.util.CheckForTownyUUID;
import io.lightplugins.light.api.util.NumberFormatter;
import io.lightplugins.vaulty.api.economy.VaultyResponse;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class VaultImplementer implements Economy {
    @Override
    public boolean isEnabled() {
        return LightEco.economyVaultService.isEnabled();
    }

    @Override
    public String getName() {
        return LightEco.economyVaultyService.getName();
    }

    @Override
    public boolean hasBankSupport() {
        return LightEco.economyVaultyService.hasBankSupport();
    }

    @Override
    public int fractionalDigits() {
        return LightEco.economyVaultyService.fractionalDigits();
    }

    @Override
    public String format(double v) {
        return LightEco.economyVaultyService.format(new BigDecimal(v));
    }

    @Override
    public String currencyNamePlural() {
        return LightEco.economyVaultyService.currencyNamePlural();
    }

    @Override
    public String currencyNameSingular() {
        return LightEco.economyVaultyService.currencyNameSingular();
    }

    @Override
    public boolean hasAccount(String s) {
        UUID uuid;
        OfflinePlayer offlinePlayer = Bukkit.getPlayer(s);

        if(offlinePlayer == null) {
            if(Light.isTowny) {
                uuid = CheckForTownyUUID.getTownyUUID(s);

                if(uuid == null) {
                    return false;
                }

                return LightEco.economyVaultyService.hasAccount(uuid);
            }
            return false;
        }

        return LightEco.economyVaultyService.hasAccount(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return hasAccount(offlinePlayer.getName());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return hasAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return hasAccount(offlinePlayer.getName());
    }

    @Override
    public double getBalance(String s) {
        UUID uuid;
        OfflinePlayer offlinePlayer = Bukkit.getPlayer(s);

        if(offlinePlayer == null) {
            if(Light.isTowny) {
                uuid = CheckForTownyUUID.getTownyUUID(s);

                if(uuid == null) {
                    return 0;
                }

                return NumberFormatter.formatDouble(LightEco.economyVaultyService.getBalance(uuid));
            }
            return 0;
        }

        return NumberFormatter.formatDouble(LightEco.economyVaultyService.getBalance(offlinePlayer.getUniqueId()));
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return getBalance(offlinePlayer.getName());
    }

    @Override
    public double getBalance(String s, String s1) {
        return getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer.getName());
    }

    @Override
    public boolean has(String s, double v) {
        UUID uuid;
        OfflinePlayer offlinePlayer = Bukkit.getPlayer(s);

        if(offlinePlayer == null) {
            if(Light.isTowny) {
                uuid = CheckForTownyUUID.getTownyUUID(s);

                if(uuid == null) {
                    return false;
                }

                return LightEco.economyVaultyService.has(uuid, NumberFormatter.convertToBigDecimal(v));
            }
            return false;
        }

        return LightEco.economyVaultyService.has(offlinePlayer.getUniqueId(), NumberFormatter.convertToBigDecimal(v));
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return has(offlinePlayer.getName(), v);
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return has(s, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {

        if(!hasAccount(s)) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE,
                    "[Vault] Account not found");
        }

        if(!has(s, v)) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE,
                    "[Vault] Not enough money");
        }

        UUID uuid;
        OfflinePlayer offlinePlayer = Bukkit.getPlayer(s);

        if(offlinePlayer == null) {
            if(Light.isTowny) {
                uuid = CheckForTownyUUID.getTownyUUID(s);

                if(uuid == null) {
                    return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE,
                            "[Vault] Withdrew failed: Towny account not found");
                }

                VaultyResponse vr = LightEco.economyVaultyService.withdrawPlayer(uuid, NumberFormatter.convertToBigDecimal(v));

                if(vr.transactionSuccess()) {
                    return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
                }
                return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE,
                        "[Vault] Withdrew failed");
            }

            return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE,
                    "[Vault] Withdrew failed");
        }
        if(LightEco.economyVaultyService.withdrawPlayer(offlinePlayer.getUniqueId(), NumberFormatter.convertToBigDecimal(v))
                .transactionSuccess()) {
            return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE,
                "[Vault] Withdrew failed");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        if(!hasAccount(s)) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE,
                    "[Vault] Account not found");
        }

        UUID uuid;
        OfflinePlayer offlinePlayer = Bukkit.getPlayer(s);

        if(offlinePlayer == null) {
            if(Light.isTowny) {
                uuid = CheckForTownyUUID.getTownyUUID(s);

                if(uuid == null) {
                    return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE,
                            "[Vault] Deposit failed: Towny account not found");
                }

                VaultyResponse vr = LightEco.economyVaultyService.depositPlayer(uuid, NumberFormatter.convertToBigDecimal(v));

                if(vr.transactionSuccess()) {
                    return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
                }
                return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE,
                        "[Vault] Deposit failed");
            }

            return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE,
                    "[Vault] Deposit failed");
        }
        VaultyResponse vr = LightEco.economyVaultyService.depositPlayer(offlinePlayer.getUniqueId(), NumberFormatter.convertToBigDecimal(v));

        if(vr.transactionSuccess()) {
            return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE,
                "[Vault] Deposit failed");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        UUID uuid;
        OfflinePlayer offlinePlayer = Bukkit.getPlayer(s);

        if(offlinePlayer == null) {
            if(Light.isTowny) {
                uuid = CheckForTownyUUID.getTownyUUID(s);

                if(uuid == null) {
                    return false;
                }

                return LightEco.economyVaultyService.createPlayerAccount(uuid);
            }
            return false;
        }

        return LightEco.economyVaultyService.createPlayerAccount(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return createPlayerAccount(offlinePlayer.getName());
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return createPlayerAccount(s);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return createPlayerAccount(offlinePlayer.getName());
    }
}
