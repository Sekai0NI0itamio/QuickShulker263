# Quick Shulker — Port for Minecraft 1.20.1 (Forge)

![Mod loader: Forge](https://img.shields.io/badge/modloader-Forge-orange?style=for-the-badge)
![Minecraft: 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/QuickShulker263?style=for-the-badge)

**Quickly open a held shulker box with the press of a key!**

Hold a shulker box (or ender chest, crafting table, or stonecutter), press **K**, and it opens right in your hand — contents saved back to the item (NBT `BlockEntityTag`) as you edit them.

### What it does

- Press **K** (rebindable) while holding a shulker box, ender chest, crafting table, or stonecutter to open it in place
- Works on servers too — opening is handled server-side via a Forge SimpleChannel packet (same ResourceLocation `quickshulker:open` as the Fabric port's payload channel)
- KeyMapping registered via `RegisterKeyMappingsEvent`; client tick sends the packet

### Forge port notes (1.20.1)

- Ported from the Fabric/1.20.1 branch; NBT `BlockEntityTag` storage version (no DataComponents in 1.20.1)
- Fabric `ClientPlayNetworking`/`ServerPlayNetworking` replaced with Forge `SimpleChannel` (`NetworkRegistry.newSimpleChannel`); no client-only fallback needed
- Java 17 · Forge 47.4.20 · ForgeGradle [6.0,6.2) with Mixin (empty config)
- Built and verified in CI via GitHub Actions (build + runtime test)

### Installation

1. Install Forge 47.4.20 for Minecraft 1.20.1
2. Drop `quickshulker-1.20.1-forge-*.jar` into your `mods` folder

### Original mod & credit

Unofficial port by **Itamio** of **QuickShulker** by **kyrptonaught** (MIT).

- Original mod: https://modrinth.com/mod/quickshulker
- Original source: https://github.com/kyrptonaught/quickshulker
- License: MIT — see `LICENSE`

All credit for the original concept, design, and logic goes to kyrptonaught. See `NOTICE`.
