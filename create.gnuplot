#Just redirect stderr from the java program to a file call foo.dat and call gnuplot on it
set term png 
set output 'create.png'
set   autoscale                        # scale axes automatically
set log                              # remove any log-scaling
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Latency for object creation and the addition of one binary blob"
set xlabel "ByteSize"
set ylabel "Latency (nsProtobuf )"
plot    "foo.dat" using 1:2 title 'protostuff' with linespoints , \
	"foo.dat" using 1:3 title 'protobuf' with linespoints
