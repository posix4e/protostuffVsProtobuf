#Just redirect stderr from the java program to a file call foo.dat and call gnuplot on it
set term png
set output 'write.png'
set   autoscale                        # scale axes automatically
set log                              # remove any log-scaling
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Latency for serializing to an outputstream"
set xlabel "ByteSize"
set ylabel "Latency (ns)"
plot "foo.dat" using 1:4 title 'protostuff' with linespoints , \
	"foo.dat" using 1:6 title 'protobuf' with linespoints

