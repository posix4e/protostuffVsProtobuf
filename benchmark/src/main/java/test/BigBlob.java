// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BigBlob.proto

package test;

public final class BigBlob {
  private BigBlob() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface BigBlob1OrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required bytes blob = 1;
    /**
     * <code>required bytes blob = 1;</code>
     */
    boolean hasBlob();
    /**
     * <code>required bytes blob = 1;</code>
     */
    com.google.protobuf.ByteString getBlob();
  }
  /**
   * Protobuf type {@code test.BigBlob1}
   */
  public static final class BigBlob1 extends
      com.google.protobuf.GeneratedMessage
      implements BigBlob1OrBuilder {
    // Use BigBlob1.newBuilder() to construct.
    private BigBlob1(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private BigBlob1(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final BigBlob1 defaultInstance;
    public static BigBlob1 getDefaultInstance() {
      return defaultInstance;
    }

    public BigBlob1 getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private BigBlob1(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              blob_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return test.BigBlob.internal_static_test_BigBlob1_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return test.BigBlob.internal_static_test_BigBlob1_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              test.BigBlob.BigBlob1.class, test.BigBlob.BigBlob1.Builder.class);
    }

    public static com.google.protobuf.Parser<BigBlob1> PARSER =
        new com.google.protobuf.AbstractParser<BigBlob1>() {
      public BigBlob1 parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new BigBlob1(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<BigBlob1> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required bytes blob = 1;
    public static final int BLOB_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString blob_;
    /**
     * <code>required bytes blob = 1;</code>
     */
    public boolean hasBlob() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required bytes blob = 1;</code>
     */
    public com.google.protobuf.ByteString getBlob() {
      return blob_;
    }

    private void initFields() {
      blob_ = com.google.protobuf.ByteString.EMPTY;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasBlob()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, blob_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, blob_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static test.BigBlob.BigBlob1 parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static test.BigBlob.BigBlob1 parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static test.BigBlob.BigBlob1 parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static test.BigBlob.BigBlob1 parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static test.BigBlob.BigBlob1 parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static test.BigBlob.BigBlob1 parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static test.BigBlob.BigBlob1 parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static test.BigBlob.BigBlob1 parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static test.BigBlob.BigBlob1 parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static test.BigBlob.BigBlob1 parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(test.BigBlob.BigBlob1 prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code test.BigBlob1}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements test.BigBlob.BigBlob1OrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return test.BigBlob.internal_static_test_BigBlob1_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return test.BigBlob.internal_static_test_BigBlob1_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                test.BigBlob.BigBlob1.class, test.BigBlob.BigBlob1.Builder.class);
      }

      // Construct using test.BigBlob.BigBlob1.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        blob_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return test.BigBlob.internal_static_test_BigBlob1_descriptor;
      }

      public test.BigBlob.BigBlob1 getDefaultInstanceForType() {
        return test.BigBlob.BigBlob1.getDefaultInstance();
      }

      public test.BigBlob.BigBlob1 build() {
        test.BigBlob.BigBlob1 result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public test.BigBlob.BigBlob1 buildPartial() {
        test.BigBlob.BigBlob1 result = new test.BigBlob.BigBlob1(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.blob_ = blob_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof test.BigBlob.BigBlob1) {
          return mergeFrom((test.BigBlob.BigBlob1)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(test.BigBlob.BigBlob1 other) {
        if (other == test.BigBlob.BigBlob1.getDefaultInstance()) return this;
        if (other.hasBlob()) {
          setBlob(other.getBlob());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasBlob()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        test.BigBlob.BigBlob1 parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (test.BigBlob.BigBlob1) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required bytes blob = 1;
      private com.google.protobuf.ByteString blob_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes blob = 1;</code>
       */
      public boolean hasBlob() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required bytes blob = 1;</code>
       */
      public com.google.protobuf.ByteString getBlob() {
        return blob_;
      }
      /**
       * <code>required bytes blob = 1;</code>
       */
      public Builder setBlob(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        blob_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes blob = 1;</code>
       */
      public Builder clearBlob() {
        bitField0_ = (bitField0_ & ~0x00000001);
        blob_ = getDefaultInstance().getBlob();
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:test.BigBlob1)
    }

    static {
      defaultInstance = new BigBlob1(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:test.BigBlob1)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_test_BigBlob1_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_test_BigBlob1_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rBigBlob.proto\022\004test\"\030\n\010BigBlob1\022\014\n\004blo" +
      "b\030\001 \002(\014"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_test_BigBlob1_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_test_BigBlob1_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_test_BigBlob1_descriptor,
              new java.lang.String[] { "Blob", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
