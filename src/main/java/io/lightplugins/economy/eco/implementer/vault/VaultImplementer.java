package io.lightplugins.economy.eco.implementer.vault;

import io.lightplugins.economy.LightEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class VaultImplementer implements Economy {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "LightEconomyV6";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String s) {
        LightEconomy.api.getDebugPrinting().print("hasAccount player");
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        LightEconomy.api.getDebugPrinting().print("hasAccount player");
        return true;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        LightEconomy.api.getDebugPrinting().print("hasAccount player in world " + s );
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        LightEconomy.api.getDebugPrinting().print("hasAccount player in world " + s );
        return true;
    }

    @Override
    public double getBalance(String s) {
        return 2000;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        LightEconomy.api.getDebugPrinting().print("getBalance player");
        return 2000;
    }

    @Override
    public double getBalance(String s, String s1) {
        LightEconomy.api.getDebugPrinting().print("getBalance player in world" + s);
        return 2000;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        LightEconomy.api.getDebugPrinting().print("getBalance player in world" + s);
        return 2000;
    }

    @Override
    public boolean has(String s, double v) {
        LightEconomy.api.getDebugPrinting().print("Has player " + v);
        return true;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        LightEconomy.api.getDebugPrinting().print("Has player " + v);
        return true;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        LightEconomy.api.getDebugPrinting().print("Has player in world" + s + " " + v);
        return true;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        LightEconomy.api.getDebugPrinting().print("Has player in world" + s + " " + v);
        return true;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        LightEconomy.api.getDebugPrinting().print("Withdraw player in world" + s + " " + v);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        LightEconomy.api.getDebugPrinting().print("Withdraw player " + v);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        LightEconomy.api.getDebugPrinting().print("Withdraw player in world" + s + " " + v);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        LightEconomy.api.getDebugPrinting().print("Withdraw player in world" + s + " " + v);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        LightEconomy.api.getDebugPrinting().print("Deposit player in world" + s + " " + v);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        LightEconomy.api.getDebugPrinting().print("Deposit player " + v);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        LightEconomy.api.getDebugPrinting().print("Deposit player in world" + s + " " + v);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        LightEconomy.api.getDebugPrinting().print("Deposit player in world" + s + " " + v);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
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
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
