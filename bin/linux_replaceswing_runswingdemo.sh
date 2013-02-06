#!/bin/sh

# This call uses our custom classloader to run a simple swing
# demo, dynamically switching Swing calls for SwingWT on the fly.

./swingwtbootstrap ../lib/demos.jar demo.SimpleFrame

