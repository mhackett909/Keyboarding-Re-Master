#!/bin/bash
DIST=dist
#java -cp .:$DIST/.:$DIST/KeyboardingMaster.jar:libs/input/jinput.jar -Djava.library.path=libs/native -jar $DIST/KeyboardingMaster.jar
java -cp .:$DIST/.:$DIST/kbmaster.jar:libs/input/jinput.jar -Djava.library.path=libs/native -jar $DIST/kbmaster.jar
