# Director Mode — feature specification

Director Mode is a planned feature. It is **not implemented in preview 0.1.0**.

## Player experience

1. Open a simple Director screen or use a keybind.
2. Select a named LivingTown NPC.
3. Choose **Possess** to directly pilot that NPC.
4. Move and act as the NPC using normal movement controls.
5. Press **Record** to capture a performance; press **Stop** to save it.
6. Select a saved performance and press **Play** to replay it.
7. Optionally assign a performance to a schedule entry or a hotkey.

## Recording model

A performance should be a timestamped sequence of safe, high-level actions, not a raw stream of every client input:

- Position and rotation samples for walking/running/jumping.
- Look direction and facing.
- Supported pose changes such as sit/stand.
- Approved interaction events such as opening a door or using a configured prop.
- Optional pauses and animation cues.

Playback should use server-side NPC navigation and validated interactions. It should not teleport through walls or replay arbitrary player packets. If a route becomes blocked, playback should pause and report that it needs a re-recording or a waypoint adjustment.

## Controls (proposed defaults)

- `J`: open Director Mode
- `Possess`: take control of selected NPC
- `F6`: start/stop recording
- `F7`: play selected performance
- `Esc`: release control / close screen

All keybinds must be configurable, and the UI must show which NPC is currently controlled or recording.

## Data and safety

- Store performances in the world's `livingtown/performances/` data folder.
- Use versioned JSON with validation and sensible size limits.
- Save atomically; never overwrite an existing recording without confirmation.
- Only one NPC is possessed at a time in the first version.
- Playback should be deterministic enough for machinima, but adapt to minor pathing differences.

## Implementation phases

1. Possess/release one NPC with keyboard movement.
2. Record and replay movement and rotation.
3. Add supported poses and prop interactions.
4. Add a Director screen, configurable keybinds and recording library.
5. Allow multiple NPCs to play separately recorded performances on cue.

## Acceptance tests

- A player can possess and release an NPC without losing the NPC's name or saved data.
- A 30-second movement take can be recorded, saved, reloaded and replayed.
- Playback respects collisions and does not force the NPC through blocks.
- Two different NPCs can each retain and replay their own performance.
- Existing schedules can resume after the NPC is released from Director Mode.
