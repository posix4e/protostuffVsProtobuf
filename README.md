protostuffVsProtobuf
====================

We wanted to show just how important 0 copy protobufs are. We created a test
which creates a bunch of protobufs using our code and using google's code
of different sizes. The results on how much timing it took are striking.

[gnuplot of the create timing](create.png)
[gnuplot of the create and write timing](createwrite.png)
[gnuplot of the create and write and read timing](createwriteread.png)

