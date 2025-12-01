import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_successfulRental() throws ParseException {
        // Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        List<VideoRental> rentals = new ArrayList<>();