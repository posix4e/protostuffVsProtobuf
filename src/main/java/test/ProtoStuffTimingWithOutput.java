package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class ProtoStuffTimingWithOutput {

  static ByteArrayOutputStream dataOutputStream;
  static ObjectOutputStream objectOutputStream;

  public static void main(String args[]) throws IOException {
    int capacity = 16;
    int multiplier = 16;

    if (args.length == 2) {
      capacity = Integer.parseInt(args[0]);
      multiplier = Integer.parseInt(args[1]);
    }

    System.out.println("Ignore the top result, the jvm makes it innacurate");
    comparisonTest(capacity);
    comparisonTest(capacity);

    capacity *= multiplier;
    comparisonTest(capacity);

    capacity *= multiplier;
    comparisonTest(capacity);
    capacity *= multiplier;
    comparisonTest(capacity);
    capacity *= multiplier;
    comparisonTest(capacity);
    capacity *= multiplier;
    comparisonTest(capacity, 10);
  }

  private static void comparisonTest(int capacity) throws IOException {
    comparisonTest(capacity, 25);
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
      protoStuffAllocation += testProtostuffCreateTime(buffer);
      protoStuffCreateAndWrite += testProtostuffCreateAndWriteTime(buffer);
      protoBufAllocation += testGoogleProtobufCreateTime(buffer);
      protoBufCreateAndWrite += testGoogleProtobufCreateAndWriteTime(buffer);

    }

    System.out.println("For creation of objects of size " + capacity + "\tProtostuff:" + protoStuffAllocation + " vs " + "\tProtobuf:" + protoBufAllocation + "\nFor creation and writing of objects of size " + capacity + "\tProtostuff:" + protoStuffCreateAndWrite + " vs " + "\tProtobuf:" + protoBufCreateAndWrite);

  }

  private static long testGoogleProtobufCreateTime(ByteBuffer buffer) {
    long begin, end;
    begin = System.nanoTime();

    BigBlob.BigBlob2.newBuilder().setBlob(com.google.protobuf.ByteString.copyFrom(buffer.array())).build();
    end = System.nanoTime();
    return end - begin;
  }

  private static long testProtostuffCreateTime(ByteBuffer buffer) {
    long begin, end;
    begin = System.nanoTime();
    new BigBlob1(buffer);
    end = System.nanoTime();
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
    return end - begin;

  }

  private static long testGoogleProtobufCreateAndWriteTime(ByteBuffer buffer) throws IOException {
    long begin;
    long end;
    begin = System.nanoTime();
    BigBlob.BigBlob2 blob2 = BigBlob.BigBlob2.newBuilder().setBlob(com.google.protobuf.ByteString.copyFrom(buffer.array())).build();
    objectOutputStream.writeObject(blob2);
    end = System.nanoTime();
    objectOutputStream.flush();
    dataOutputStream.flush();
    return end - begin;
  }
}
