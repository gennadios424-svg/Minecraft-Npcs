# LivingTown

A Fabric mod for Minecraft Java **1.21.11** focused on a lively, player-like NPC town for single-player worlds.

> **Early preview.** This repository now contains the first NPC foundation. It is not yet the full town simulator: daily schedules, school/work activities, custom skins, relationships and memories are still planned.

## What is implemented in this preview

- A persistent humanoid NPC entity with a visible name.
- A player-proportioned biped model using the default Steve texture.
- Basic autonomous wandering and looking at nearby players.
- Commands to spawn, list, remove and send nearby NPCs to a destination.
- A GitHub Actions workflow that builds the mod and uploads a JAR artifact.

## In-game commands

Enable cheats or use a world where you have permission to run commands:

| Command | What it does |
|---|---|
| `/livingtown help` | Show command help |
| `/livingtown spawn Alex` | Spawn an NPC named Alex at your position |
| `/livingtown list` | List LivingTown NPCs within 128 blocks |
| `/livingtown goto Alex 120 64 -30` | Send Alex to the coordinates |
| `/livingtown remove Alex` | Remove a nearby NPC named Alex |

NPC names are used to identify characters, so give each nearby NPC a unique name. The first preview uses a default Steve-style skin; per-NPC skin selection is not implemented yet.

## Requirements

- Minecraft Java **1.21.11**
- Fabric Loader **0.18.1 or newer**
- Fabric API **0.141.1+1.21.11**
- Java **21**

## Easiest way to get the JAR

1. Open the repository's **Actions** tab.
2. Open the latest successful **Build LivingTown** run.
3. Under **Artifacts**, download **LivingTown-JAR**.
4. Put the regular `livingtown-*.jar` file (not a sources JAR) in your Minecraft `.minecraft/mods/` folder.
5. Make sure Fabric API is also installed.

The workflow runs automatically on pushes to `main` and `develop`, pull requests, and manual dispatch.

## Build from source

1. Install JDK 21 and Gradle 9.2.1.
2. Clone this repository.
3. Open the project in IntelliJ IDEA or another Gradle IDE.
4. Run `gradle build`.
5. Find the JAR in `build/libs/`.

## Development status

See [ROADMAP.md](ROADMAP.md). Features listed as planned are not implemented until they are moved into **What is implemented** above.

## World safety

Back up important worlds before testing preview builds. NPCs are saved as Minecraft entities, but early versions may change how custom NPC data is stored.

## License

MIT. See [LICENSE](LICENSE).
