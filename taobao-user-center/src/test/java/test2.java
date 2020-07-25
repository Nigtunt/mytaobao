import javafx.application.Application;
import sun.nio.ch.FileLockImpl;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: YHQ
 * @Date: 2020/7/16 20:18
 */
public class test2 {

    public static void main(String args[]) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            int select = selector.select();
            if (select==0) continue;

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                //接入事件
                if (selectionKey.isAcceptable()){
                    SocketChannel accept = serverSocketChannel.accept();
                    accept.configureBlocking(false);

                    accept.register(selector,SelectionKey.OP_READ);

                    accept.write(Charset.forName("UTF-8").encode("已连接"));
                }
                //可读事件
                if (selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    StringBuilder request = new StringBuilder();
                    while (channel.read(byteBuffer)>0){
                        byteBuffer.flip();
                        request.append(Charset.forName("utf-8").decode(byteBuffer));
                    }
                    channel.register(selector,SelectionKey.OP_READ);

                    if (request.length()>0){
                        System.out.println("接收到：" + request.toString());
                        Set<SelectionKey> keys = selector.keys();

                        keys.forEach(sk -> {
                            Channel c = sk.channel();
                            //剔除发消息的客户端
                            if (c instanceof SocketChannel && c != channel){
                                try {
                                    ((SocketChannel) c).write(Charset.forName("utf-8")
                                            .encode(request.toString()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }

        }
    }
}
