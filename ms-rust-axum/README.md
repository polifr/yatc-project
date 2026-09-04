# ms-rust-axum - A Rust + Axum based microservice

## Features

Rust microservice with Axum; multi-stage `Dockerfile.multi` based on Debian trixie.

## Makefile commands
- `make update` to update Cargo dependencies (ref. `cargo update`)
- `make clean` deletes `target` directory (ref. `cargo clean`)
- `make build` compiles and builds target artifacts (reg. `cargo build`)
- `make release` builds with `--release` option and applies strip to the artifact
- `make build-image` creates Docker image, release build mode
