# QuickShulker263

Quickly open shulker boxes (and more) without placing them down — ported to Minecraft 26.3 (Fabric).

Unofficial port of [QuickShulker](https://github.com/kyrptonaught/quickshulker) by
[kyrptonaught](https://modrinth.com/mod/quickshulker) (MIT). All credit for the
original concept, design, and logic goes to kyrptonaught. See [NOTICE](NOTICE).

## Features

- Press **K** (rebindable) while holding a shulker box, ender chest, crafting
  table, or stonecutter to open it in place.
- **Right-click** a held shulker box / ender chest / crafting table / stonecutter
  to open it instead of placing it.
- Shulker contents are stored in the item's container data component and are
  saved back as you edit them.

## Notes

- Original source was found and studied at
  https://github.com/kyrptonaught/quickshulker (version for 1.19.4 at the time
  of cloning). This port is a clean reimplementation for 26.3 APIs and drops
  the original's kyrptconfig/shulkerutils dependencies. Creative-mode
  bundling helpers from the original are not included.

## Requirements

- Minecraft 26.3
- Fabric Loader ≥ 0.20.1
- Fabric API
- Java 25

## Building

```sh
./gradlew build
```

CI builds via GitHub Actions (`.github/workflows/build.yml`).
