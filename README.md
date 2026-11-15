# LivingTown

A Fabric mod for Minecraft Java 1.21.11 focused on a lively, player-like NPC town for single-player worlds.

> Early development: this repository is the source project, not a finished release. The first milestone is a reliable NPC foundation; school timetables, social behaviour and richer routines will be added and tested in later milestones.

## Goals

- Player-proportioned humanoid NPCs with names and roles.
- Simple commands instead of complicated setup.
- Configurable daily schedules and destinations.
- NPCs navigate between homes, school and workplaces.
- NPC state is saved with the world.
- Lightweight simulation: no mining, crafting or building AI is required.

## Requirements

- Minecraft Java **1.21.11**
- Fabric Loader **0.18.1 or newer**
- Fabric API **0.141.1+1.21.11**
- Java **21**

## Build

1. Install JDK 21.
2. Clone this repository.
3. Open the folder in IntelliJ IDEA (or another Gradle IDE).
4. Use Gradle 9.2.1 and run `gradle build` (the GitHub Actions build does this automatically).
5. Find the mod JAR in `build/libs/` (the `-sources` JAR is not the one to install).
6. Put the regular JAR in `.minecraft/mods/` alongside Fabric API.

The GitHub Actions workflow builds the project on every push and pull request. Download the `LivingTown-JAR` artifact from a successful workflow run.

## Design promise

LivingTown is intended for single-player, but the NPC simulation runs on the integrated server so NPCs behave consistently and their data is saved by Minecraft. It does not require a separate server.

## Development status

See [ROADMAP.md](ROADMAP.md) for the current milestone and planned features. Do not treat planned features as implemented until they are listed under **Working now**.

## Safety for worlds

Back up important worlds before testing development builds. Early builds may change saved NPC data as the format evolves.

## License

MIT. See [LICENSE](LICENSE).
