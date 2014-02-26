package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class ProtoStuffTimingWithOutput {

  static ByteArrayOutputStream dataOutputStream;
  static ObjectOutputStream objectOutputStream;

  public static void main(String args[]) throws IOException {
    int max_byte_size = 1024 * 1024 * 16;

    if (args.length == 1) {
      max_byte_size = Integer.parseInt(args[0]);
    }

    System.err.println("#bytesize\tprotostufCreateTime\tprotostuffCreateAndWrite\tprotobufCreateTime\tprotobufCreateAndWrite");
    System.out.println("Ignore the top result, the jvm makes it innacurate");
    comparisonTest(16, 2);

    for (int i = 1; i < max_byte_size ; i *=2) {
      System.out.println(i);
      comparisonTest(i, 5);
    }
  }

  private static void comparisonTest(int capacity, int runs) throws IOException {

    ByteBuffer buffer = ByteBuffer.allocate(capacity);
    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    objectOutputStream = new ObjectOutputStream(dataOutputStream);
    long protoStuffAllocation, protoBufAllocation, protoStuffCreateAndWrite, protoBufCreateAndWrite;
    protoStuffAllocation = 0;
    protoBufAllocation = 0;
    protoStuffCreateAndWrite = 0;
    protoBufCreateAndWrite = 0;

    for (int i = 0; i != runs; i++) {
      System.err.print(capacity);
      protoStuffAllocation += testProtostuffCreateTime(buffer);
      protoStuffCreateAndWrite += testProtostuffCreateAndWriteTime(buffer);
      protoBufAllocation += testGoogleProtobufCreateTime(buffer);
      protoBufCreateAndWrite += testGoogleProtobufCreateAndWriteTime(buffer);
    }
  }

  private static long testGoogleProtobufCreateTime(ByteBuffer buffer) {
    long begin, end;
    begin = System.nanoTime();

    BigBlob.BigBlob2.newBuilder().setBlob(com.google.protobuf.ByteString.copyFrom(buffer.array())).build();
    end = System.nanoTime();
    System.err.print("\t" + (end - begin));
    return end - begin;
  }

  private static long testProtostuffCreateTime(ByteBuffer buffer) {
    long begin, end;
    begin = System.nanoTime();
    new BigBlob1(buffer);
    end = System.nanoTime();
    System.err.print("\t" + (end - begin));
    return end - begin;
  }

  private static long testProtostuffCreateAndWriteTime(ByteBuffer buffer) throws IOException {
    long begin, end;
    begin = System.nanoTime();
    BigBlob1 blob1 = new BigBlob1(buffer);
    objectOutputStream.writeObject(blob1);
    objectOutputStream.flush();
    dataOutputStream.flush();
    end = System.nanoTime();
    System.err.print("\t" + (end - begin));
    return end - begin;

  }

  private static long testGoogleProtobufCreateAndWriteTime(ByteBuffer buffer) throws IOException {
    long begin;
    long end;
    begin = System.nanoTime();
    BigBlob.BigBlob2 blob2 = BigBlob.BigBlob2.newBuilder().setBlob(com.google.protobuf.ByteString.copyFrom(buffer.array())).build();
    objectOutputStream.writeObject(blob2);
    objectOutputStream.flush();
    dataOutputStream.flush();
    end = System.nanoTime();
    System.err.println("\t" + (end - begin));
    return end - begin;
  }
}
