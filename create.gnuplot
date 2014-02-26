#Just redirect stderr from the java program to a file call foo.dat and call gnuplot on it
set term png
set output 'create.png'
set   autoscale                        # scale axes automatically
set log                              # remove any log-scaling
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Protostuff vs Protobuf latency for object creation and write"
set xlabel "ByteSize"
set ylabel "Latency"
plot    "foo.dat" using 1:2 title 'protostuffCreateTime' with linespoints , \
	"foo.dat" using 1:5 title 'protobufCreateTime' with linespoints