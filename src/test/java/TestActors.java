import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.actor.ActorSystem;
import akka.testkit.TestProbe;
import com.jhood.playground.Ping;
import com.jhood.playground.TestActor;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;

public class TestActors {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        system.shutdown();
        system.awaitTermination(Duration.create("10 seconds"));
    }

    @Test
    public void testPinging() {
        new JavaTestKit(system) {{
            TestProbe testProbe = TestProbe.apply(system);
            ActorRef responder = system.actorOf(Props.create(TestActor.class));
            responder.tell(new Ping(), testProbe.ref());
            Object msg = testProbe.receiveOne(Duration.create("10 seconds"));
            Assert.assertTrue(msg instanceof Ping);
        }};
    }
}
