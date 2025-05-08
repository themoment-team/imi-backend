#!/bin/bash
set -e

COMPOSE_FILE=compose.yaml

echo "1) 기존 컨테이너 중지 및 삭제"
docker compose -f $COMPOSE_FILE down --rmi all --volumes --remove-orphans

echo "2) 이미지 빌드 및 컨테이너 실행"
docker compose -f $COMPOSE_FILE up -d --build

echo "3) deploy script end"
