import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    @BeforeEach
    void init () {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spyRestaurant =Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("12:00:00"));
        assertThat(spyRestaurant.isRestaurantOpen(),equalTo(true));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spyRestaurant =Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:00:00"));
        assertThat(spyRestaurant.isRestaurantOpen(),equalTo(false));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_name_should_return_the_restaurant_name() {
        assertThat(restaurant.getName(), equalTo("Amelie's cafe"));
    }

    @Test
    public void display_details_should_return_restaurant_details() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        String orgStr = "Restaurant:"+ "Amelie's cafe" + "\n"
                +"Location:"+ "Chennai" + "\n"
                +"Opening time:"+ "10:30" +"\n"
                +"Closing time:"+ "22:00" +"\n"
                +"Menu:"+"\n"+restaurant.getMenu();
        assertThat(restaurant.displayDetails(),equalTo(orgStr));
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() throws itemNotFoundException{

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void get_item_name_should_return_existing_item() {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(restaurant.getMenu().get(initialMenuSize).getName(),"Sizzling brownie");
        assertEquals(restaurant.getMenu().get(initialMenuSize).getPrice(),319);
    }

    @Test
    public void to_string_item_should_return_existing_item() {
        Item item1 = restaurant.getMenu().get(0);
        String orgStr = "Sweet corn soup:119\n";
        assertThat(item1.toString(), equalTo(orgStr));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void get_order_cost_string_return_total_cost_of_selected_item() throws itemNotFoundException {
        //"Sweet corn soup",119;
        //"Vegetable lasagne", 269;
        //119 + 269 = 388
        int  totalOrderCost = restaurant.getOrderCost("Sweet corn soup", "Vegetable lasagne") ;
        assertEquals(totalOrderCost,388);
    }

    @Test
    public void get_order_cost_string_return_zero_cost_for_no_item_selected() throws itemNotFoundException{

        int  totalOrderCost = restaurant.getOrderCost("French fries") ;
        assertEquals(totalOrderCost,0);
    }

}