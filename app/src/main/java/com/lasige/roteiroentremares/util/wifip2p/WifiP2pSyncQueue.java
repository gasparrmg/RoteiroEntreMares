package com.lasige.roteiroentremares.util.wifip2p;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class WifiP2pSyncQueue<String> extends AbstractQueue<String> {

    public interface Listener<String> {
        void onElementAdded();
    }

    private final Queue<String> delegate;  //backing queue
    private Listener listener;

    public WifiP2pSyncQueue(Queue<String> delegate) {
        this.delegate = delegate;
    }

    public WifiP2pSyncQueue<String> registerListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    @NonNull
    @Override
    public Iterator<String> iterator() {
        return delegate.iterator();
    }

    @Override
    public int size() {
        return delegate.size();
    }


    @Override
    public boolean offer(String e) {
        // here, we put an element in the backing queue,
        // then notify listeners
        if (delegate.offer(e)) {
            listener.onElementAdded();
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public String poll() {
        return delegate.poll();
    }

    @Nullable
    @Override
    public String peek() {
        return delegate.peek();
    }
}
