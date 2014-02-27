#!/bin/sh
set -ex
mvn assembly:assembly
java -cp target/protostuffVsProtobuf-1.0-SNAPSHOT-jar-with-dependencies.jar test.ProtoStuffTimingWithOutput> out 
cp out foo.dat
for i in create.gnuplot  createRead.gnuplot  createReadWrite.gnuplot
	do gnuplot $i
done
