package test;

import com.google.protobuf.ByteString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class ProtoStuffTimingWithOutput {

  public static void main(String args[]) throws IOException, ClassNotFoundException {
    int max_byte_size = 1024 * 1024 * 16;
    int repeated_runs = 10;

    if (args.length == 1) {
      max_byte_size = Integer.parseInt(args[0]);
    }

    ProtoStuffTimingWithOutput protoStuffTimingWithOutput = new ProtoStuffTimingWithOutput();

    for (int i = 1; i < max_byte_size; i *= 2) {
      for (int j = 0; j < repeated_runs; j++) {
        double pert = (j * .1) + 1;
        int pertCapacity = Double.valueOf(Math.ceil(pert * i)).intValue();
        protoStuffTimingWithOutput.comparisonTest(pertCapacity, 5);
      }
    }
  }

  private void comparisonTest(int capacity, int runs) throws IOException, ClassNotFoundException {

    ByteArrayOutputStream dataOutputStream;
    ObjectOutputStream objectOutputStream;

    ByteBuffer buffer = ByteBuffer.allocate(capacity);
    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    objectOutputStream = new ObjectOutputStream(dataOutputStream);

    System.out.print(capacity + " \t");

    long begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testProtostuffCreateTime(buffer);
    }
    System.out.print(System.nanoTime() - begin + "\t");

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testProtostuffCreateWriteTime(buffer, dataOutputStream, objectOutputStream);
    }
    System.out.print(System.nanoTime() - begin + "\t");
    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    objectOutputStream = new ObjectOutputStream(dataOutputStream);

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testProtostuffCreateWriteReadTime(buffer, dataOutputStream, objectOutputStream);
    }
    System.out.print(System.nanoTime() - begin + "\t");
    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    objectOutputStream = new ObjectOutputStream(dataOutputStream);

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testGoogleProtobufCreateTime(buffer);
    }
    System.out.print(System.nanoTime() - begin + "\t");

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testGoogleProtobufCreateWriteTime(buffer, dataOutputStream, objectOutputStream);
    }
    System.out.print(System.nanoTime() - begin + "\t");
    dataOutputStream = new ByteArrayOutputStream(capacity * 2);
    objectOutputStream = new ObjectOutputStream(dataOutputStream);

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testProtobufCreateWriteReadTime(buffer, dataOutputStream, objectOutputStream);
    }
    System.out.println(System.nanoTime() - begin);
  }

  private void testGoogleProtobufCreateTime(final ByteBuffer buffer) {
    BigBlob.BigBlob2.newBuilder().setBlob(com.google.protobuf.ByteString.copyFrom(buffer.array())).build();
  }

  private void testProtostuffCreateTime(final ByteBuffer buffer) {
    new BigBlob1(buffer);
  }

  private void testProtostuffCreateWriteTime(final ByteBuffer buffer,
                                             final ByteArrayOutputStream dataOutputStream,
                                             final ObjectOutputStream objectOutputStream) throws IOException {
    BigBlob1 blob1 = new BigBlob1(buffer);
    objectOutputStream.writeObject(blob1);
    objectOutputStream.flush();
    dataOutputStream.flush();
  }

  private void testGoogleProtobufCreateWriteTime(final ByteBuffer buffer,
                                                 final ByteArrayOutputStream dataOutputStream,
                                                 final ObjectOutputStream objectOutputStream) throws IOException {
    ByteString blobString = ByteString.copyFrom(buffer.array());
    BigBlob.BigBlob2 blob2 = BigBlob.BigBlob2.newBuilder().setBlob(blobString).build();
    objectOutputStream.writeObject(blob2);
    objectOutputStream.flush();
    dataOutputStream.flush();
  }

  private void testProtostuffCreateWriteReadTime(final ByteBuffer buffer,
                                                 final ByteArrayOutputStream dataOutputStream,
                                                 ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
    BigBlob1 blob1 = new BigBlob1(buffer);
    objectOutputStream.writeObject(blob1);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(dataOutputStream.toByteArray());
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    blob1 = (BigBlob1) objectInputStream.readObject();
  }

  private void testProtobufCreateWriteReadTime(final ByteBuffer buffer,
                                               final ByteArrayOutputStream dataOutputStream,
                                               final ObjectOutputStream objectOutputStream) throws IOException {
    ByteString blobString = ByteString.copyFrom(buffer.array());
    BigBlob.BigBlob2 blob2 = BigBlob.BigBlob2.newBuilder().setBlob(blobString).build();
    objectOutputStream.writeObject(blob2);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(dataOutputStream.toByteArray());
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    blob2 = BigBlob.BigBlob2.parseDelimitedFrom(objectInputStream);
  }
}
