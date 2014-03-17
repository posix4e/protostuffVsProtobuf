#!/bin/sh
set -ex
mvn install

cd benchmark 
mvn assembly:assembly 
java -Xmx2G -cp target/benchmark-1.1-SNAPSHOT-jar-with-dependencies.jar test.ProtostuffVsProtobuf> out
cp out ../foo.dat
cd ..
for i in create.gnuplot  createRead.gnuplot  createReadWrite.gnuplot
	do gnuplot $i || /bin/true
done
