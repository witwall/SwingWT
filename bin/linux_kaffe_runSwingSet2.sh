#!/bin/sh
export MOZILLA_FIVE_HOME=/usr/lib/firefox
export LD_LIBRARY_PATH=/usr/lib/firefox
kaffe -Xss128m -cp ../lib/demos.jar:../lib/linux_gtk2/swt.jar:../lib/swingwt.jar:../demo/swingset/swingres.jar demo.swingset.SwingSet2
