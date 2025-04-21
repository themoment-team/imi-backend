#!/bin/bash

IMAGE_NAME=imi-img
CONTAINER_NAME=imi-container
DOCKERFILE_NAME=Dockerfile

CURRENT_CONTAINER_ID=$(docker ps -a -q -f name=$CONTAINER_NAME)

if [ ! -z "$CURRENT_CONTAINER_ID" ]
then
  echo "현재 구동 중인 컨테이너가 있습니다. 중지하고 삭제합니다."
  docker stop $CONTAINER_NAME || true
  docker rm $CONTAINER_NAME
fi

cd /home/ubuntu
docker build -t $IMAGE_NAME -f $DOCKERFILE_NAME .

docker run -d --name $CONTAINER_NAME -p 8080:8080 --restart always $IMAGE_NAME