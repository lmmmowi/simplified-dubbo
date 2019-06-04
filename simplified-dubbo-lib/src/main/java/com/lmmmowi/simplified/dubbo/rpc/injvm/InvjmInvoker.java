package com.lmmmowi.simplified.dubbo.rpc.injvm;

import com.lmmmowi.simplified.dubbo.rpc.Invocation;
import com.lmmmowi.simplified.dubbo.rpc.Result;
import com.lmmmowi.simplified.dubbo.rpc.RpcException;
import com.lmmmowi.simplified.dubbo.rpc.protocol.AbstractInvoker;

/**
 * @Author: mowi
 * @Date: 2019-06-04
 * @Description:
 */
public class InvjmInvoker<T> extends AbstractInvoker<T> {

    public InvjmInvoker(Class<T> type) {
        super(type);
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        return null;
    }
}
