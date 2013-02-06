# GCJ Makefile for SwingWT. Written by R.Rawson-Tetley 
# 23/04/2004
#
# Compiles SWT, SwingWT and a demo to native code using
# GCJ. Feel free to use this as a template Makefile for 
# your own GCJ projects.
#
# Linux/GTK2: Works fine for just about all distros 
# 	      (make sure you have the "unzip" command)
# 
# Win32:      You'll need cygwin and the GNU find tool.
#             Also need the command line *nix "unzip" utility.
#             
# MacOS X:    Just need GCJ 3.3+
#
# targets:
# 	all: Builds everything
# 	swt: Builds libswt for dynamic linking
# 	swing: Builds libswingwt for dynamic linking
# 	demo: Builds the SwingWT demo into an executable
# 	clean: Removes built files
# 	install: Installs the demo/libs
# 	uninstall: Uninstalls the demo/libs

SWTBUILD = 3232
SWTVER = "3.2"
GCJ = gcj

SWINGWT_BASE = swingwt
#SWINGWT_BASE = java

ifdef WIN32

PLATFORM = Win32
SWINGWT = .
SWTDIR = $(SWINGWT)/lib/win32
SWT_LIBS = $(SWTDIR)/swt-awt-win32-$(SWTBUILD).dll $(SWTDIR)/swt-win32-$(SWTBUILD).dll
SWT_PLATFORM_JARS = 
LIB_SWT = swt.dll
LIB_SWINGWT = swingwt.dll
LIB_DIR = $(SWINGWT)/lib
DEMO = swingwtdemo.exe
SWT_SRC = $(SWINGWT)/lib/win32/swt-source.zip

endif

ifdef DARWIN

PLATFORM = Darwin/Carbon
SWINGWT = .
SWTDIR = $(SWINGWT)/lib/macosx_carbon
SWT_LIBS = $(SWTDIR)/libswt-carbon-$(SWTBUILD).jnilib $(SWTDIR)/libswt-pi-carbon-$(SWTBUILD).jnilib $(SWTDIR)/libswt-webkit-carbon-$(SWTBUILD).jnilib
SWT_PLATFORM_JARS = -I$(SWTDIR)/swt-pi.jar
LIB_SWT = libswt.so
LIB_SWINGWT = libswingwt.so
LIB_DIR = $(SWINGWT)/lib
DEMO = swingwtdemo
SWT_SRC = $(SWINGWT)/lib/macosx_carbon/swt-source.zip

else

PLATFORM=Linux/GTK2
SWINGWT = `pwd`
SWTDIR = $(SWINGWT)/lib/linux_gtk2
SWT_LIBS = $(SWTDIR)/libswt-atk-gtk-$(SWTBUILD).so $(SWTDIR)/libswt-gnome-gtk-$(SWTBUILD).so $(SWTDIR)/libswt-gtk-$(SWTBUILD).so $(SWTDIR)/libswt-mozilla-gtk-$(SWTBUILD).so $(SWTDIR)/libswt-pi-gtk-$(SWTBUILD).so
SWT_PLATFORM_JARS = -I$(SWTDIR)/swt-pi.jar -I$(SWTDIR)/swt-mozilla.jar
LIB_SWT = libswt.so
LIB_DIR = $(SWINGWT)/lib
LIB_SWINGWT = libswingwt.so
DEMO = swingwtdemo
SWT_SRC = $(SWINGWT)/lib/linux_gtk2/swt-source.zip
XPCOM = /usr/lib/firefox/libxpcom.so
XPCOM_CORE = /usr/lib/firefox/libxpcom_core.so

endif

all:	demo

swt:
	@echo ""
	@echo "[$(LIB_SWT) $(PLATFORM)]"
	@export LD_LIBRARY_PATH=$(SWTDIR)
	@echo "  unzip $(SWT_SRC)"
	@unzip $(SWT_SRC) > /dev/null
	@find org -name '*.java' > classlist.txt
	@echo "  gcj $(LIB_SWT)"
	@$(GCJ) -fjni -shared -o $(LIB_DIR)/$(LIB_SWT) $(SWT_LIBS) `cat classlist.txt`
	@rm classlist.txt org -rf

