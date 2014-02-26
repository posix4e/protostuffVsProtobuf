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
    comparisonTest(capacity);
    comparisonTest(capacity);
    comparisonTest(capacity);
    comparisonTest(capacity);
    comparisonTest(capacity);
    comparisonTest(capacity);
    comparisonTest(capacity);
    comparisonTest(capacity);
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
    System.out.println("Allocating: " + capacity + " bytes ");
    ByteBuffer buffer = ByteBuffer.allocate(capacity);
    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    objectOutputStream = new ObjectOutputStream(dataOutputStream);

    testProtostuffTime(buffer);
    testGoogleProtobufTime(buffer);
  }

  private static void testProtostuffTime(ByteBuffer buffer) throws IOException {
    long begin, end;
    begin = System.nanoTime();
    BigBlob1 blob1 = new BigBlob1(buffer);
    objectOutputStream.writeObject(blob1);
    objectOutputStream.flush();
    dataOutputStream.flush();
    end = System.nanoTime();
    System.out.println("Protostuff:" + (end - begin));

  }

  private static void testGoogleProtobufTime(ByteBuffer buffer) throws IOException {
    long begin;
    long end;
    begin = System.nanoTime();
    BigBlob.BigBlob2 blob2 = BigBlob.BigBlob2.newBuilder().setBlob(com.google.protobuf.ByteString.copyFrom(buffer.array())).build();
    objectOutputStream.writeObject(blob2);
    end = System.nanoTime();
    objectOutputStream.flush();
    dataOutputStream.flush();
    System.out.println(" ProtoBuff:" + (end - begin));
  }
}
