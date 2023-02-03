import com.bringframework.BringApplication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class BringApplicationTest {
  @Test
  void constructorShouldNotCreateApplicationContext() {
    BringApplication bringApplication = new BringApplication();
    assertNull(bringApplication.getApplicationContext());
  }
}
