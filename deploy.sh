#!/bin/bash

REPOSITORY=/home/ubuntu/rare
cd $REPOSITORY

APP_NAME=rare

JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)

echo "> JAR_NAME 확인 : $JAR_NAME"

JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

echo "> JAR_PATH 확인: $JAR_PATH"

CURRENT_PID=$(pgrep -f $APP_NAME)

echo "> 현재 구동중인 애플리케이션 PID 확인 : $CURRENT_PID"

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar -Dspring.profiles.active=prod $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
echo "> 배포 완료"
