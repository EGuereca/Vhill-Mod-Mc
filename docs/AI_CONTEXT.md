# 🤖 AI_CONTEXT.md - System Context for Generative AI & Autonomous Agents

> **Notice for AI Models & Coding Assistants**:
> This file contains authoritative system architecture, coding invariants, conventions, and operational instructions for **Vhill-Mod-Mc**. Consult this file before proposing or executing changes in this repository.

---

## 1. Project Metadata & Technical Stack

| Property | Value | Notes |
| :--- | :--- | :--- |
| **Project Name** | `Vhill-Mod-Mc` | Repository name on GitHub |
| **Mod ID** | `vhill` | Lowercase namespace used across assets, tags, and registries |
| **Minecraft Version** | `1.20.1` | Fixed target version |
| **Target Loaders** | `Fabric` & `Forge` | Dual support via Architectury |
| **Multi-Loader Tool** | **Architectury Loom 1.6** + **Architect Plugin 3.4** | Multi-project setup |
| **Java Version** | **Java 17 (JDK 17)** | Configured in Gradle toolchain and `gradle.properties` |
| **Gradle Version** | `Gradle 8.8` | Configured in `gradle/wrapper/gradle-wrapper.properties` |
| **Mappings Layer** | **Official Mojang Mappings** | `loom.officialMojangMappings()` (Yarn is NOT used) |
| **Base Package** | `net.vhill` | Subpackages: `.item`, `.village`, `.fabric`, `.forge` |

---

## 2. Directory Structure & Responsibilities

```
mod-mc/
├── common/                  # LOADER-AGNOSTIC CODE AND RESOURCES
│   ├── build.gradle         # Common dependencies (Architectury API, Minecraft)
│   └── src/main/
│       ├── java/net/vhill/
│       │   ├── VhillMod.java               # Global entrypoint (VhillMod.init())
│       │   ├── item/                       # Item definitions & registry
│       │   │   ├── ModItemGroup.java       # Creative Tab definition
│       │   │   ├── ModItems.java           # DeferredRegister<Item>
│       │   │   ├── VhillCategory.java      # Durability enum (3k, 12k, 32k)
│       │   │   ├── VhillFlavor.java        # Flavors & MobEffects enum
│       │   │   ├── VhillItem.java          # Base reusable vape item
│       │   │   └── WaxItem.java            # Multi-effect craftable 32k item
│       │   └── village/
│       │       └── ModVillagerTrades.java  # Cleric trade offers with LazyTrade
│       └── resources/
│           ├── architectury.common.json
│           ├── pack.mcmeta                 # pack_format: 15
│           ├── assets/vhill/               # Textures (16x16), models, lang
│           └── data/vhill/recipes/         # JSON Recipes (wax.json shapeless)
├── fabric/                  # FABRIC SPECIFIC ENTRYPOINT & METADATA
│   ├── build.gradle         # Fabric Loader & Fabric API dependencies + shadow
│   └── src/main/
│       ├── java/net/vhill/fabric/VhillModFabric.java  # Implements ModInitializer
│       └── resources/fabric.mod.json
├── forge/                   # FORGE SPECIFIC ENTRYPOINT & METADATA
│   ├── build.gradle         # Forge dependency + shadow
│   ├── gradle.properties    # loom.platform=forge
│   └── src/main/
│       ├── java/net/vhill/forge/VhillModForge.java    # Annotated with @Mod
│       └── resources/
│           ├── META-INF/mods.toml
│           └── pack.mcmeta                 # Required by Forge ModPackResources
└── docs/                    # Architectural & onboarding documentation
```

---

## 3. Strict Invariants & Anti-Patterns

