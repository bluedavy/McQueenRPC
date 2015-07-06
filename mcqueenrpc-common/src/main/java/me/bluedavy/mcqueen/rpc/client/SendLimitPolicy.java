/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
package me.bluedavy.mcqueen.rpc.client;

/**
 * when send bytes size reaches limit size,then do sth.
 *
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public enum SendLimitPolicy {

	REJECT, // Reject send request and throw exception
	WAIT1SECOND // Wait 1 second then retry,if failed again,then throw exception
	
}
