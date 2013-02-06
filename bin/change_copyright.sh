#!/bin/sh

# Use to change copyright notice on source files

cd ../src
for F in `grep -rl '2003-2007' \`find . -name '*.java'\``; do
	echo $F
	cat $F | sed 's/2003-2007/2003-2008/g' > $F.tmp
	mv $F.tmp $F;
done

