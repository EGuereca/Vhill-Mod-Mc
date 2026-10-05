# SYSTEM PROMPT / CONTEXTO
**Objetivo:** Migrar un mod de Minecraft 1.20.1 nativo de Fabric a una arquitectura Multi-loader (Fabric + Forge) utilizando Architectury API y el plugin Architectury Loom.
**Audiencia:** Asistente de IA / LLM encargado de ejecutar la refactorización del código.
**Estructura de directorios objetivo:** Proyecto multimodular de Gradle (`common`, `fabric`, `forge`).

---

## FASE 1: Inicialización del Entorno (Scaffolding)

1.  **Clonar Plantilla Base:** No se debe mutar el proyecto de Fabric existente. Se debe instanciar un nuevo proyecto usando el repositorio `architectury-templates`.
    *   **Rama objetivo:** `1.20.1`
2.  **Mapeo de `gradle.properties`:**
    *   Copiar los metadatos del proyecto original al nuevo `gradle.properties` en la raíz.
    *   *Variables clave a definir:*
        *   `minecraft_version=1.20.1`
        *   `architectury_version=` (última compatible con 1.20.1)
        *   `fabric_loader_version=`
        *   `fabric_api_version=`
        *   `forge_version=`
        *   `archives_base_name=` (Nombre del mod)
        *   `maven_group=` (Ej. `com.modder.mimod`)

---

## FASE 2: Refactorización de Registros (El Módulo `common`)

Todo el código agnóstico a la plataforma (bloques, ítems, entidades, lógicas, matemáticas) debe ser movido al módulo `common/src/main/java/{maven_group}/`.

### Traducción de Registros (Registry Pattern)
El sistema nativo de Fabric (`Registry.register`) debe ser reemplazado por la API unificada de Architectury (`DeferredRegister`).

**PATRÓN ANTES (Fabric Nativo):**
```java
// fabric/src/.../Registry.java
public static final Item MI_ITEM = new Item(new FabricItemSettings());

public static void register() {
    Registry.register(Registries.ITEM, new Identifier("mi_mod", "mi_item"), MI_ITEM);
}
```

**PATRÓN DESPUÉS (Architectury Common):**
```java
// common/src/.../ModItems.java
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;

public class ModItems {
    // 1. Instanciar el registro diferido
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("mi_mod", Registries.ITEM);

    // 2. Registrar el ítem
    public static final RegistrySupplier<Item> MI_ITEM = ITEMS.register("mi_item", 
        () -> new Item(new Item.Properties().arch$tab(ModTabs.MI_TAB)));

    // 3. Método para invocar en el inicializador común
    public static void register() {
        ITEMS.register();
    }
}
```
*Instrucción para la IA:* Escanear el código fuente original en busca de `Registry.register` y transformarlo iterativamente al patrón `DeferredRegister`.

---

## FASE 3: Configuración de Entrypoints (Puntos de Entrada)

El mod ahora requiere 3 puntos de entrada. Uno principal en `common` y dos delegados en las plataformas específicas.

### 3.1. Common Entrypoint
Ruta: `common/src/main/java/{maven_group}/{ModName}.java`
```java
public class MiMod {
    public static final String MOD_ID = "mi_mod";

    public static void init() {
        // Inicializar registros comunes
        ModItems.register();
        ModBlocks.register();
    }
}
```

### 3.2. Fabric Entrypoint
Ruta: `fabric/src/main/java/{maven_group}/fabric/{ModName}Fabric.java`
*Requisito:* Debe implementar `ModInitializer`. Declarado en `fabric.mod.json`.
```java
public class MiModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Delegar al módulo común
        MiMod.init();
    }
}
```

### 3.3. Forge Entrypoint
Ruta: `forge/src/main/java/{maven_group}/forge/{ModName}Forge.java`
*Requisito:* Anotado con `@Mod(MOD_ID)`. Declarado en `mods.toml`.
```java
@Mod(MiMod.MOD_ID)
public class MiModForge {
    public MiModForge() {
        // Preparar bus de eventos de Forge si es necesario (EventBuses)
        EventBuses.registerModEventBus(MiMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        
        // Delegar al módulo común
        MiMod.init();
    }
}
```

---

## FASE 4: Manejo de Mixins (Si aplica)

Si el mod original inyectaba código (Mixins), la estructura debe dividirse:

1.  **Mixins Comunes:** Ubicados en `common/src/main/resources/{mod_id}.mixins.json`. Aplican inyecciones a código vainilla que funciona igual en Forge y Fabric.
2.  **Mixins Específicos (Opcional):** Si un Mixin choca con la estructura de Forge, debe moverse a `forge/src/main/resources/{mod_id}-forge.mixins.json` o su equivalente en Fabric.
3.  **Configuración de compilación:** Asegurar que los `.json` de los mixins estén referenciados en los archivos de metadatos respectivos (`fabric.mod.json` y `mods.toml`).

---

## FASE 5: Construcción y Compilación

Una vez refactorizado el código, las instrucciones CLI para validación son:

*   **Generar fuentes y configurar entorno IDE:** `./gradlew genSources`
*   **Compilar y construir binarios (Jars):** `./gradlew build`
    *   *Output esperado:* En la carpeta `fabric/build/libs/` estará el `.jar` de Fabric, y en `forge/build/libs/` estará el `.jar` de Forge. Ambos listos para producción.