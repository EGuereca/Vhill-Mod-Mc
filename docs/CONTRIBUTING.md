# 🤝 Guía de Contribución y Extensión de Vhill Mod

Esta guía explica cómo colaborar en el desarrollo de **Vhill Mod** y cómo extender sus características (añadir nuevos sabores, nuevas categorías de durabilidad o nuevos ítems especiales crafteables) manteniendo la compatibilidad multi-loader.

---

## 🛠️ Requisitos Previos

1. **JDK 17**: Adoptium Eclipse Temurin u OpenJDK 17.
2. **Git**.
3. **IDE Recomendado**: IntelliJ IDEA con el plugin de Minecraft Development, o VSCode / Cursor.

---

## 🚀 Cómo Añadir un Nuevo Sabor de Vhill (Tutorial Paso a Paso)

Supongamos que deseas añadir un nuevo sabor: **Mango** con efecto de **Resistencia al Fuego (`FIRE_RESISTANCE`)**.

### Paso 1: Añadir el Sabor en el Enum [`VhillFlavor.java`](file:///home/enrique/Documents/mod-mc/common/src/main/java/net/vhill/item/VhillFlavor.java)
```java
public enum VhillFlavor {
    FRESA("fresa", MobEffects.REGENERATION, ChatFormatting.RED),
    MENTA("menta", MobEffects.MOVEMENT_SPEED, ChatFormatting.AQUA),
    ADRENALINA("adrenalina", MobEffects.DAMAGE_BOOST, ChatFormatting.GOLD),
    MANGO("mango", MobEffects.FIRE_RESISTANCE, ChatFormatting.YELLOW); // <- Nuevo sabor
    
    // ...
}
```

---

### Paso 2: Registrar las Variantes en [`ModItems.java`](file:///home/enrique/Documents/mod-mc/common/src/main/java/net/vhill/item/ModItems.java)
Crea las instancias correspondientes a las categorías deseadas (ej. 3k, 12k y 32k):

```java
// Mango (Resistencia al Fuego)
public static final RegistrySupplier<VhillItem> VHILL_MANGO_3K = registerVhill("vhill_mango_3k", VhillCategory.K3, VhillFlavor.MANGO);
public static final RegistrySupplier<VhillItem> VHILL_MANGO_12K = registerVhill("vhill_mango_12k", VhillCategory.K12, VhillFlavor.MANGO);
public static final RegistrySupplier<VhillItem> VHILL_MANGO_32K = registerVhill("vhill_mango_32k", VhillCategory.K32, VhillFlavor.MANGO);
```

---

### Paso 3: Asignar los Comercios en [`ModVillagerTrades.java`](file:///home/enrique/Documents/mod-mc/common/src/main/java/net/vhill/village/ModVillagerTrades.java)
Añade las ofertas a los aldeanos Clérigos en los niveles pertinentes usando siempre `LazyTrade`:

```java
// Nivel 1 (Novato):
TradeRegistry.registerVillagerTrade(VillagerProfession.CLERIC, 1,
    // ...
    new LazyTrade(new ItemStack(Items.EMERALD, 5), ModItems.VHILL_MANGO_3K, 8, 2, 0.05F)
);
```

---

### Paso 4: Crear los Recursos Gráficos y Modelos

1. **Texturas Pixel Art (16x16 PNG)**:
   Guardar en `common/src/main/resources/assets/vhill/textures/item/`:
   - `vhill_mango_3k.png`
   - `vhill_mango_12k.png`
   - `vhill_mango_32k.png`

2. **Modelos JSON**:
   Guardar en `common/src/main/resources/assets/vhill/models/item/vhill_mango_3k.json`:
   ```json
   {
     "parent": "minecraft:item/generated",
     "textures": {
       "layer0": "vhill:item/vhill_mango_3k"
     }
   }
   ```
   *(Repetir para 12k y 32k)*.

---

### Paso 5: Añadir Traducciones de Idioma

1. **Inglés** (`common/src/main/resources/assets/vhill/lang/en_us.json`):
   ```json
   "item.vhill.vhill_mango_3k": "Mango Vhill (3k)",
   "item.vhill.vhill_mango_12k": "Mango Vhill (12k)",
   "item.vhill.vhill_mango_32k": "Mango Vhill (32k)",
   "flavor.vhill.mango": "Mango (Fire Resistance)"
   ```

2. **Español** (`common/src/main/resources/assets/vhill/lang/es_es.json`):
   ```json
   "item.vhill.vhill_mango_3k": "Vhill de Mango (3k)",
   "item.vhill.vhill_mango_12k": "Vhill de Mango (12k)",
   "item.vhill.vhill_mango_32k": "Vhill de Mango (32k)",
   "flavor.vhill.mango": "Mango (Resistencia al Fuego)"
   ```

---

## ⚖️ Reglas de Código y Buenas Prácticas

1. **Seguridad en Servidores Dedicados**:
   - Nunca importes `net.minecraft.client.*` en el código de `:common`.
   - Lógica de mundo: usa `if (!level.isClientSide)` para otorgar efectos, aplicar daño al ítem o spawnear entidades.
   - Lógica visual: usa `if (level.isClientSide)` para partículas y sonidos específicos del cliente.
2. **Evaluación Perezosa**:
   - Nunca llames a `.get()` en un `RegistrySupplier` durante la inicialización o en constructores estáticos. Pasa siempre el proveedor `Supplier<? extends Item>` o utilízalo dentro de lambdas.
3. **Mapeos Oficiales**:
   - El proyecto utiliza Mappings oficiales de Mojang (`Item.Properties`, `MobEffectInstance`, etc.).

---

## 🧪 Verificación Antes de Crear un Pull Request

Ejecuta las siguientes tareas de Gradle para comprobar que todo compila y funciona en ambos cargadores:

```bash
# 1. Compilación completa y generación de JARs
./gradlew build

# 2. Prueba manual en Fabric
./gradlew :fabric:runClient

# 3. Prueba manual en Forge
./gradlew :forge:runClient
```
