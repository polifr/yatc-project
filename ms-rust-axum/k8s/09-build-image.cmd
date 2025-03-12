@FOR /f "tokens=*" %%i IN ('minikube -p minikube docker-env --shell cmd') DO @%%i

docker build -t yatc/ms-rust-axum -f ../Dockerfile.multi ..
