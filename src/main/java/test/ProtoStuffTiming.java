package test;

import com.dyuproject.protostuff.ByteString;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by posix4e on 2/25/14.
 */
public class ProtoStuffTiming {

  static ByteArrayOutputStream dataOutputStream;
  public static void main(String args[]){
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

  private static void comparisonTest(int capacity) {
    System.out.println("Allocating: " + capacity + " bytes ");
    ByteBuffer buffer = ByteBuffer.allocate(capacity);
    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    testProtostuffTime(buffer);
    testGoogleProtobufTime(buffer);
  }

  private static void testProtostuffTime(ByteBuffer buffer) {
    long begin,end;
    begin = System.nanoTime();
    BigBlob1 blob1 = new BigBlob1(buffer);
    end = System.nanoTime();
    System.out.println("Protostuff:" + (end - begin));
  }

  private static void testGoogleProtobufTime(ByteBuffer buffer) {
    long begin;
    long end;
    begin = System.nanoTime();
    BigBlob.BigBlob2 blob2 = BigBlob.BigBlob2.newBuilder().setBlob(com.google.protobuf.ByteString.copyFrom(buffer.array())).build();
    end = System.nanoTime();
    System.out.println(" ProtoBuff:" + (end - begin));
  }
}
