# Fase 1: Compilazione
FROM rust:bookworm AS builder

# Imposta la cartella di lavoro
WORKDIR /app

# Copia il file Cargo e scarica le dipendenze
COPY Cargo.toml Cargo.lock ./
RUN mkdir src && echo "fn main() {}" > src/main.rs
RUN cargo build --release && rm -rf src

# Copia i sorgenti effettivi e compila
COPY . .
RUN cargo build --release

RUN strip target/release/ms-rust-axum

# Fase 2: Creazione dell'immagine finale
FROM debian:bookworm-slim AS release

# Imposta la cartella di lavoro
WORKDIR /app

# Copia solo il binario compilato dalla fase precedente
COPY --from=builder /app/target/release/ms-rust-axum /app/

# Espone la porta 8080
EXPOSE 8080

# Comando di esecuzione
CMD ["/app/ms-rust-axum"]
