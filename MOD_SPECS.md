# Especificación del Proyecto: Mod "Vhill" (Minecraft)

## 1. Stack Tecnológico

- **Juego:** Minecraft Java Edition 1.20.1
- **Mod Loader:** Fabric
- **Lenguaje:** Java 17 (o 21)
- **Dependencias:** Fabric API

## 2. Objetivo del Mod

Implementar "Vhills" de distintos sabores que otorgan efectos de poción breves (10 segundos) al jugador tras su uso.

- **Categorías y Desgaste:** Existen tres categorías (3k, 12k y 32k) que determinan la vida útil del objeto. El ítem pierde durabilidad (como una herramienta) cada vez que se aplica el efecto.
- **Mecánica de Agotamiento (Veneno):** Cuando la durabilidad del Vhill llega a cero (daño máximo), el ítem NO se rompe ni desaparece del inventario. Si el jugador intenta usar un Vhill vacío, este aplicará un efecto de Veneno (Poison) por 10 segundos en lugar de su efecto original.
- **Obtención:** Se obtienen exclusivamente mediante intercambios comerciales (trades) con el aldeano Clérigo.
- **Economía y Progresión:** El precio en esmeraldas y el nivel del aldeano requerido para desbloquear el tradeo escala según la categoría del Vhill (3k es el más barato y de nivel bajo, 32k es el más caro y de nivel alto).

## 3. Arquitectura del Código

El proyecto sigue principios de programación orientada a objetos para evitar código duplicado:

- **Ítem Base (`VhillItem.java`):** Hereda de `Item`.
  - Su constructor recibe un `StatusEffectInstance` (para el sabor/efecto) y se le configuran las `Item.Settings` con un `maxDamage` según su categoría (3k, 12k o 32k).
  - Sobrescribe `finishUsing` para manejar la lógica principal.
  - Utiliza `stack.setDamage(danoActual + 1)` de forma manual para evitar que el motor del juego destruya el ítem al llegar al límite. Evalúa si el daño actual es menor al máximo para dar el efecto base; si ya está al máximo, aplica `StatusEffects.POISON`.
- **Registro (`ModItems.java`):** Utiliza la clase `Registry` de Fabric para dar de alta las combinaciones de `VhillItem`. Ejemplos de nomenclatura:
  - `mimod:vhill_fresa_3k` (Efecto Regeneración, Durabilidad Baja)
  - `mimod:vhill_menta_12k` (Efecto Velocidad, Durabilidad Media)
  - `mimod:vhill_adrenalina_32k` (Efecto Fuerza, Durabilidad Alta)
- **Sistema de Trades (`ModVillagerTrades.java`):** Utiliza `TradeOfferHelper` de la Fabric API para inyectar los Vhills en las ofertas del aldeano Clérigo (`VillagerProfession.CLERIC`).
  - **Nivel 1-2 (Novato/Aprendiz):** Desbloquea Vhills 3k.
  - **Nivel 3-4 (Compañero/Experto):** Desbloquea Vhills 12k.
  - **Nivel 5 (Maestro):** Desbloquea Vhills 32k.

## 4. Reglas de Desarrollo para la IA

- NO utilices código obsoleto de Forge o de versiones anteriores a la 1.20.x.
- Utiliza siempre los mapeos de Yarn (nombres oficiales de métodos en Fabric).
- Configura la propiedad `maxDamage` en las `Item.Settings` al registrar el ítem. No uses `maxCount` superior a 1 en ítems con durabilidad.
- NO mezcles código de cliente (renderizado, texturas, tooltips visuales) en las clases de servidor (ítems base). Si necesitas Tooltips, asegúrate de usar `@Environment(EnvType.CLIENT)` o separarlo correctamente.
- Todo nuevo ítem debe agruparse bajo un `ItemGroup` personalizado para que aparezca organizado en el modo creativo.
