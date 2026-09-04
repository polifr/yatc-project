update:
	cargo update

clean:
	cargo clean

build:
	cargo build

release:
	cargo build --release
	strip target/release/ms-rust-axum

build-image:
	docker build --tag yatc/ms-rust-axum --label yatc/ms-rust-axum -f Dockerfile.multi .
	docker image prune --force --filter='label=yatc/ms-rust-axum'
