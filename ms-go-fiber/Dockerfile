# Stage 1: Build stage
FROM golang:bookworm AS builder

# Set the working directory
WORKDIR /app

# Copy and download dependencies
COPY go.mod go.sum ./
RUN go mod download

# Copy the source code
COPY . .

# Build the Go application
RUN go build -o myapp .

# Fase 2: Creazione dell'immagine finale
FROM debian:bookworm-slim AS release

# Imposta la cartella di lavoro
WORKDIR /app

# Copy the binary from the build stage
COPY --from=builder /app/myapp .

# Set the entrypoint command
ENTRYPOINT ["/app/myapp"]
