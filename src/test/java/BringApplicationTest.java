import com.bringframework.BringApplication;
import com.bringframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class BringApplicationTest {
  @Test
  void constructorShouldNotCreateApplicationContext() {
    BringApplication bringApplication = new BringApplication("testPackageName");
    assertNull(bringApplication.getApplicationContext());
  }

  @Test
  void runShouldReturnCreatedApplicationContext() {
    BringApplication bringApplication = new BringApplication("testPackageName");

    ApplicationContext applicationContext = bringApplication.run();
    assertNotNull(applicationContext);
  }

}
