#!/bin/sh
export MOZILLA_FIVE_HOME=/usr/lib/firefox
export LD_LIBRARY_PATH=/usr/lib/firefox
gij -cp ../lib/demos.jar:../lib/linux_gtk2/swt.jar:../lib/swingwt.jar demo.SwingWTBrowser $@
