import org.junit.Assert;
import org.junit.Test;
import com.javacore.brokenLinksFinder.HTMLBrokenLinksFinder;

public class AppTest {
    @Test
    public void mainTest() {
        HTMLBrokenLinksFinder mainClass = new HTMLBrokenLinksFinder();
        String arg = "String sample";
        Assert.assertEquals(arg, mainClass.foo(arg));
    }
}
