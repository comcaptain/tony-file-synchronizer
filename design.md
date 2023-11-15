# File Transfer Application Specifications

## General Requirements

- Command-line based tool with GUI elements.
- Supports ad-hoc and batch file transfers.
- Auto-file-sync between different machines.
- Proxy mode for file transfers.
- Developed in Java 11 with Java sockets.

## Server Requirements

- Hosts a socket server for handling file transfer requests.

## Client Requirements

- Contains a socket client for establishing connections.
- Modern UI compatible with Mac and Windows.

### UI Elements

- **Profiles Tab**: Lists saved profiles; each with a sync option.
- **Remotes Tab**: Manages remote host configurations.
- **Hosts Tab**: Groups transfer targets by host.
- **Paths Tab**: Manages file paths for sync/transfer operations.

### Main Control Area

- Input fields with autocomplete for profile, remote name, and paths.
- Toggleable star icon for bookmarking host-path combinations.
- Display for creation time and last update time.

### Console Output Area

- Shows logs and progress of file transfer operations.

## Performance Requirements

- Initially considered multi-threading, later deprioritized for a single-threaded approach.

## File Transfer Requirements

- Bi-directional file transfer capability.
- Efficient with large files and multiple small files.
