package test;

import com.dyuproject.protostuff.ByteBufferInput;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.LowCopyProtobufOutput;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;

public class ProtostuffVsProtobuf {

  static int max_byte_size = 1024 * 1024 * 16;
  static byte[] random_bytes = new byte[max_byte_size];
  final LinkedBuffer linkedBuffer = LinkedBuffer.allocate(1024);

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
      protoStuffTimingWithOutput.comparisonTest(warm_up_runs * 10, 100, false);
    }

    for (int i = 1; i < max_byte_size; i *= 2) {
      for (int j = 0; j < repeated_runs; j++) {
        double pert = (j * .1) + 1;
        int pertCapacity = Double.valueOf(Math.ceil(pert * i)).intValue();
        protoStuffTimingWithOutput.comparisonTest(pertCapacity, 100, true);
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

    List<ByteBuffer> byteBuffers = lowCopyProtobufOutput.buffer.finish();
    ByteBuf pooledBuffer = Unpooled.wrappedBuffer(byteBuffers.toArray(new ByteBuffer[byteBuffers.size()]));
    ByteBufferInput byteBufferInput = new ByteBufferInput(pooledBuffer.nioBuffer(), false);

    begin = System.nanoTime();
    for (int i = 0; i != runs; i++) {
      testProtostuffReadTime(byteBufferInput);
    }
    if (output) {
      System.out.print(System.nanoTime() - begin + "\t");
    }

    linkedBuffer.clear();
    // Protobuf

    BigBlob.BigBlob2 blob2 = BigBlob.BigBlob2.newBuilder().setBlob(ByteString.copyFrom(buffer.array())).build();
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
    BigBlob.BigBlob2.Builder bigBlob2 = BigBlob.BigBlob2.newBuilder();
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

  private void testGoogleProtobufWriteTime(final BigBlob.BigBlob2 blob2,
                                           final OutputStream outputStream) throws IOException {
    blob2.writeDelimitedTo(outputStream);
  }

  private void testProtostuffReadTime(final ByteBufferInput input) throws IOException, ClassNotFoundException {

    BigBlob1.getSchema().mergeFrom(input, BigBlob1.DEFAULT_INSTANCE);
  }

  private void testProtobufReadTime(final InputStream inputStream) throws IOException {
    BigBlob.BigBlob2.parseDelimitedFrom(inputStream);
  }
}
