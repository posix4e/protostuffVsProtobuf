#Just redirect stderr from the java program to a file call foo.dat and call gnuplot on it
set term png 
set output 'read.png'
set   autoscale                        # scale axes automatically
set log                              # remove any log-scaling
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Latency for deserializing from an input"
set xlabel "ByteSize"
set ylabel "Latency (ns)"
plot    "foo.dat" using 1:5 title 'protostuff' with linespoints , \
	"foo.dat" using 1:7 title 'protobuf' with linespoints
