package com.xgoing.xmq.core.connection;

import com.xgoing.xmq.core.consumer.XConsumer;
import com.xgoing.xmq.core.destination.XDestination;
import com.xgoing.xmq.core.listener.XListener;
import com.xgoing.xmq.core.producer.XProducer;

import java.io.Closeable;

public interface XConnection extends Closeable {
    XProducer createProducer(String topic);

    XConsumer createConsumer(String topic, XListener listener);
}
