package net.vhill.item;

public enum VhillCategory {
    K3("3k", 30, 1, 5),
    K12("12k", 120, 3, 16),
    K32("32k", 320, 5, 32);

    private final String id;
    private final int maxDamage;
    private final int villagerLevel;
    private final int baseEmeraldCost;

    VhillCategory(String id, int maxDamage, int villagerLevel, int baseEmeraldCost) {
        this.id = id;
        this.maxDamage = maxDamage;
        this.villagerLevel = villagerLevel;
        this.baseEmeraldCost = baseEmeraldCost;
    }

    public String getId() {
        return id;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getVillagerLevel() {
        return villagerLevel;
    }

    public int getBaseEmeraldCost() {
        return baseEmeraldCost;
    }
}
