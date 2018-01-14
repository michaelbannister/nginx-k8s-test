#!/usr/bin/env bash
set -e
cd spring-jetty
./gradlew build
docker build -t spring-jetty:latest .
