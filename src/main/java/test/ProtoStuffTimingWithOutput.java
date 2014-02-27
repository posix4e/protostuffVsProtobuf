package test;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.google.protobuf.ByteString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class ProtoStuffTimingWithOutput {

  final LinkedBuffer linkedBuffer = LinkedBuffer.allocate(1024);

  public static void main(String args[]) throws IOException, ClassNotFoundException {
    int max_byte_size = 1024 * 1024 * 16;
    int repeated_runs = 10;

    if (args.length == 1) {
      max_byte_size = Integer.parseInt(args[0]);
    }

    ProtoStuffTimingWithOutput protoStuffTimingWithOutput = new ProtoStuffTimingWithOutput();

    int warm_up_runs = 100;
    for (int i = 1; i < warm_up_runs; i++) {
      protoStuffTimingWithOutput.comparisonTest(16, 10, false);
    }

    for (int i = 1; i < max_byte_size; i *= 2) {
      for (int j = 0; j < repeated_runs; j++) {
        double pert = (j * .1) + 1;
        int pertCapacity = Double.valueOf(Math.ceil(pert * i)).intValue();
        protoStuffTimingWithOutput.comparisonTest(pertCapacity, 10, true);
      }
    }
  }

  private void comparisonTest(final int capacity, final int runs, final boolean output)
      throws IOException, ClassNotFoundException {
    ByteBuffer buffer = ByteBuffer.allocate(capacity);
    if (output) {
      System.out.print(capacity + " \t");
    }
    benchmarkCreateTime(runs, buffer, output);
    benchmarkReadWriteTime(runs, capacity, buffer, output);
  }

  private void benchmarkReadWriteTime(final int runs, final int capacity, final ByteBuffer buffer, boolean output)
      throws IOException, ClassNotFoundException {

    ByteArrayOutputStream dataOutputStream;
    ObjectOutputStream objectOutputStream;

    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    objectOutputStream = new ObjectOutputStream(dataOutputStream);

    // Protostuff
    BigBlob1 blob1 = new BigBlob1(buffer);
    long begin = System.nanoTime();

    for (int i = 0; i != runs; i++) {
      testProtostuffWriteTime(blob1, objectOutputStream);
    }

    if (output) {
      System.out.print(System.nanoTime() - begin + "\t");
    }

    // Point output to input
    ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(dataOutputStream.toByteArray()));

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testProtostuffReadTime(objectInputStream, linkedBuffer);
    }
    if (output) {
      System.out.print(System.nanoTime() - begin + "\t");
    }

    // Protobuf
    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    objectOutputStream = new ObjectOutputStream(dataOutputStream);

    BigBlob.BigBlob2 blob2 = BigBlob.BigBlob2.newBuilder().setBlob(ByteString.copyFrom(buffer.array())).build();

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testGoogleProtobufWriteTime(blob2, objectOutputStream);
    }
    if (output) {
      System.out.print(System.nanoTime() - begin + "\t");
    }
    // Point output to input
    objectInputStream = new ObjectInputStream(new ByteArrayInputStream(dataOutputStream.toByteArray()));

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testProtobufReadTime(objectInputStream);
    }
    if (output) {
      System.out.println(System.nanoTime() - begin);
    }
  }

  private void benchmarkCreateTime(final int runs, final ByteBuffer buffer, final boolean output) throws IOException {
    long begin;

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testProtostuffCreateTime(buffer);
    }

    if (output) {
      System.out.print(System.nanoTime() - begin + "\t");
    }

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testGoogleProtobufCreateTime(buffer);
    }

    if (output) {
      System.out.print(System.nanoTime() - begin + "\t");
    }
  }

  private void testGoogleProtobufCreateTime(final ByteBuffer buffer) {
    BigBlob.BigBlob2.newBuilder().setBlob(com.google.protobuf.ByteString.copyFrom(buffer.array())).build();
  }

  private void testProtostuffCreateTime(final ByteBuffer buffer) {
    new BigBlob1(buffer);
  }

  private void testProtostuffWriteTime(final BigBlob1 blob1,
                                       final ObjectOutputStream objectOutputStream) throws IOException {
    objectOutputStream.writeObject(blob1);
  }

  private void testGoogleProtobufWriteTime(final BigBlob.BigBlob2 blob2,
                                           final ObjectOutputStream objectOutputStream) throws IOException {
    objectOutputStream.writeObject(blob2);
  }

  private void testProtostuffReadTime(final ObjectInputStream inputStream,
                                      final LinkedBuffer linkedBuffer) throws IOException, ClassNotFoundException {
    ProtobufIOUtil.mergeFrom(inputStream, new BigBlob1(), BigBlob1.getSchema(), linkedBuffer);
  }

  private void testProtobufReadTime(final ObjectInputStream inputStream) throws IOException {
    BigBlob.BigBlob2.parseDelimitedFrom(inputStream);
  }
}
