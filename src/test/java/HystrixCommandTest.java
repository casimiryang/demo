import com.hystrix.CommandCollapserGetValueForKey;
import com.hystrix.CommandUsingRequestCache;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lenovo on 2017/9/12.
 */
public class HystrixCommandTest {


    @Test
    public void testWithoutCacheHits() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            assertTrue(new CommandUsingRequestCache(2).execute());
            assertTrue(new CommandUsingRequestCache(4 ).execute());
            assertTrue(new CommandUsingRequestCache(2).execute());
        } finally {
            context.shutdown();
        }
    }

    @Test
    public void testCollapser() throws Exception {
        try {
            for (int i = 0; i < 5; i++) {
                HystrixRequestContext context = HystrixRequestContext.initializeContext();
                System.out.println("------");
                Future<String> f1 = new CommandCollapserGetValueForKey(1).queue();
                Future<String> f2 = new CommandCollapserGetValueForKey(2).queue();
                Future<String> f3 = new CommandCollapserGetValueForKey(3).queue();
                Future<String> f4 = new CommandCollapserGetValueForKey(4).queue();

                assertEquals("ValueForKey: 1", f1.get());
                assertEquals("ValueForKey: 2", f2.get());
                assertEquals("ValueForKey: 3", f3.get());
                assertEquals("ValueForKey: 4", f4.get());

                context.shutdown();
            }


            // assert that the batch command 'GetValueForKey' was in fact
            // executed and that it executed only once
            //assertEquals(1, HystrixRequestLog.getCurrentRequest().getExecutedCommands().size());
           // HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
            // assert the command is the one we're expecting
           // assertEquals("GetValueForKey", command.getCommandKey().name());
            // confirm that it was a COLLAPSED command execution
           // assertTrue(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
            // and that it was successful
           // assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
        } finally {
            //context.shutdown();
        }
    }


}
