#!/bin/bash

DIST="/home/spethm/Software/IDE/NetBeansProjects/kbm/dist"
java -cp .:$DIST/KeyboardingMaster.jar:libs/db/db4o-8.0.236.16058-core-java5.jar:libs/db/xstream-1.4.7.jar -Djava.library.path=lib/native com.monkygames.kbmaster.util.ConvertToXML $@

