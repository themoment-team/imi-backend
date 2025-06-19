#!/bin/bash
set -e

COMPOSE_FILE=compose.yaml

echo "1) Docker 권한 확인"
sudo chmod 666 /var/run/docker.sock

echo "2) 기존 컨테이너 중지 및 삭제"
sudo docker-compose -f $COMPOSE_FILE down --rmi local --volumes --remove-orphans || true

echo "3) 이미지 빌드 및 컨테이너 실행"
sudo docker-compose -f $COMPOSE_FILE up -d --build

echo "4) deploy script end"