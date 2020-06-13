#!/bin/bash
java -cp .:build/libs/keyboarding-master.jar:libs/input/jinput.jar -Djava.library.path=libs/native com.monkygames.kbmaster.util.ScanHardware "$1" >& map.txt

