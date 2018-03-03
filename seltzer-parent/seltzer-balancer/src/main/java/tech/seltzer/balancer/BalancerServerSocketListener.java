package tech.seltzer.balancer;

import tech.seltzer.server.util.AbstractServerSocketListener;

public class BalancerServerSocketListener extends AbstractServerSocketListener {

	public BalancerServerSocketListener(int port, int backlog) {
		super(port, backlog);
	}

	@Override
	public void run() {
	}
}