swing:	swt
	@echo "[$(LIB_SWINGWT)]"
	@# move non-native compilable stuff out of the way
	@mv src/$(SWINGWT_BASE)/classloader classloader
	@mv src/$(SWINGWT_BASE)x/custom/Graphics2DRenderer.java Graphics2DRenderer.java
	@mv src/$(SWINGWT_BASE)x/custom/JSWTTable.java JSWTTable.java
	@# ===
	@find src/$(SWINGWT_BASE) -name '*.java' > classlist.txt
	@find src/$(SWINGWT_BASE)x -name '*.java' >> classlist.txt
	@# move non-native stuff back again
	@mv classloader src/$(SWINGWT_BASE)/classloader
	@mv Graphics2DRenderer.java src/$(SWINGWT_BASE)x/custom/Graphics2DRenderer.java
	@mv JSWTTable.java src/$(SWINGWT_BASE)x/custom/JSWTTable.java
	@# ===
	@echo "  gcj $(LIB_SWINGWT)"
	@$(GCJ) --CLASSPATH=src -I$(SWTDIR)/swt.jar -shared -o $(LIB_DIR)/$(LIB_SWINGWT) $(LIB_DIR)/$(LIB_SWT) `cat classlist.txt` 
	@rm classlist.txt

demo:	swing
	@echo "[$(DEMO)]"
	@echo "  gcj $(DEMO)"
	@$(GCJ) -I$(SWTDIR)/swt.jar $(SWT_PLATFORM_JARS) -I$(LIB_DIR)/swingwt.jar -c $(SWINGWT)/src/demo/Everything.java -o $(SWINGWT)/src/demo.o
	@$(GCJ) --resource $(SWINGWT)/src/demo/Homepage.png $(SWINGWT)/src/demo/pic.gif -o $(SWINGWT)/src/res.o -c 
	@$(GCJ) --main=demo.Everything -o $(SWINGWT)/$(DEMO) $(SWINGWT)/src/res.o $(SWINGWT)/src/demo.o $(LIB_DIR)/$(LIB_SWT) $(LIB_DIR)/$(LIB_SWINGWT) $(XPCOM) $(XPCOM_CORE)
	@rm $(SWINGWT)/src/demo.o $(SWINGWT)/src/res.o -f
	@echo ""
	@echo "Now run 'make install' as root to install the demo."

install:
	@echo "[install]"
	@echo "  cp $(LIB_SWINGWT) $(LIB_SWT) /usr/lib"
	@cp lib/$(LIB_SWINGWT) lib/$(LIB_SWT) /usr/lib
	@echo "  cp libswt-gtk-$(SWTBUILD).so /usr/lib"
	@cp $(SWTDIR)/libswt*$(SWTBUILD)*.so /usr/lib
	@echo "  cp $(DEMO) /usr/bin"
	@cp $(DEMO) /usr/bin
	@echo ""
	@echo "Now run '$(DEMO)' to see the demo. You can"
	@echo "also uninstall with 'make uninstall' (as root)"

uninstall:
	@echo "[uninstall]"
	@echo "  rm /usr/lib/$(LIB_SWT) /usr/lib/$(LIB_SWINGWT)"
	@rm /usr/lib/$(LIB_SWT) -f
	@rm /usr/lib/$(LIB_SWINGWT).so -f
	@echo "  rm /usr/lib/libswt-gtk-$(SWTBUILD).so"
	@rm /usr/lib/libswt*$(SWTBUILD)*.so -f
	@echo "  rm /usr/bin/$(DEMO)"
	@rm /usr/bin/$(DEMO) -f

clean:
	@echo "[clean]"
	@echo "   rm $(DEMO), $(LIB_SWT) and $(LIB_SWINGWT)"
	-@rm $(SWINGWT)/$(DEMO)
	-@rm $(LIB_DIR)/$(LIB_SWINGWT)
	-@rm $(LIB_DIR)/$(LIB_SWT)
	-@rm $(SWINGWT)/org -rf
