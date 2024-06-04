package nz.co.sample.store;

import nz.co.sample.Assertion;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import nz.co.sample.storeScreen.StoreLandingScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import javax.inject.Inject;

@ScenarioScoped
public class StoreShoppingSteps {

    private static final Logger log = LogManager.getLogger(StoreShoppingSteps.class.getName());
    private final StoreLandingScreen storeLandingScreen;
    private Scenario scenario;
    private String product1;
    private String priceProduct1;
    private String product2;
    private String priceProduct2;
    private Double totalPayment = 0.00;
    private Integer totalItems = 0;

    @Inject
    public StoreShoppingSteps() {
        storeLandingScreen = new StoreLandingScreen();
    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        log.info("FEATURE " + scenario.getUri().toString().substring(scenario.getUri().toString().lastIndexOf("/") + 1) + ": " + scenario.getName() + " " + formatScenarioTags());
    }

    private String formatScenarioTags() {
        String[] tags = scenario.getSourceTagNames().toString().split(",\\s*");
        return String.join(" and ", tags);
    }

    @Given("^Store website has loaded$")
    public void storeWebsiteHasLoaded() {
        Assertion.assertResponseValues("Store website loads successfully", true, storeLandingScreen.getTitleString().isDisplayed());
        Assertion.assertResponseValues("The title on the Store webpage is: ", "STORE", storeLandingScreen.getTitleString().getText());
        Assertion.printAssertion(scenario);
    }

    @Given("^Cart page has loaded$")
    public void CartPageHasLoaded() {
        Assertion.assertResponseValues("Cart page loads successfully", true, storeLandingScreen.getTitleString().isDisplayed());
        Assertion.assertResponseValues("The title on the Cart page is: ", "CART", storeLandingScreen.getTitleString().getText());
        Assertion.printAssertion(scenario);
    }

    @When("^The item ([^\"]*) is added to the cart$")
    public void anItemIsAddedToTheCart(String selectedProduct) {
        product1 = selectedProduct;
        priceProduct1 = storeLandingScreen.getProductPriceString1().getText();
        storeLandingScreen.getAddToCartButton1().click();
        String priceWithoutCurrencySymbol = priceProduct1.substring(1);
        double preTotal = Double.parseDouble(priceWithoutCurrencySymbol);
        totalPayment = totalPayment + preTotal;
        totalItems = totalItems + 1;
    }

    @When("^A second item ([^\"]*) is added to the cart$")
    public void aSecondItemIsAddedToTheCart(String selectedProduct) {
        product2 = selectedProduct;
        priceProduct2 = storeLandingScreen.getProductPriceString2().getText();
        storeLandingScreen.getAddToCartButton2().click();
        String priceWithoutCurrencySymbol = priceProduct2.substring(1);
        double preTotal = Double.parseDouble(priceWithoutCurrencySymbol);
        totalPayment = totalPayment + preTotal;
        totalItems = totalItems + 1;
    }

    @When("^The Cart link is clicked$")
    public void theCartLinkIsClicked() {
        storeLandingScreen.getCartLink().click();
    }

    @When("^The item ([^\"]*) is successfully added to the cart$")
    public void anItemIsSuccessfullyAddedToTheCart(String selectedProduct) {
        anItemIsAddedToTheCart(selectedProduct);
        theCartLinkIsClicked();
        CartPageHasLoaded();
        theSelectedProductExistsInCart();
        theTotalOfItemsMatch();
        theTotalPaymentAmountMatches();
        theSelectedProductHasDeleteButtonInCart();
    }

    @When("^The quantity of the first item is increased$")
    public void theQuantityOfTheFirstItemIsIncreased() {
        storeLandingScreen.getProductIncreaseButton1().click();
        String priceWithoutCurrencySymbol = priceProduct1.substring(1);
        double preTotal = Double.parseDouble(priceWithoutCurrencySymbol);
        totalPayment = totalPayment + preTotal;
        totalItems = totalItems + 1;
    }

    @When("^The clear button is clicked$")
    public void theClearButtonIsClicked() {
        storeLandingScreen.getClearButton().click();
        totalItems = 0;
    }

    @When("^The checkout button is clicked$")
    public void theCheckoutButtonIsClicked() {
        storeLandingScreen.getCheckoutButton().click();
    }

    @When("^The reduce button is clicked for the first item$")
    public void theReduceButtonIsClicked() {
        storeLandingScreen.getProductDeleteButton1().click();
        String priceWithoutCurrencySymbol = priceProduct1.substring(1);
        double preTotal = Double.parseDouble(priceWithoutCurrencySymbol);
        totalPayment = totalPayment - preTotal;
        totalPayment = round(totalPayment,2);
        totalItems = totalItems -1;

    }

    @When("^The delete button is clicked for the second item$")
    public void theDeleteButtonIsClickedForSecondItem() {
        storeLandingScreen.getProductDeleteButton2().click();
        String priceWithoutCurrencySymbol = priceProduct2.substring(1);
        double preTotal = Double.parseDouble(priceWithoutCurrencySymbol);
        totalPayment = totalPayment - preTotal;
        totalPayment = round(totalPayment,2);
        totalItems = totalItems -1;

    }

    @Then("^The selected product exists in cart")
    public void theSelectedProductExistsInCart() {
        Assertion.assertResponseValues("Item exists in Cart:", product1.toUpperCase(), storeLandingScreen.getCartProductString1().getText());
        Assertion.printAssertion(scenario);
    }

    @Then("^The selected product has a delete button in cart")
    public void theSelectedProductHasDeleteButtonInCart() {
        Assertion.assertResponseValues("Delete button appears for selected item:", true, storeLandingScreen.getProductDeleteButton1().exists());
        Assertion.printAssertion(scenario);
    }

    @Then("^The first item has a reduce button in cart")
    public void theFirstItemHasReduceButtonInCart() {
        Assertion.assertResponseValues("Reduce button appears for first item:", true, storeLandingScreen.getProductDeleteButton1().exists());
        Assertion.printAssertion(scenario);
    }

    @Then("^The second item has a delete button in cart")
    public void theSecondItemHasDeleteButtonInCart() {
        Assertion.assertResponseValues("Delete button appears for second item:", true, storeLandingScreen.getProductDeleteButton2().exists());
        Assertion.printAssertion(scenario);
    }

    @Then("^The total of items match")
    public void theTotalOfItemsMatch() {
        Assertion.assertResponseValues("Total of Items:", totalItems.toString(), storeLandingScreen.getTotalItemsString().getText());
        Assertion.printAssertion(scenario);
    }

    @Then("^The total payment amount matches")
    public void theTotalPaymentAmountMatches() {
        Assertion.assertResponseValues("Total Payment Amount $:", "$"+totalPayment.toString(), storeLandingScreen.getTotalPaymentString().getText());
        Assertion.printAssertion(scenario);
    }

    @Then("^The Checkout successfully message is displayed")
    public void theCheckoutSuccessfullyMenssageIsDisplayed() throws SQLException, ClassNotFoundException {
        Assertion.assertResponseValues("Checkout Message:","Checkout successfull", storeLandingScreen.getCheckoutSuccessfullyString().getText());
        Assertion.printAssertion(scenario);
    }

    @Then("^The cart is clear")
    public void theCartIsClear() throws SQLException, ClassNotFoundException {
        Assertion.assertResponseValues("The cart is clear","Your cart is empty", storeLandingScreen.getEmptyCartString().getText());
        Assertion.printAssertion(scenario);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
