# 💨 Vhill Mod (Minecraft 1.20.1)

[![Minecraft 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-brightgreen.svg)](https://minecraft.net/)
[![Multi-Loader](https://img.shields.io/badge/Loader-Fabric%20%7C%20Forge-blue.svg)](https://architectury.dev/)
[![Architectury API](https://img.shields.io/badge/Dependency-Architectury%209.2.14-orange.svg)](https://modrinth.com/mod/architectury-api)
[![Java 17](https://img.shields.io/badge/Java-17-red.svg)](https://adoptium.net/)
[![License](https://img.shields.io/badge/License-MIT-lightgrey.svg)](LICENSE)

**Vhill Mod** es un mod para Minecraft 1.20.1 construido sobre una arquitectura **Multi-loader (Architectury)**, compatible de forma nativa tanto con **Fabric** como con **Forge**. Introduce una serie de dispositivos mágicos de inhalación (*Vhills*) con mecánicas de efectos dinámicos, desgaste no destructivo, progresión mediante aldeanos Clérigos y un ítem exclusivo de crafteo avanzado: el **Wax (32k)**.

---

## 🌟 Características Principales

### 🍓 Catálogo de Vhills Base (9 Variantes)
Los Vhills estándar se dividen en 3 categorías de durabilidad y 3 sabores elementales:

| Sabor | Efecto Activo (Nivel I) | Duración | Color / Formato |
| :--- | :--- | :--- | :--- |
| **Fresa** | Regeneración (`REGENERATION`) | 10 segundos (200 ticks) | Rojo / Suave |
| **Menta** | Velocidad (`SPEED`) | 10 segundos (200 ticks) | Verde azulado / Fresco |
| **Adrenalina** | Fuerza (`STRENGTH`) | 10 segundos (200 ticks) | Naranja / Intenso |

#### Categorías y Usos:
- **3k**: **30 usos** de durabilidad.
- **12k**: **120 usos** de durabilidad.
- **32k**: **320 usos** de durabilidad.

---

### 🍯 Ítem Especial: Wax (32k)
El **Wax** es el artefacto definitivo del mod.
- **Durabilidad**: 320 usos.
- **Multi-Efecto Simultáneo**: Al inhalarlo, otorga 5 efectos al mismo tiempo durante 10 segundos:
  1. **Regeneración I**
  2. **Fuerza I**
  3. **Velocidad I**
  4. **Náusea I** *(efecto secundario de sobrecarga sensorial)*
  5. **Visión Nocturna I**
- **Efectos Audiovisuales**: Genera un vapor místico denso combinando aliento de dragón y humo de fogata.
- **Crafteo Exclusivo**: Es el **único ítem crafteable** del mod (no se obtiene con aldeanos). Se elabora combinando sin orden fijo (*shapeless*) los tres Vhills de categoría 32k:
  - 1x Vhill Fresa (32k) + 1x Vhill Menta (32k) + 1x Vhill Adrenalina (32k) ➔ **1x Wax (32k)**.

---

### ⚙️ Mecánicas de Juego

1. **Uso e Inhalación**:
   - Mantener pulsado clic derecho (~1.25 segundos con animación de cuerno) para consumir un uso.
   - Emite partículas de vapor personalizadas y sonido al completarse.
2. **Desgaste No Destructivo**:
   - Los ítems no desaparecen al quedarse sin usos. La barra de durabilidad se vacía completamente y el ítem permanece en el inventario marcado como **Agotado**.
3. **Penalización por Ítem Agotado (Veneno)**:
   - Si intentas inhalar un Vhill o Wax agotado (0 usos), recibirás **Veneno** durante 10 segundos.
4. **Comercio con Aldeanos (Clérigos)**:
   - Se integran en los intercambios naturales de los aldeanos con profesión **Clérigo**:
     - **Nivel 1 (Novato)**: Vhills 3k de Fresa y Menta (5 Esmeraldas).
     - **Nivel 2 (Aprendiz)**: Vhill 3k de Adrenalina (6 Esmeraldas).
     - **Nivel 3 (Compañero)**: Vhills 12k de Fresa y Menta (15 Esmeraldas).
     - **Nivel 4 (Experto)**: Vhill 12k de Adrenalina (18 Esmeraldas).
     - **Nivel 5 (Maestro)**: Vhills 32k de Fresa, Menta (32 Esmeraldas) y Adrenalina (36 Esmeraldas).

---

## 📂 Estructura del Repositorio

El proyecto utiliza **Architectury Loom** y Gradle multi-módulo para compilar ambas plataformas desde una base de código compartida:

```
mod-mc/
├── common/                  # Código y recursos agnósticos del cargador (Mojang mappings)
│   ├── src/main/java/       # Lógica común (VhillMod, ítems, trades perezosos)
│   └── src/main/resources/  # Texturas 16x16, modelos, recetas, lang y pack.mcmeta
├── fabric/                  # Subproyecto para Fabric
│   ├── src/main/java/       # Entrypoint Fabric (VhillModFabric)
│   └── src/main/resources/  # fabric.mod.json
├── forge/                   # Subproyecto para Forge
│   ├── src/main/java/       # Entrypoint Forge (VhillModForge con @Mod)
│   └── src/main/resources/  # META-INF/mods.toml y pack.mcmeta
├── docs/                    # Documentación técnica y guías de arquitectura
│   ├── ARCHITECTURE.md      # Detalles de diseño multi-loader y flujos de ejecución
│   ├── CONTRIBUTING.md      # Guía para extender el mod (nuevos sabores, ítems, etc.)
│   └── AI_CONTEXT.md        # Documento optimizado para modelos de IA generativa
├── build.gradle             # Configuración Gradle raíz y orquestación de subproyectos
├── gradle.properties        # Versiones de Minecraft, loaders y dependencias
└── settings.gradle          # Inclusión de submódulos (:common, :fabric, :forge)
```

---

## 🛠️ Requisitos de Compilación y Ejecución

- **Java**: JDK 17 (configurado automáticamente vía toolchains de Gradle).
- **Minecraft**: 1.20.1
- **Dependencia en Runtime**: **Architectury API 9.2.14+** (requerido tanto en cliente como en servidor).

---

## 🚀 Compilación y Desarrollo

### Compilar los JARs de Distribución
Para compilar ambos JARs listos para producción:
```bash
./gradlew build
```
Los artefactos listos para compartir se generarán en:
- **Forge**: `forge/build/libs/vhill-forge-1.0.0.jar`
- **Fabric**: `fabric/build/libs/vhill-fabric-1.0.0.jar`

### Probar en Entorno de Desarrollo (Loom)
- **Cliente Fabric**:
  ```bash
  ./gradlew :fabric:runClient
  ```
- **Cliente Forge**:
  ```bash
  ./gradlew :forge:runClient
  ```

---

## 📥 Instalación para Jugadores y Servidores

### En Forge:
1. Instalar **Minecraft 1.20.1 con Forge** (ej. Forge 47.4.26).
2. Descargar [Architectury API para Forge 1.20.1](https://modrinth.com/mod/architectury-api/version/9.2.14+forge).
3. Colocar en la carpeta `.minecraft/mods/` (o carpeta `mods/` del servidor):
   - `vhill-forge-1.0.0.jar`
   - `architectury-9.2.14-forge.jar`

### En Fabric:
1. Instalar **Minecraft 1.20.1 con Fabric Loader** (ej. 0.15+ o 0.19+).
2. Descargar **Fabric API** y [Architectury API para Fabric 1.20.1](https://modrinth.com/mod/architectury-api/version/9.2.14+fabric).
3. Colocar en la carpeta `.minecraft/mods/`:
   - `vhill-fabric-1.0.0.jar`
   - `fabric-api-*.jar`
   - `architectury-*-fabric.jar`

---

## 📖 Documentación Adicional

- [Arquitectura del Proyecto](docs/ARCHITECTURE.md): Diseño técnico, abstracciones y ciclo de vida de registros.
- [Guía de Contribución](docs/CONTRIBUTING.md): Guía paso a paso para añadir sabores, ítems o recetas.
- [Contexto para Modelos de IA (AI_CONTEXT.md)](docs/AI_CONTEXT.md): Resumen estructurado para agentes y LLMs.

---

## 📄 Licencia

Este proyecto está distribuido bajo la licencia [MIT](LICENSE).
