#!/bin/bash

java -cp .:KeyboardingMaster.jar:lib/db4o-8.0.236.16058-core-java5.jar:lib/xstream-1.4.7.jar -Djava.library.path=lib/native com.monkygames.kbmaster.util.ConvertToXML $@
