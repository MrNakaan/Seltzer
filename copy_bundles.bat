rem I have a folder called "bnd" at the same level as my Git repos that I store my bundles in.
rem This script copies my seltzer-cr and seltzer-util bundle to a folder within that.
mkdir ..\bnd\Seltzer
copy ".\seltzer-parent\seltzer-cr\target\seltzer-cr-*.jar" "..\bnd\Seltzer"
copy ".\seltzer-parent\seltzer-util\target\seltzer-util-*.jar" "..\bnd\Seltzer"
