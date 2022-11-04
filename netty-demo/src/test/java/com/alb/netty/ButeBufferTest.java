package com.alb.netty;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ButeBufferTest {

    @Test
    public void FileRead() {
        //可以通过输入流或输出流间接获取FileChannel
        try {
            FileChannel channel = new FileInputStream("data.txt").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(10);   //ByteBuffer不能直接new，需要通过该方法静态分配一片缓冲区，单位是Byte,即字节
            //从Channel中读数据实际上就是想Buffer中写入数据，时刻记住两者一个是管道，一个是缓冲区的关系
            channel.read(buffer);       //该方法的返回值是Int型，当其值为-1表示不能再读到字节了
            buffer.flip();   //将buffer切换到读模式
            while (buffer.hasRemaining()) {
                byte b = buffer.get();
                System.out.println((char) (b));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CycleReadFile() {
        try {
            FileChannel channel = new FileInputStream("data.txt").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                if (channel.read(buffer) == -1) {
                    break;
                }

                buffer.flip(); //开始进行一个周期的读文件，需要将buffer切换为都模式
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.print((char) (b));
                }
                buffer.clear(); //缓冲区中分配的字节空间已经用完，将缓冲区清空，迎接下一次channel的数据
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //使用put方法向buffer中添加数据
    @Test
    public void putinBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'0', '1', '2', '3', '4', '5'});
        buffer.flip();

        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.print((char) (b));
        }
        buffer.rewind();
        System.out.println();
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.println((char) (b));
        }
    }

    //字符串转换为ByteBuffer
    @Test
    public void stringToByteBuffer() {
        String str="hello world";
        byte[] bytes = str.getBytes();//返回值是一个byte数组
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put(bytes);


        buffer.flip();
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.print((char) (b));
        }
    }

    //粘包和半包的演示实现
    @Test
    public void split(){
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put(("Hello world\nI'm zhangsan\nHo").getBytes());
        
    }




}