### ⚠️ Invariant 1: Lazy Registry Evaluation (Crucial for Forge)
- **Rule**: NEVER call `RegistrySupplier.get()` during static class initialization or inside `VhillMod.init()`.
- **Reason**: In Minecraft Forge, mod constructors run **before** `RegisterEvent` fires. Calling `.get()` before the registry phase throws `NullPointerException: Registry Object not present`.
- **Implementation**: In [`ModVillagerTrades.java`](file:///home/enrique/Documents/mod-mc/common/src/main/java/net/vhill/village/ModVillagerTrades.java), we use `ModVillagerTrades.LazyTrade`, which implements `VillagerTrades.ItemListing` and delays calling `supplier.get()` until `getOffer()` is invoked in-game.

### ⚠️ Invariant 2: Dedicated Server Safety (No Client Imports in Common)
- **Rule**: NEVER import `net.minecraft.client.*` or `net.fabricmc.api.*` into any class in `:common`.
- **Reason**: Importing client classes into common classes triggers `ClassNotFoundException` or `NoClassDefFoundError` on dedicated servers (*headless server environments*).
- **Rule on Tooltips**: `Item.appendHoverText(...)` is part of vanilla `Item` in common code. Do NOT annotate it with `@Environment(EnvType.CLIENT)`.

### ⚠️ Invariant 3: Forge Resource Pack Descriptor
- **Rule**: Forge requires a valid `pack.mcmeta` file at the root of `forge/src/main/resources/` and `common/src/main/resources/` with `"pack_format": 15`.
- **Reason**: Without this file, Forge fails to generate a valid `ResourcePackInfo` and displays `Missing metadata in pack mod:vhill`.

### ⚠️ Invariant 4: Non-Destructive Item Wear
- **Rule**: Vhills do NOT break when durability reaches 0.
- **Implementation**: Durability is managed via `stack.getDamageValue()` and `stack.setDamageValue(damage + 1)` manually in `finishUsingItem()`. When `currentDamage >= maxDamage`, the item is NOT damaged further; instead, `MobEffects.POISON` (200 ticks) is applied to the player.

---

## 4. Current Mod Catalog & Data Spec

| Item ID | Class | Category | Max Damage (Uses) | Effects / Mechanics | Source |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `vhill_fresa_3k` | `VhillItem` | `3k` | 30 | Regeneración I (10s) | Clérigo Nivel 1 (5 Esmeraldas) |
| `vhill_fresa_12k` | `VhillItem` | `12k` | 120 | Regeneración I (10s) | Clérigo Nivel 3 (15 Esmeraldas) |
| `vhill_fresa_32k` | `VhillItem` | `32k` | 320 | Regeneración I (10s) | Clérigo Nivel 5 (32 Esmeraldas) |
| `vhill_menta_3k` | `VhillItem` | `3k` | 30 | Velocidad I (10s) | Clérigo Nivel 1 (5 Esmeraldas) |
| `vhill_menta_12k` | `VhillItem` | `12k` | 120 | Velocidad I (10s) | Clérigo Nivel 3 (15 Esmeraldas) |
| `vhill_menta_32k` | `VhillItem` | `32k` | 320 | Velocidad I (10s) | Clérigo Nivel 5 (32 Esmeraldas) |
| `vhill_adrenalina_3k` | `VhillItem` | `3k` | 30 | Fuerza I (10s) | Clérigo Nivel 2 (6 Esmeraldas) |
| `vhill_adrenalina_12k` | `VhillItem` | `12k` | 120 | Fuerza I (10s) | Clérigo Nivel 4 (18 Esmeraldas) |
| `vhill_adrenalina_32k` | `VhillItem` | `32k` | 320 | Fuerza I (10s) | Clérigo Nivel 5 (36 Esmeraldas) |
| `wax` | `WaxItem` | `32k` | 320 | Regen I + Fuerza I + Velocidad I + Náusea I + Visión Nocturna I (10s) | Crafteo Shapeless (3 Vhills 32k) |

---

## 5. Build & Test Commands Cheatsheet

```bash
# Full build of both loaders
./gradlew build

# Build only Forge JAR
./gradlew :forge:build

# Build only Fabric JAR
./gradlew :fabric:build

# Run client locally
./gradlew :fabric:runClient
./gradlew :forge:runClient

# Inspect built Forge JAR contents
jar tf forge/build/libs/vhill-forge-1.0.0.jar
```
