#!/usr/bin/env bash
set -e
cd spring-jetty
./gradlew build
docker build -t spring-jetty:latest .
docker tag spring-jetty:latest michaelbannister/spring-jetty:latest
