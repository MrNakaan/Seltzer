rem I have a folder called "bnd" at the same level as my Git repos that I store my bundles in.
rem This script copies my command-response bundle to a folder within that.
mkdir ..\bnd\SeleniumServer
copy ".\SeleniumServer\command-response\target\command-response-*.jar" "..\bnd\SeleniumServer"
