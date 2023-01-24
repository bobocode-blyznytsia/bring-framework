import static org.junit.jupiter.api.Assertions.assertSame;

import com.bringframework.Application;
import org.junit.jupiter.api.Test;

public class ApplicationTest {
  @Test
  void sampleTest() {
    Application.main(new String[0]);
    assertSame(4, 2 + 2);
  }
}
