package test;

import com.dyuproject.protostuff.CodedInput;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.LowCopyProtobufOutput;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.google.protobuf.ByteString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Random;

public class ProtostuffVsProtobuf {

  static int max_byte_size = 1024 * 1024 * 32;
  static byte[] random_bytes = new byte[max_byte_size];

  public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {

    Random random = new Random();
    random.nextBytes(random_bytes);

    int repeated_runs = 10;

    if (args.length == 1) {
      max_byte_size = Integer.parseInt(args[0]);
    }

    ProtostuffVsProtobuf protoStuffTimingWithOutput = new ProtostuffVsProtobuf();

    int warm_up_runs = 100;
    for (int i = 1; i < warm_up_runs; i++) {
      protoStuffTimingWithOutput.comparisonTest(10, warm_up_runs, false);
    }

    for (int i = 1; i < max_byte_size; i *= 2) {
      for (int j = 0; j < repeated_runs; j++) {
        double pert = (j * .01) + 1;
        int pertCapacity = Double.valueOf(Math.ceil(pert * i)).intValue();
        protoStuffTimingWithOutput.comparisonTest(pertCapacity, 10, true);
      }
    }
  }

  private void comparisonTest(final int capacity, final int runs, final boolean output)
      throws IOException, ClassNotFoundException {

    ByteBuffer buffer = ByteBuffer.allocate(capacity);
    buffer.put(random_bytes, 0, capacity);
    buffer.rewind();

    if (output) {
      System.out.print(capacity + " \t");
    }
    benchmarkCreateTime(runs, buffer, output);
    benchmarkReadWriteTime(runs, buffer, output);
  }

  private void benchmarkReadWriteTime(final int runs, final ByteBuffer buffer, boolean output)
      throws IOException, ClassNotFoundException {

    // Protostuff
    BigBlob1 blob1 = new BigBlob1(buffer);
    long begin = System.nanoTime();

    LowCopyProtobufOutput lowCopyProtobufOutput = new LowCopyProtobufOutput();

    for (int i = 0; i != runs; i++) {
      testProtostuffWriteTime(blob1, lowCopyProtobufOutput);
    }

    if (output) {
      System.out.print(System.nanoTime() - begin + "\t");
    }

    begin = System.nanoTime();


    CodedInput[] byteBufferInputs = new CodedInput[runs];
    for (int i = 0; i != runs; i++) {
      byte[] blobBytes = ProtobufIOUtil.toByteArray(blob1, BigBlob1.getSchema(), LinkedBuffer.allocate(256));
      byteBufferInputs[i] = CodedInput.newInstance(blobBytes);
    }

    for (int i = 0; i != runs; i++) {
      testProtostuffReadTime(byteBufferInputs[i]);
    }
    if (output) {
      System.out.print(System.nanoTime() - begin + "\t");
    }

    // Protobuf
    BigBlob.BigBlob1 blob2 = BigBlob.BigBlob1.newBuilder().setBlob(ByteString.copyFrom(buffer.array())).build();
    ByteArrayOutputStream dataOutputStream = new ByteArrayOutputStream();
    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testGoogleProtobufWriteTime(blob2, dataOutputStream);
    }

    if (output) {
      System.out.print(System.nanoTime() - begin + "\t");
    }

    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataOutputStream.toByteArray());
    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testProtobufReadTime(byteArrayInputStream);
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
    ByteString blob = ByteString.copyFrom(buffer.array());
    BigBlob.BigBlob1.Builder bigBlob2 = BigBlob.BigBlob1.newBuilder();
    bigBlob2.setBlob(blob);
    bigBlob2.build();
  }

  private void testProtostuffCreateTime(final ByteBuffer buffer) {
    new BigBlob1(buffer);
  }

  private void testProtostuffWriteTime(final BigBlob1 blob1,
                                       final LowCopyProtobufOutput lowCopyProtobufOutput) throws IOException {
    blob1.writeTo(lowCopyProtobufOutput, blob1);
  }

  private void testGoogleProtobufWriteTime(final BigBlob.BigBlob1 blob2,
                                           final OutputStream outputStream) throws IOException {
    blob2.writeDelimitedTo(outputStream);
  }

  private void testProtostuffReadTime(final CodedInput input) throws IOException, ClassNotFoundException {
    BigBlob1 blob = new BigBlob1();
    BigBlob1.getSchema().mergeFrom(input, blob);

  }

  private void testProtobufReadTime(final InputStream inputStream) throws IOException {
    BigBlob.BigBlob1.parseDelimitedFrom(inputStream);
  }
}
