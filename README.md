# Quick Shulker — Modern Port for Minecraft 1.20.1 (Fabric)

![Mod loader: Fabric](https://img.shields.io/badge/modloader-Fabric-1976d2?style=for-the-badge)
![Minecraft: 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/QuickShulker263?style=for-the-badge)

**Quickly open a held shulker box with the press of a key!**

No more placing a shulker box just to peek inside it. Hold a shulker box
(or ender chest, crafting table, or stonecutter), press **K**, and it
opens right in your hand — contents saved back to the item as you edit
them. Perfect for quick inventory checks mid-adventure.

Now with a **modern port for Minecraft 1.20.1 (Fabric)**, rebuilt for
1.20.1's data-component storage (`DataComponents.CONTAINER`), keymapping
(`KeyMappingHelper`), and serverbound payload APIs.

### What it does

- Press **K** (rebindable in Controls) while holding a shulker box,
  ender chest, crafting table, or stonecutter to open it in place
- Shulker contents live in the item's container data component and are
  saved back automatically as you edit them
- Works on servers too — opening is handled server-side via a custom packet

### Modern port notes (1.20.1)

- Clean reimplementation for 1.20.1 APIs; drops the original's
  kyrptconfig/shulkerutils dependencies
- Right-click-to-open from the original was not ported (`UseItemCallback`
  changed substantially in 1.20.1) — the K keybind is the supported way to open
- Java 17 · Fabric Loader 0.16.14+ · Fabric API 0.92.11+1.20.1
- Built and verified in CI via GitHub Actions

### Installation

1. Install Fabric Loader for Minecraft 1.20.1
2. Install Fabric API
3. Drop `quickshulker263-*.jar` into your `mods` folder

### Original mod & credit

This is an unofficial modern port by **Itamio** of **QuickShulker** by
**kyrptonaught** — the original (680k+ downloads on Modrinth, last
updated for 1.20.1) has not been updated to 1.20.1.

- Original mod: https://modrinth.com/mod/quickshulker
- Original source: https://github.com/kyrptonaught/quickshulker
- License: MIT — see `LICENSE`

All credit for the original concept, design, and logic goes to
kyrptonaught. This port exists to bring it to Minecraft 1.20.1. See `NOTICE`.
