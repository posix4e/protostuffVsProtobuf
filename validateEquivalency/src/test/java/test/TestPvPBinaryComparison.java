package test;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.google.protobuf.ByteString;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class TestPvPBinaryComparison {
  static int max_byte_size = 1024 * 1024 * 32;
  static byte[] random_bytes = new byte[max_byte_size];
  static Random random = new Random();

  static {
    random.nextBytes(random_bytes);
  }

  @Test
  public void compareSimpleBlob() throws IOException {


    int capacity = 64;
    do {
      ByteBuffer buffer = ByteBuffer.allocate(capacity);
      buffer.put(random_bytes, 0, capacity);
      buffer.rewind();

      BigBlob1 blob1 = new BigBlob1(buffer);

      BigBlob.BigBlob1.Builder blob2 = BigBlob.BigBlob1.newBuilder();
      blob2.setBlob(ByteString.copyFrom(buffer));
      buffer.rewind();

      ByteArrayOutputStream dataOutputStream = new ByteArrayOutputStream();

      blob2.build().writeTo(dataOutputStream);

      byte[] blob1Bytes = ProtobufIOUtil.toByteArray(blob1, BigBlob1.getSchema(), LinkedBuffer.allocate(256));
      byte[] blob2Bytes = dataOutputStream.toByteArray();
      Assert.assertArrayEquals(blob1Bytes, blob2Bytes);
      capacity = capacity * 2;
    } while (capacity < max_byte_size);
  }

  @Test
  public void compareSelfBlob() throws IOException {

    int randomInt = random.nextInt();

    int capacity = 64;
    ByteBuffer buffer = ByteBuffer.allocate(capacity);
    buffer.put(random_bytes, 0, capacity);
    buffer.rewind();

    ComplexBlob.SubSubGroup.Builder subSubGroupAB = ComplexBlob.SubSubGroup.newBuilder().setTestIntA(randomInt).setTestIntB(randomInt);
    ComplexBlob.SubSubGroup.Builder subSubGroupBB = ComplexBlob.SubSubGroup.newBuilder().setTestIntA(randomInt).setTestIntB(randomInt);
    ComplexBlob.SubGroup.Builder subGroupB = ComplexBlob.SubGroup.newBuilder()
        .setSubSubGroupA(subSubGroupAB)
        .setSubSubGroupB(subSubGroupBB)
        .addByteGroup(ByteString.copyFrom(buffer));

    buffer.rewind();

    ComplexBlob.SelfBlob.Builder selfBlob1B = ComplexBlob.SelfBlob.newBuilder().setTestInt(randomInt).setBlob(subGroupB);

    SelfBlob selfBlob2 = new SelfBlob(new SubGroup(Arrays.asList(buffer), new SubSubGroup(randomInt, randomInt), new SubSubGroup(randomInt, randomInt)), randomInt);


    byte[] blob2BytesR = ProtobufIOUtil.toByteArray(selfBlob2, SelfBlob.getSchema(), LinkedBuffer.allocate(256));

    ByteArrayOutputStream dataOutputStream = new ByteArrayOutputStream();
    selfBlob1B.build().writeTo(dataOutputStream);

    Assert.assertArrayEquals(dataOutputStream.toByteArray(), blob2BytesR);
  }
}
