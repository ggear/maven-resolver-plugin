#!/bin/sh

###############################################################################
#
# Resolve Generic tar Dependencies
#
###############################################################################

BUNDLE="/some/path/to/a/tar.tar.gz"
BUNDLE_DIR="some/dir/inside/the/tar"
BUNDLE_VERSION="a.version"
BUNDLE_GROUP="com.a.group.prefix"

TMP_DIR_TEMPLATE="/tmp/`echo ${0%/} | awk -F/ '{print \$NF}'`.XXXXXXXXXXXXXX"
TMP_DIR=`mktemp -d $TMP_DIR_TEMPLATE`
SRC_DIR="$TMP_DIR/src"
MVN_DIR="$TMP_DIR/mvn"

mkdir -p "$MVN_DIR"
mkdir -p "$SRC_DIR"

tar xvzf "$BUNDLE" -C "$SRC_DIR"

ls "$SRC_DIR"
ls "$SRC_DIR/$BUNDLE_DIR"

mvn -e org.ggear.maven.plugin.resolver:org.ggear.maven.plugin.resolver.plugin:install \
	-Dmaven.resolver.targetDir="$MVN_DIR"  \
	-Dmaven.resolver.rootDir="$SRC_DIR/$BUNDLE_DIR"  \
	-Dmaven.resolver.versionRegExp="$BUNDLE_VERSION"  \
	-Dmaven.resolver.groupPrefix="$BUNDLE_GROUP"  \
	-Dmaven.resolver.groupMaskRegExp="(.*)"  \
	-Dmaven.resolver.excludeRegExp="/web|/samples|/examples|/docs|/tmp|/temp|/work"  \

rm -rf "$TMP_DIR"


