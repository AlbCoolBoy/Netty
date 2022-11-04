package com.alb.netty;

import java.nio.ByteBuffer;

/*实现粘包和半包问题的解决*/
/*在实际的网络编程中，进行io进行信息的传输的时候，由于缓冲区的大小是有限的额，所以存在一条数据被拆分成两次传输的情况*/
public class ByteBufferExam {
    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(32);
        buffer.put("Hello world\nI'm zhangsan\nHq".getBytes());

    }
}
