// Generated by http://code.google.com/p/protostuff/ ... DO NOT EDIT!
// Generated from resources

package test;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import java.nio.ByteBuffer;

import com.dyuproject.protostuff.GraphIOUtil;
import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Message;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.Schema;

public final class SubGroup implements Externalizable, Message<SubGroup>, Schema<SubGroup>
{

    public static Schema<SubGroup> getSchema()
    {
        return DEFAULT_INSTANCE;
    }

    public static SubGroup getDefaultInstance()
    {
        return DEFAULT_INSTANCE;
    }

    static final SubGroup DEFAULT_INSTANCE = new SubGroup();

    
    private List<ByteBuffer> byteGroup;
    private SubSubGroup subSubGroupA;
    private SubSubGroup subSubGroupB;

    public SubGroup() {
    }

    public SubGroup(
       List<ByteBuffer> byteGroup,
       SubSubGroup subSubGroupA,
       SubSubGroup subSubGroupB
    ) {
       this.byteGroup = java.util.Collections.unmodifiableList(byteGroup);
       this.subSubGroupA = subSubGroupA;
       this.subSubGroupB = subSubGroupB;
    }

    @Override
    public String toString() {
        return "SubGroup{" +
                    "byteGroup=" + byteGroup +
                    ", subSubGroupA=" + subSubGroupA +
                    ", subSubGroupB=" + subSubGroupB +
                '}';
    }
    // getters and setters

    // byteGroup

    public List<ByteBuffer> getByteGroupList()
    {
        return byteGroup;
    }


    // subSubGroupA

    public SubSubGroup getSubSubGroupA()
    {
        return subSubGroupA;
    }


    // subSubGroupB

    public SubSubGroup getSubSubGroupB()
    {
        return subSubGroupB;
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

    public Schema<SubGroup> cachedSchema()
    {
        return DEFAULT_INSTANCE;
    }

    // schema methods

    public SubGroup newMessage()
    {
        return new SubGroup();
    }

    public Class<SubGroup> typeClass()
    {
        return SubGroup.class;
    }

    public String messageName()
    {
        return SubGroup.class.getSimpleName();
    }

    public String messageFullName()
    {
        return SubGroup.class.getName();
    }

    public boolean isInitialized(SubGroup message)
    {
        return true;
    }

    public void mergeFrom(Input input, SubGroup message) throws IOException
    {
        try {
        for(int number = input.readFieldNumber(this);; number = input.readFieldNumber(this))
        {
            switch(number)
            {
                case 0:
                    return;
                case 1:
                    if(message.byteGroup == null)
                        message.byteGroup = new ArrayList<ByteBuffer>();
                    message.byteGroup.add(input.readByteBuffer());
                    break;
                case 2:
                    message.subSubGroupA = input.mergeObject(message.subSubGroupA, SubSubGroup.getSchema());
                    break;

                case 3:
                    message.subSubGroupB = input.mergeObject(message.subSubGroupB, SubSubGroup.getSchema());
                    break;

                default:
                    input.handleUnknownField(number, this);
            }   
        }
        } finally {
        if (message.byteGroup != null)
            message.byteGroup = java.util.Collections.unmodifiableList(message.byteGroup);
        else
            message.byteGroup = java.util.Collections.emptyList();
        }
    }


    public void writeTo(Output output, SubGroup message) throws IOException
    {
        if(message.byteGroup != null)
        {
            for(ByteBuffer byteGroup : message.byteGroup)
            {
                if(byteGroup != null)
                    output.writeBytes(1, byteGroup, true);
            }
        }

        if(message.subSubGroupA != null)
            output.writeObject(2, message.subSubGroupA, SubSubGroup.getSchema(), false);


        if(message.subSubGroupB != null)
            output.writeObject(3, message.subSubGroupB, SubSubGroup.getSchema(), false);

    }

    public String getFieldName(int number)
    {
        switch(number)
        {
            case 1: return "byteGroup";
            case 2: return "subSubGroupA";
            case 3: return "subSubGroupB";
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
        __fieldMap.put("byteGroup", 1);
        __fieldMap.put("subSubGroupA", 2);
        __fieldMap.put("subSubGroupB", 3);
    }
    
}
