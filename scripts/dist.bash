#!/bin/bash
echo "Prepares the distribution"


# The directory used by netbeans to create the jar
DIST_DIR=dist
INSTALLER_DIR=installer
DEST_DIR=$INSTALLER_DIR/kbmaster
BUILDER_ROOT='/home/spethm/Software/IDE/installbuilder/latest'
BUILDER="$BUILDER_ROOT/bin/builder"
LICENSE='/home/spethm/Software/IDE/installbuilder/kbm.license.xml'
JRE=jre1.7.0_25

# setup the license
if [ -e $BUILDER_ROOT/license.xml ]; then
    rm $BUILDER_ROOT/license.xml
fi
ln -s $LICENSE "$BUILDER_ROOT/license.xml"

if [ -e scripts/GetVersion.class ]; then
    rm scripts/GetVersion.class
fi

mkdir $INSTALLER
rm -rf $DEST_DIR
#rm dist/kbmaster.tar.bz2

mkdir $DEST_DIR
cp $DIST_DIR/KeyboardingMaster.jar ${DEST_DIR}/.
cp -r $DIST_DIR/lib $DEST_DIR/.
cp -r libs/native $DEST_DIR/lib/.
cp -r libs/jre/${JRE}-linux-x64/java-linux-x64 $DEST_DIR/lib/.
cp -r libs/jre/${JRE}-linux/java-linux $DEST_DIR/lib/.
cp scripts/run.bash $DEST_DIR/.
cp scripts/convert_db.bash $DEST_DIR/.
cp COPYING $DEST_DIR/.
cp docs/readme.txt $DEST_DIR/.
cp art_assets/banner\ and\ logo/logo_48x48.png $DEST_DIR/desktop_icon.png

# get the version
javac -cp ${DIST_DIR}/KeyboardingMaster.jar scripts/GetVersion.java 
VERSION=$(java -cp ${DIST_DIR}/KeyboardingMaster.jar:scripts GetVersion)
echo "Building Keyboarding Master $VERSION"

# Building Linux 32-bit
$BUILDER build ./scripts/installbuilder.xml linux --setvars project.version=$VERSION

# Building Linux 64-bit
$BUILDER build ./scripts/installbuilder.xml linux-x64 --setvars project.version=$VERSION

# todo, check if dist exists
#if [ -e dist ]; then
#    rm -rf dist
#fi
#mkdir dist

# move the install files
mv $BUILDER_ROOT/output/kbm* $INSTALLER_DIR/.
