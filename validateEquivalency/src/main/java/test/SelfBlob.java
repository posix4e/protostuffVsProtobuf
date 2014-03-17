// Generated by http://code.google.com/p/protostuff/ ... DO NOT EDIT!
// Generated from resources

package test;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.nio.ByteBuffer;

import com.dyuproject.protostuff.GraphIOUtil;
import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Message;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.UninitializedMessageException;

public final class SelfBlob implements Externalizable, Message<SelfBlob>, Schema<SelfBlob>
{

    public static Schema<SelfBlob> getSchema()
    {
        return DEFAULT_INSTANCE;
    }

    public static SelfBlob getDefaultInstance()
    {
        return DEFAULT_INSTANCE;
    }

    static final SelfBlob DEFAULT_INSTANCE = new SelfBlob();

    
    private SubGroup blob;
    private int testInt;

    public SelfBlob() {
    }

    public SelfBlob(
       SubGroup blob,
       int testInt
    ) {
       this.blob = blob;
       this.testInt = testInt;
    }

    @Override
    public String toString() {
        return "SelfBlob{" +
                    "blob=" + blob +
                    ", testInt=" + testInt +
                '}';
    }
    // getters and setters

    // blob

    public SubGroup getBlob()
    {
        return blob;
    }


    // testInt

    public int getTestInt()
    {
        return testInt;
    }


    // java serialization

    public void readExternal(ObjectInput in) throws IOException
    {
        GraphIOUtil.mergeDelimitedFrom(in, this, this);
    }

    public void writeExternal(ObjectOutput out) throws IOException
    {
        GraphIOUtil.writeDelimitedTo(out, this, this);
    }

    // message method

    public Schema<SelfBlob> cachedSchema()
    {
        return DEFAULT_INSTANCE;
    }

    // schema methods

    public SelfBlob newMessage()
    {
        return new SelfBlob();
    }

    public Class<SelfBlob> typeClass()
    {
        return SelfBlob.class;
    }

    public String messageName()
    {
        return SelfBlob.class.getSimpleName();
    }

    public String messageFullName()
    {
        return SelfBlob.class.getName();
    }

    public boolean isInitialized(SelfBlob message)
    {
        return true;
    }

    public void mergeFrom(Input input, SelfBlob message) throws IOException
    {
        for(int number = input.readFieldNumber(this);; number = input.readFieldNumber(this))
        {
            switch(number)
            {
                case 0:
                    return;
                case 1:
                    message.blob = input.mergeObject(message.blob, SubGroup.getSchema());
                    break;

                case 2:
                    message.testInt = input.readInt32();
                    break;
                default:
                    input.handleUnknownField(number, this);
            }   
        }
    }


    public void writeTo(Output output, SelfBlob message) throws IOException
    {
        if(message.blob != null)
            output.writeObject(1, message.blob, SubGroup.getSchema(), false);


        output.writeInt32(2, message.testInt, false);

    }

    public String getFieldName(int number)
    {
        switch(number)
        {
            case 1: return "blob";
            case 2: return "testInt";
            default: return null;
        }
    }

    public int getFieldNumber(String name)
    {
        final Integer number = __fieldMap.get(name);
        return number == null ? 0 : number.intValue();
    }

    private static final java.util.HashMap<String,Integer> __fieldMap = new java.util.HashMap<String,Integer>();
    static
    {
        __fieldMap.put("blob", 1);
        __fieldMap.put("testInt", 2);
    }
    
}
