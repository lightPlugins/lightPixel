package io.lightplugins.economy.eco;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.economy.eco.config.SettingParams;
import io.lightplugins.economy.eco.implementer.events.CreatePlayerOnJoin;
import io.lightplugins.economy.eco.implementer.vault.VaultImplementer;
import io.lightplugins.economy.eco.implementer.vaulty.VaultyImplementer;
import io.lightplugins.economy.eco.interfaces.AccountHolder;
import io.lightplugins.economy.eco.manager.QueryManager;
import io.lightplugins.light.api.LightModule;
import io.lightplugins.light.api.commands.SubCommand;
import io.lightplugins.light.api.files.FileManager;
import io.lightplugins.vaulty.api.economy.VaultyEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class LightEco implements LightModule {

    public static LightEco instance;
    public boolean isModuleEnabled = false;
    public Economy economy = null;
    private QueryManager queryManager;
    public static VaultyEconomy economyVaultyService;
    public static net.milkbowl.vault.economy.Economy economyVaultService;

    public final String moduleName = "eco";
    public final String adminPerm = "lighteconomy." + moduleName + ".admin";
    public final static String tablePrefix = "lighteco_";
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    private SettingParams settingParams;

    public VaultyEconomy vaultyProvider;
    public VaultyImplementer vaultyImplementer;

    private net.milkbowl.vault.economy.Economy vaultProvider;
    private VaultImplementer vaultImplementer;

    private FileManager settings;
    private FileManager language;

    @Override
    public void enable() {
        LightEconomy.api.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Starting the core module " + this.moduleName);
        instance = this;
        LightEconomy.api.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Creating default files for core module " + this.moduleName);
        initFiles();
        this.settingParams = new SettingParams(this);
        LightEconomy.api.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Selecting module language for core module " + this.moduleName);
        selectLanguage();
        LightEconomy.api.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Registering subcommands for core module " + this.moduleName + "...");
        initSubCommands();
        this.isModuleEnabled = true;
        LightEconomy.api.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Successfully started core module " + this.moduleName + "!");
        vaultyImplementer = new VaultyImplementer();
        vaultImplementer = new VaultImplementer();
        hookVault();

        if(!initDatabase()) {
            LightEconomy.api.getDebugPrinting().print("§4Failed to initialize start sequence while enabling module §c" + this.moduleName);
            disable();
        }


        registerEvents();
        RegisteredServiceProvider<VaultyEconomy> vaultyRSP =
                Bukkit.getServer().getServicesManager().getRegistration(VaultyEconomy.class);
        RegisteredServiceProvider<Economy> vaultRSP =
                Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        economyVaultyService = vaultyRSP.getProvider();
        economyVaultService = vaultRSP.getProvider();

        //getQueryManager().createEcoTable();

    }

    @Override
    public void disable() {
        this.isModuleEnabled = false;
        unhookVault();
        LightEconomy.api.getDebugPrinting().print("Disabled module " + this.moduleName);
    }

    @Override
    public void reload() {
        enable();
    }

    @Override
    public boolean isEnabled() {
        return this.isModuleEnabled;
    }

    @Override
    public String getName() {
        return moduleName;
    }

    public FileManager getSettings() { return settings; }

    public FileManager getLanguage() { return language; }

    private void selectLanguage() {
        this.language = LightEconomy.instance.selectLanguage(settingParams.getModuleLanguage(), moduleName);
    }

    private void initFiles() {
        this.settings = new FileManager(
                LightEconomy.instance, moduleName + "/settings.yml", true);
    }

    private void initSubCommands() {

    }

    public QueryManager getQueryManager() { return this.queryManager; }

    private void hookVault() {
        this.vaultyProvider = vaultyImplementer;
        this.vaultProvider = vaultImplementer;
        Bukkit.getServicesManager().register(VaultyEconomy.class, vaultyProvider, LightEconomy.instance, ServicePriority.Highest);
        Bukkit.getConsoleSender().sendMessage(LightEconomy.consolePrefix +
                "Vaulty successfully hooked with highest priority into " + LightEconomy.instance.getName());
        Bukkit.getServicesManager().register(Economy.class, this.vaultProvider, LightEconomy.instance, ServicePriority.Highest);
        Bukkit.getConsoleSender().sendMessage(LightEconomy.consolePrefix +
                "Vault successfully hooked with highest priority into " + LightEconomy.instance.getName());


    }

    private void unhookVault() {
        Bukkit.getServicesManager().unregister(VaultyEconomy.class, this.vaultyProvider);
        Bukkit.getConsoleSender().sendMessage(LightEconomy.consolePrefix +
                "Vaulty successfully unhooked from " + LightEconomy.instance.getName());
        Bukkit.getServicesManager().unregister(Economy.class, this.vaultProvider);
        Bukkit.getConsoleSender().sendMessage(LightEconomy.consolePrefix +
                "Vault successfully unhooked from " + LightEconomy.instance.getName());
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new CreatePlayerOnJoin(), LightEconomy.instance);
    }

    public SettingParams getSettingParams() {
        return settingParams;
    }

    private boolean initDatabase() {
        try {
            this.queryManager = new QueryManager(LightEconomy.api.getConnection());
            queryManager.createEcoTable();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
