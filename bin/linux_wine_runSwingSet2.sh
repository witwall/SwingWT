#!/bin/sh

# This script is for running the Win32 SwingSet under
# WINE on Linux/BSD. Saves me mucking about with vmware
wine ../../Program\ Files/Java/j2re1.4.2_07/bin/java.exe -cp "..\lib\demos.jar;..\lib\win32\swt.jar;..\lib\swingwt.jar;..\demo\swingset\swingres.jar" demo.swingset.SwingSet2
