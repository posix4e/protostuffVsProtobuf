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
    comparisonTest(capacity);

    capacity *= 16;
    comparisonTest(capacity);

    capacity *= 16;
    comparisonTest(capacity);
    capacity *= 16;
    comparisonTest(capacity);
    capacity *= 16;
    comparisonTest(capacity);
    capacity *= 16;
    comparisonTest(capacity);
  }

  private static void comparisonTest(int capacity) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(capacity);
    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    objectOutputStream = new ObjectOutputStream(dataOutputStream);
    long protoStuffAllocation, protoBuffAllocation, protoStuffCreateAndWrite, protoBuffCreateAndWrite;
    protoStuffAllocation = 0;
    protoBuffAllocation = 0;
    protoStuffCreateAndWrite = 0;
    protoBuffCreateAndWrite = 0;

    for (int i = 0; i != 10; i++) {
      protoStuffAllocation += testProtostuffCreateTime(buffer);
      protoBuffAllocation += testGoogleProtobufCreateTime(buffer);
      protoStuffCreateAndWrite += testProtostuffCreateAndWriteTime(buffer);
      protoBuffCreateAndWrite += testGoogleProtobufCreateAndWriteTime(buffer);
    }

    System.out.println("For creation of objects of size " + capacity + "\tProtostuff:" + protoStuffAllocation + " vs " + "\tProtobuff:" + protoBuffAllocation + "\nFor creation and writing of objects of size " + capacity + "\tProtostuff:" + protoStuffCreateAndWrite + " vs " + "\tProtobuff:" + protoBuffCreateAndWrite);

  }

  private static long testGoogleProtobufCreateTime(ByteBuffer buffer) {
    long begin, end;
    begin = System.nanoTime();

    for (int i = 0; i != 100; i++) {
     BigBlob.BigBlob2.newBuilder().setBlob(com.google.protobuf.ByteString.copyFrom(buffer.array())).build();
    }
    end = System.nanoTime();
    return end - begin;
  }

  private static long testProtostuffCreateTime(ByteBuffer buffer) {
    long begin, end;
    begin = System.nanoTime();
    for (int i = 0; i != 100; i++) {
      new BigBlob1(buffer);
    }
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
