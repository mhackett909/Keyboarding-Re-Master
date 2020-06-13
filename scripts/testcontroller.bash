#!/bin/bash
java -cp .:libs/input/jinput.jar:libs/input/jinput-test.jar -Djava.library.path=libs/native net.java.games.input.test.ControllerReadTest
