#!/bin/bash
#maye need to run with sudo
java -cp .:build/libs/keyboarding-re-master.jar:libs/input/jinput.jar -Djava.library.path=libs/native com.monkygames.kbmaster.util.ScanHardware &> HardwareScan.log


