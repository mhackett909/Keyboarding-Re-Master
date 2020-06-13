#!/bin/bash
if [ $(uname -m) == 'x86_64' ]; then
  # 64-bit stuff here
    JAVA=lib/java-linux-x64/bin/java
else
  # 32-bit stuff here
    JAVA=lib/java-linux/bin/java
fi
$JAVA -cp . -Djava.library.path=lib/native -jar KeyboardingMaster.jar
