#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/rare
cd $REPOSITORY

APP_NAME=rare
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar -Dspring.profiles.active=prod $JAR_PATH & 1> /home/ubuntu/nohup.out
