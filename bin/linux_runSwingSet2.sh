#!/bin/sh
export LD_LIBRARY_PATH=/usr/lib/firefox
export MOZILLA_FIVE_HOME=/usr/lib/firefox
$JAVA_HOME/bin/java -cp ../lib/demos.jar:../lib/linux_gtk2/swt.jar:../lib/swingwt.jar:../src/demo/swingset/swingres.jar demo.swingset.SwingSet2
