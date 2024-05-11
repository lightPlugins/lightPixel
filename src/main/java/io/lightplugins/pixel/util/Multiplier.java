package io.lightplugins.pixel.util;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.stats.Stat;
import com.willfp.ecoskills.stats.Stats;
import org.bukkit.entity.Player;

import java.util.Random;

public class Multiplier {

    private final Player player;
    private int MINING_FORTUNE;
    private int FARMING_FORTUNE;
    private int FORAGING_FORTUNE;

    public Multiplier(Player player) {
        this.player = player;
        initFortune();
    }


    private void initFortune() {
        Stat miningFortune = Stats.INSTANCE.getByID("mining_fortune");
        Stat farmingFortune = Stats.INSTANCE.getByID("farming_fortune");
        Stat foragingFortune = Stats.INSTANCE.getByID("foraging_fortune");

        if(miningFortune == null || farmingFortune == null || foragingFortune == null) {
            return;
        }

        this.MINING_FORTUNE = EcoSkillsAPI.getStatLevel(player, miningFortune);
        this.FARMING_FORTUNE = EcoSkillsAPI.getStatLevel(player, farmingFortune);
        this.FORAGING_FORTUNE = EcoSkillsAPI.getStatLevel(player, foragingFortune);

    }

    private int getMiningFortuneMultiplier() { return (MINING_FORTUNE / 100) + 1; }
    private double getMiningFortuneChance() { return (MINING_FORTUNE % 100); }
    private int getFarmingFortuneMultiplier() { return (FARMING_FORTUNE / 100) + 1; }
    private double getFarmingFortuneChance() { return  (FARMING_FORTUNE % 100);}
    private int getForagingFortuneMultiplier() { return (FORAGING_FORTUNE / 100) + 1; }
    private double getForagingFortuneChance() { return (FORAGING_FORTUNE % 100); }

    public int getMiningFortuneAmount() {
        if(checkPercentage(getMiningFortuneChance())) {
            return getMiningFortuneMultiplier() != 1 ? getMiningFortuneMultiplier() : 2;

        } else {
            if(getMiningFortuneMultiplier() == 1) {
                return 1;
            }
            return getMiningFortuneMultiplier() - 1;
        }
    }

    public int getFarmingFortuneAmount() {
        if(checkPercentage(getFarmingFortuneChance())) {
            return getFarmingFortuneMultiplier() != 1 ? getFarmingFortuneMultiplier() : 2;
        } else {
            if(getFarmingFortuneMultiplier() == 1) {
                return 1;
            }
            return getFarmingFortuneMultiplier() - 1;
        }
    }

    public int getForagingFortuneAmount() {
        if(checkPercentage(getForagingFortuneChance())) {
            return getForagingFortuneMultiplier() != 1 ? getForagingFortuneMultiplier() : 2;
        } else {
            if(getForagingFortuneMultiplier() == 1) {
                return 1;
            }
            return getForagingFortuneMultiplier() - 1;
        }
    }

    private boolean checkPercentage(double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percent value must be between 0 and 100");
        }
        Random random = new Random();
        double randomPercent = random.nextDouble() * 100;
        return randomPercent <= percent;
    }
}
