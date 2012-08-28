#!/bin/sh

# Build project
mvn -Dmaven.test.skip=false -P run-its clean install
