#!/bin/bash

echo ">배포 시작"
REPOSITORY=/home/ubuntu/rare
cd $REPOSITORY/build/libs/

nohup java -jar -Dspring.profiles.active=prod *.jar > /dev/null 2> /dev/null < /dev/null &
echo "> 배포 완료"
