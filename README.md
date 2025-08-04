# BirdsExpansion
Minecraft Expansion for Birds - Plugin &amp; Data Pack

See the [Showcase Blog](https://daffodil-bead-1f1.notion.site/Birds-Expansion-2409053ce4c28067a3c3ee66e076fa7a?pvs=74) for more info

# 📋 Project Status Update

**Project:** Minecraft Birds + Nests & Houses Expansion  
**Status Date:** *22/07/25*  
**Version:** 1.0.0-dev

---

## Current Progress Summary

- ✅ **Core Plugin Framework**
    - Project scaffolded with Kotlin, targeting **Minecraft 1.21.6+**
    - Plugin architecture designed for **high modularity and flexibility**

- ⚠️ **Nest System**
    - **NestVariant system** implemented — defines variants (e.g. chicken nests, parrot nests) supporting multiple `NestType` contexts (GROUND, TREE, CLIFF, WATER)
    - Each **NestVariant** includes per-type data such as:
        - Base block material
        - Visual handler for rendering
        - Item drops
        - Behavior lifecycle hooks

- ⚠️ **Visual System**
    - `NestVisualHandler` established to manage:
        - Visual placement of nests via `BlockDisplay` entities
        - Cleanup and removal
        - Dynamic updates based on **visual states** (e.g. eggs, feathers, damaged)
    - Visual states structured via a **data class** to support **composable visual features**

- ⚠️ **Nest Lifecycle Behavior**
    - `NestBehavior` defines passive ticking, player interaction responses, and decay logic
    - Separated from **bird species behaviors** for clean responsibility

- ⚠️ **Species System**
    - Wrappers for **vanilla Minecraft birds (Chickens, Parrots)** being implemented under `species.vanilla`
    - `SpeciesNestBehavior` interface defined for custom species-specific interactions with nests

- ⚠️ **Spawning and Patching**
    - **SpawnerTask** periodically attempts nest spawning based on environment
    - **NestPatcher** allows for **retroactive world population**, respecting loaded-only chunks to avoid unintended generation
    - ❌ **On World Generation** not yet worked on

- ⚠️ **Commands**
    - Admin commands scaffolded for nest inspection, patching, and debug utilities

- ❌ **Persistence**
    - Nest data persists via **Chunk PDC (PersistentDataContainer)**
    - Nests automatically load/unload with chunks

---

## Project Structure Overview

- **nest/**: Core nesting system — behavior, data, spawning, visual rendering, interaction
- **species/**: Vanilla and custom species definitions + interaction behaviors
- **listener/**: Bukkit listeners (chunk load/unload, player interactions)
- **command/**: Commands for admin/dev tasks
- **util/**: Reusable utility classes for data, tasks, world manipulation

---

## Next Steps

1. **Develop Additional Visual Handlers**
    - Implement support for **ItemsAdder** custom block visuals

2. **Enhance Species Behaviors**
    - Introduce more nuanced species interactions (e.g. egg-laying, nest defending)

3. **Expand Nest Behaviors**
    - Add tick-based lifecycle evolutions (e.g. egg incubation, wear and tear)

4. **Configuration System**
    - Load **NestVariants, BirdSpecies, and visual configurations from YAML/JSON**

5. **Custom Events**
    -  Birds-specific events like:
        - `NestOccupiedEvent`
        - `NestDecayedEvent`

6. **In-game GUIs**
    - Provide players/admins with GUI menus to inspect or manage nests

---

## Cammy Notes

- The system is highly modular - any new nests, visuals, or species can be registered via their respective registries - soon through configs alone for most
- Ensure that **NestVisualHandlers are paired correctly with NestVariants** for predictable rendering
- Plugin currently depends on Paper 1.21.6+, Kotlin 2.1.21, and Cloud for command framework

---

## ❌ Current Blockers

- No player-facing GUI yet for nest discovery/interaction beyond basic events
- Species behaviors are basic — no AI tasks implemented yet for birds
- No custom models for nests, houses, or birds

---
