package nz.co.sample.storeScreen;

import nz.co.sample.Element;
import org.openqa.selenium.By;

public class StoreLandingScreen {
    //Overall elements (found on both homepage and cart)
    private Element titleString;
    private Element cartLink;

    //Elements found on homepage
    private Element addToCartButton1;
    private Element productPriceString1;
    private Element addToCartButton2;
    private Element productPriceString2;

    //Elements found on cart
    private Element productDeleteButton1;
    private Element productIncreaseButton1;
    private Element cartProductString1;
    private Element cartPriceString1;
    private Element productDeleteButton2;
    private Element productIncreaseButton2;
    private Element cartProductString2;
    private Element cartPriceString2;
    private Element clearButton;
    private Element checkoutButton;
    private Element checkoutSuccessfullyString;
    private Element emptyCartString;
    private Element totalPaymentString;
    private Element totalItemsString;

    public StoreLandingScreen() {
        //Overall elements (found on both homepage and cart)
        titleString = new Element(By.tagName("h1"));
        cartLink = new Element(By.cssSelector("a[href='/cart']"));

        //Elements found on homepage
        addToCartButton1 = new Element(By.xpath("//p[text()='Bacardi Breezer - Tropical']/following-sibling::div[@class='text-right']/button[text()='Add to cart']"));
        productPriceString1 = new Element(By.xpath("//p[text()='Bacardi Breezer - Tropical']/following-sibling::h3"));
        addToCartButton2 = new Element(By.xpath("//p[text()='Wine - Gato Negro Cabernet']/following-sibling::div[@class='text-right']/button[text()='Add to cart']"));
        productPriceString2 = new Element(By.xpath("//p[text()='Wine - Gato Negro Cabernet']/following-sibling::h3"));

        //Elements found on cart
        productDeleteButton1 = new Element(By.xpath("//button[@class='btn btn-danger btn-sm mb-1']"));
        productIncreaseButton1 = new Element(By.xpath("//button[@class='btn btn-primary btn-sm mr-2 mb-1']"));
        cartProductString1 = new Element(By.cssSelector("div.col-sm-4.p-2 > h5.mb-1"));
        cartPriceString1 = new Element(By.cssSelector("div.col-sm-4.p-2 > p.mb-1"));
        productDeleteButton2 = new Element(By.xpath("(//div[@class='card card-body border-0']/div[@class='row no-gutters py-2'])[2]//button[contains(@class, 'btn-danger')]"));
        productIncreaseButton2 = new Element(By.xpath("//button[@class='btn btn-danger btn-sm mb-2']"));
        cartProductString2 = new Element(By.cssSelector("div.col-sm-4.p-2 > h5.mb-1"));
        cartPriceString2 = new Element(By.cssSelector("div.col-sm-4.p-2 > p.mb-1"));
        clearButton = new Element(By.xpath("//button[@type='button' and @class='btn btn-outlineprimary btn-sm' and text()='CLEAR']"));
        checkoutButton = new Element(By.xpath("//button[@type='button' and @class='btn btn-primary mb-2' and text()='CHECKOUT']"));
        checkoutSuccessfullyString = new Element(By.cssSelector("div.p-3.text-center.text-success p"));
        emptyCartString = new Element(By.cssSelector("div.p-3.text-center.text-muted"));
        totalPaymentString = new Element(By.cssSelector("h3.m-0.txt-right"));
        totalItemsString = new Element(By.xpath("//h4[@class=' mb-3 txt-right']"));
    }

    //Overall elements (found on both homepage and cart)
    public Element getTitleString() { return titleString; }
    public Element getCartLink() { return cartLink; }

    //Elements found on homepage
    public Element getAddToCartButton1() { return addToCartButton1; }
    public Element getProductPriceString1() { return productPriceString1; }
    public Element getAddToCartButton2() { return addToCartButton2; }
    public Element getProductPriceString2() { return productPriceString2; }

    //Elements found on cart
    public Element getProductDeleteButton1() { return productDeleteButton1; }
    public Element getProductIncreaseButton1() { return productIncreaseButton1; }
    public Element getCartProductString1() { return cartProductString1; }
    public Element getCartPriceString1() { return cartPriceString1; }
    public Element getProductDeleteButton2() { return productDeleteButton2; }
    public Element getProductIncreaseButton2() { return productIncreaseButton2; }
    public Element getCartPriceString2() { return cartPriceString2; }
    public Element getClearButton() { return clearButton; }
    public Element getCheckoutButton() { return checkoutButton; }
    public Element getCheckoutSuccessfullyString() { return checkoutSuccessfullyString; }
    public Element getEmptyCartString() { return emptyCartString; }
    public Element getTotalPaymentString() { return totalPaymentString; }
    public Element getTotalItemsString() { return totalItemsString; }
}
