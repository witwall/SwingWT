#!/bin/sh

# Search replace code for occurrences of javax.swing and java.awt
# replacing them with swingwtx.swing/swingwt.awt

for F in `grep -rl 'javax.swing."' .`; do
	cat $F | sed 's/javax\.swing\./swingwtx\.swing\./g' > $F.tmp
	mv $F.tmp $F;
done
for F in `grep -rl 'java.awt."' .`; do
	cat $F | sed 's/java\.awt\./swingwt\.awt\./g' > $F.tmp
	mv $F.tmp $F;
done

