// ==version1==
```
+ "Validate Booking Eligibility": Check if a customer can book seats on a trip. Eligibility: Trip must exist and have available seats. The number of available seats on the trip ≥ the number of booking seats. There are no overlapping time periods between existing bookings booked by customer and the new booking time on the same day. The current booking time must be at least 2 hours earlier than the departure time of the trip(excluding the case where it is exactly 2 hours). If a customer can book seats on a trip, return true and update the number of seats of the trip. 

+ "Calculate Discounted Trip Price": Compute the final price for a booking after applying reward discounts (the discount is always 20%off). Discount applies only if the booking is made by customer with membership and the booking time is ≥24 hours before departure. Return the final price (Keep 1 decimal places) if conditions are met, otherwise, return original price.

+ "Check Stop Overlap for Indirect Trips": Determine if two indirect trips (have ≥1 stop) by the same driver share any common stops. Return true if any stop station is shared.

+ "Compute Monthly Reward Points": Calculate total reward points earned by a customer in the given current month. Preconditions: The customer must have a membership with a points reward. Calculation rules: Only calculate the bookings whose booking dates are within the target month. 5 points per seat per booking. Return totalPoints = sum(seats * 5) for eligible bookings.

+ "Validate Trip Posting Feasibility": Preconditions: The driver must exist and be valid; The new trip time must be valid (departure < arrival). Check if there are time conflicts between the new trip and the existed trips (The time period of the new trip intersects with that of any existing trip. Completely identical time periods are considered a conflict. Adjacent boundaries (e.g., A ends = B starts) are not considered a conflict.) Return true if no time conflict.

```
// ==end==