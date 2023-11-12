package glamit.api_tests;

import glamit.utilities.apiBase;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static utilities.ConfigReader.getProperty;

public class eventBusRms extends apiBase {
    public static Logger log = LogManager.getLogger(eventBusRms.class);

    public static String strId, strIncrementId, strOrderId, strExternalId, strCreationDate, strPaymentDate, strProductsUnitPrice, strProductsOriginalPrice,
            strProductsDiscount, strProductsQty, strProductsTotal, strShippingCost,
            strShippingMethod, strBillingStreetName, strBillingStreetNumber, strBillingFloor, strBillingDoorNumber,
            strBillingObservation, strBillingZipcode,strBillingCountry, strBillingProvince, strBillingProvinceName,
            strBillingLocality, strShippingStreetName, strShippingStreetNumber,strShippingFloor, strShippingDoorNumber, strShippingObservation,
            strShippingZipcode, strShippingCountry, strShippingProvince,strShippingProvinceName, strShippingLocality,
             strCustomerName, strCustomerIdentificationNumber, strCustomerId, strCustomerEmail,
            strCustomerPhone, strCustomerBirthdate, strCustomerGroup, strStoreId, strStoreName, strTotalDiscount, strTotalQtyOrdered,
            strRegion, strPaymentMethod, strSubTotal, strTotal, strState, strStatus,  strCouponCode, strCardIssuer,
            strCard, strInstallments, strInterests,strOriginChannel, strOrigin, strType, strRmaId, strEasyCart, strOrderId2,
            strOrderIncrementId, strType2, strRef, strSubTotal2,
            strTotal2, strStatus2, strStatusDescription, strExpireDate, strReceptionDate, strReturnDate, strReason, strPayShippingCost,
            strGiftCardCode, strCae, strNroFactura, strFechaFactura, strVtoCae, strCreatedAt, strUpdatedAt, strPaymentMethod2,
            strRefundShippingCost, strStoreId2, strInterests2, strOrigin2, strManagementStart, strShipmentCarrier, strShippingCost2,
            strShipmentTrackingNumber,  strShippingStreetName2, strShippingStreetNumber2,strShippingFloor2, strShippingDoorNumber2,
            strShippingObservation2,
            strShippingZipcode2, strShippingCountry2, strShippingProvince2,strShippingProvinceName2, strShippingLocality2,
            strNewOrderIncrementId, strNewOrderId, strFinalizedBy,
            strCustomerId2, strIdentificationNumber, strCustomerFullName, strCustomerPhone2, strCustomerEmail2, strCustomerAddressid2,
            strCustomerGroup2, strProductsRpId,
            strProductId2,  strProductsQty2, strProductsPrice, strReturnStock;
    @DataProvider(name = "eventBusCreateOrderData")
    public Object[][] getData() {
        return new Object[][]{
                {"withOutCarrierPickUP", "eventBusRms.json"},
        };
    }


    @Test(priority = 0, description = "EventbusAPI - create order", dataProvider = "eventBusCreateOrderData")
    @Description("EventbusAPI - create order with")
    @Story("BA-1497")
    @TmsLink("BA-1497")
    public void createRmsOrders(String carrierData, String fileName) {

        // getting the id token
        idTknResp = idToken();


        //getting the env and respective URL
        if (apiEnv.equalsIgnoreCase("uat")) {

            domain = getProperty("baseURI_UAT_New");
        }
        // final end point
        String URI = domain + "/v1/eventbus/rma";

        try {
            JSONObject payload = readJSONFromFile(fileName);

            //Generating data using faker class
            strId = String.valueOf(faker.number().numberBetween(100000, 999999));
            strIncrementId = "Automation" + faker.number().digits(10);
            strOrderId = String.valueOf(faker.number().numberBetween(100000, 999999));
            strExternalId = strIncrementId + "-" + String.valueOf(faker.number().numberBetween(100, 999));
            Date dtCr = faker.date().future(3, TimeUnit.DAYS);
            strCreationDate = getDateFormat(dtCr, "yyyy-MM-dd HH:MM:s") ;
            strPaymentDate = strCreationDate;

            //product details
            strProductsUnitPrice = String.valueOf(faker.number().numberBetween(10000, 99999));
            strProductsOriginalPrice = strProductsUnitPrice;
            strProductsDiscount = String.valueOf(faker.number().numberBetween(100, 999));
            strProductsQty = faker.number().digits(1);
            strProductsTotal = strProductsUnitPrice;

            //shipping
            strShippingCost = String.valueOf(faker.number().numberBetween(100, 999));

            //billing address
            strBillingStreetName = faker.address().streetName();
            strBillingStreetNumber = faker.address().streetAddressNumber();
            strBillingFloor = faker.number().digits(1);
            strBillingZipcode = String.valueOf(faker.number().numberBetween(10000, 99999));

            //shipping address
            strShippingStreetName = faker.address().streetName();
            strShippingStreetNumber = faker.address().streetAddressNumber();
            strShippingFloor = faker.number().digits(1);
            strShippingZipcode = String.valueOf(faker.number().numberBetween(10000, 99999));

            //customer
            strCustomerName = faker.name().firstName() + faker.name().lastName();
            strCustomerEmail = strCustomerName + "@mailinatorCom";
            strCustomerPhone = "+54" + faker.phoneNumber().cellPhone();
            strCustomerBirthdate = getDateFormat(dtCr, "yyyy-MM-dd");

            strTotalDiscount = String.valueOf(2* Integer.valueOf(strProductsDiscount));
            strTotalQtyOrdered = String.valueOf(2* Integer.valueOf(strProductsQty));
            strSubTotal =String.valueOf(Integer.valueOf(strTotalQtyOrdered) * Integer.valueOf(strProductsUnitPrice));
            strTotal = String.valueOf(faker.number().numberBetween(1000, 9999));
           // strCard = faker.finance().creditCard();
            strInstallments =String.valueOf(faker.number().numberBetween(1, 9));

            //new order
            strOrderId2 = String.valueOf(faker.number().numberBetween(100000, 999999));
            strOrderIncrementId = String.valueOf(faker.number().numberBetween(1000000000, 999999999));
            strRef = "Auto"+ faker.number().numberBetween(10000000, 9999999) +"-" + faker.number().numberBetween(1, 9);
            strSubTotal2 =String.valueOf(faker.number().numberBetween(10000, 99999));
            strTotal2 = String.valueOf(faker.number().numberBetween(1000, 9999));

            //shipping 2
            strShippingCost2 = String.valueOf(faker.number().numberBetween(100, 999));

            //shipping address 2
            strShippingStreetName2 = faker.address().streetName();
            strShippingStreetNumber2 = faker.address().streetAddressNumber();
            strShippingFloor2 = faker.number().digits(1);
            strShippingZipcode2 = String.valueOf(faker.number().numberBetween(10000, 99999));

            strNewOrderIncrementId = String.valueOf(faker.number().numberBetween(1000000000, 999999999));
            strNewOrderId = String.valueOf(faker.number().numberBetween(100000, 999999));

            //customer 2
            strCustomerFullName = faker.name().firstName() + faker.name().lastName();
            strCustomerEmail2 = strCustomerFullName + "@mailinatorCom";
            strCustomerPhone2 = "+54" + faker.phoneNumber().cellPhone();

            //product details 2
            strProductsPrice = String.valueOf(faker.number().numberBetween(10000, 99999));
            strProductsQty2 = faker.number().digits(1);



            //passing data using faker class

            payload.put("id", strId);
            payload.getJSONObject("order").put("increment_id",strIncrementId);
            payload.getJSONObject("order").put("order_id",strOrderId);
            payload.getJSONObject("order").put("external_id",strExternalId);
            payload.getJSONObject("order").put("creation_date",strCreationDate);
            payload.getJSONObject("order").put("payment_date",strPaymentDate);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(0).put("unit_price", strProductsUnitPrice);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(0).put("original_price", strProductsOriginalPrice);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(0).put("qty", strProductsQty);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(0).put("total", strProductsTotal);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(0).put("discount", strProductsDiscount);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(1).put("unit_price", strProductsUnitPrice);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(1).put("original_price", strProductsOriginalPrice);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(1).put("qty", strProductsQty);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(1).put("total", strProductsTotal);
            payload.getJSONObject("order").getJSONArray("products").getJSONObject(1).put("discount", strProductsDiscount);
            payload.getJSONObject("order").getJSONObject("shipping").put("shipping_cost", strShippingCost);
            payload.getJSONObject("order").getJSONObject("shipping").getJSONObject("billing_address").put("street_name", strBillingStreetName);
            payload.getJSONObject("order").getJSONObject("shipping").getJSONObject("billing_address").put("street_number", strBillingStreetNumber);
            payload.getJSONObject("order").getJSONObject("shipping").getJSONObject("billing_address").put("floor", strBillingFloor);
            payload.getJSONObject("order").getJSONObject("shipping").getJSONObject("billing_address").put("zip_code", strBillingZipcode);
            payload.getJSONObject("order").getJSONObject("shipping").getJSONObject("shipping_address").put("street_name", strShippingStreetName);
            payload.getJSONObject("order").getJSONObject("shipping").getJSONObject("shipping_address").put("street_number", strShippingStreetNumber);
            payload.getJSONObject("order").getJSONObject("shipping").getJSONObject("shipping_address").put("floor", strShippingFloor);
            payload.getJSONObject("order").getJSONObject("shipping").getJSONObject("shipping_address").put("zip_code", strShippingZipcode);
            payload.getJSONObject("order").getJSONObject("customer").put("customer_name", strCustomerName);
            payload.getJSONObject("order").getJSONObject("customer").put("email", strCustomerEmail);
            payload.getJSONObject("order").getJSONObject("customer").put("phone", strCustomerPhone);
            payload.getJSONObject("order").getJSONObject("customer").put("birth_date", strCustomerBirthdate);
            payload.getJSONObject("order").put("total_discount", strTotalDiscount);
            payload.getJSONObject("order").put("total_qty_ordered", strTotalQtyOrdered);
            payload.getJSONObject("order").put("subtotal", strSubTotal);
            payload.getJSONObject("order").put("total", strTotal);
            //payload.getJSONObject("order").put("card", strCard);
            payload.getJSONObject("order").put("installments", strInstallments);
            payload.put("order_id", strOrderId2);
            payload.put("order_increment_id", strOrderIncrementId);
            payload.put("ref", strRef);
            payload.put("subtotal", strSubTotal2);
            payload.put("total", strTotal2);
            payload.getJSONObject("shipment").put("shipping_cost", strShippingCost2);
            payload.getJSONObject("shipment").getJSONObject("shipping_address").put("street_name", strShippingStreetName2);
            payload.getJSONObject("shipment").getJSONObject("shipping_address").put("street_number", strShippingStreetNumber2);
            payload.getJSONObject("shipment").getJSONObject("shipping_address").put("floor", strShippingFloor2);
            payload.getJSONObject("shipment").getJSONObject("shipping_address").put("zip_code", strShippingZipcode2);
            payload.put("new_order_increment_id", strNewOrderIncrementId);
            payload.put("new_order_id", strNewOrderId);
            payload.getJSONObject("customer").put("full_name", strCustomerFullName);
            payload.getJSONObject("customer").put("email", strCustomerEmail2);
            payload.getJSONObject("customer").put("phone", strCustomerPhone2);
            payload.getJSONArray("products").getJSONObject(0).put("price", strProductsPrice);
            payload.getJSONArray("products").getJSONObject(0).put("qty", strProductsQty2);
            payload.getJSONArray("products").getJSONObject(1).put("price", strProductsPrice);
            payload.getJSONArray("products").getJSONObject(1).put("qty", strProductsQty2);




            log.info("payload load is" + payload);
            //hitting the api to get the response
            Response response = given().header(new Header("Authorization", "Bearer " + idTknResp)).filter(new AllureRestAssured()).
                    header("Content-Type", "application/json").
                    when().body(payload.toString()).post(URI).then().extract().response();
            log.info(response.asString());
            // validating the status code
            Assert.assertEquals(response.statusCode(), 200);

            //validation of response
            Assert.assertEquals(response.jsonPath().getString("external_id"), strId);
            Assert.assertEquals(response.jsonPath().getString("order.increment_id"), strIncrementId);
            Assert.assertEquals(response.jsonPath().getString("order.external_id"), strExternalId);
           // Assert.assertEquals(response.jsonPath().getString("order.created_at"), strCreationDate);/confirmed with david hours will be always different*/
            Assert.assertEquals(response.jsonPath().getString("order.payment.payment_date"), strPaymentDate);
            Assert.assertEquals(response.jsonPath().getString("order.products[1].price"), strProductsUnitPrice + ".0");
            Assert.assertEquals(response.jsonPath().getString("order.products[1].original_price"), strProductsOriginalPrice);
            Assert.assertEquals(response.jsonPath().getString("order.products[1].qty"), strProductsQty);
            Assert.assertEquals(response.jsonPath().getString("order.products[1].discount"), strProductsDiscount);
            Assert.assertEquals(response.jsonPath().getString("order.total"), String.valueOf(
                        Integer.valueOf(strProductsOriginalPrice) * (2 * Integer.valueOf(strProductsQty)) -
                                (2 * Integer.valueOf(strProductsDiscount)) + Integer.valueOf(strShippingCost))+".0");
            Assert.assertEquals(response.jsonPath().getString("order.shipment.shipping_cost"), strShippingCost+".0");
            Assert.assertEquals(response.jsonPath().getString("order.shipment.shipping_address.street_name"), strShippingStreetName);
            Assert.assertEquals(response.jsonPath().getString("order.shipment.shipping_address.street_number"), strShippingStreetNumber);
            Assert.assertEquals(response.jsonPath().getString("order.shipment.shipping_address.zip_code"), strShippingZipcode);
            Assert.assertEquals(response.jsonPath().getString("order.shipment.shipping_address.floor"), strShippingFloor);
            Assert.assertEquals(response.jsonPath().getString("order.billing_address.street_name"), strBillingStreetName);
            Assert.assertEquals(response.jsonPath().getString("order.billing_address.street_number"), strBillingStreetNumber);
            Assert.assertEquals(response.jsonPath().getString("order.billing_address.zip_code"), strBillingZipcode);
            Assert.assertEquals(response.jsonPath().getString("order.billing_address.floor"), strBillingFloor);
            Assert.assertEquals(response.jsonPath().getString("order.customer.full_name"), strCustomerName);
            Assert.assertEquals(response.jsonPath().getString("order.customer.email"), strCustomerEmail);
            Assert.assertEquals(response.jsonPath().getString("order.customer.phone"), strCustomerPhone);
            Assert.assertEquals(response.jsonPath().getString("order.customer.birth_date"), strCustomerBirthdate);
            Assert.assertEquals(response.jsonPath().getString("order.total_discount"), strTotalDiscount);
            Assert.assertEquals(response.jsonPath().getString("order.total_qty_ordered"), strTotalQtyOrdered);
            Assert.assertEquals(response.jsonPath().getString("order.subtotal"), strSubTotal+".0");
            Assert.assertEquals(response.jsonPath().getString("order.payment.installments"), strInstallments);
            //Assert.assertEquals(response.jsonPath().getString("order.increment_id"), strOrderIncrementId);/** not in response**/
            Assert.assertEquals(response.jsonPath().getString("ref"), strRef);
            Assert.assertEquals(response.jsonPath().getString("subtotal"), strSubTotal2);
            Assert.assertEquals(response.jsonPath().getString("total"), strTotal2);
            Assert.assertEquals(response.jsonPath().getString("shipment.shipping_cost"), strShippingCost2);
            Assert.assertEquals(response.jsonPath().getString("shipment.shipping_address.street_name"), strShippingStreetName2);
            Assert.assertEquals(response.jsonPath().getString("shipment.shipping_address.street_number"), strShippingStreetNumber2);
            Assert.assertEquals(response.jsonPath().getString("shipment.shipping_address.zip_code"), strShippingZipcode2);
            Assert.assertEquals(response.jsonPath().getString("shipment.shipping_address.floor"), strShippingFloor2);
          //  Assert.assertEquals(response.jsonPath().getString("new_order.increment_id"), strNewOrderIncrementId);/** not in response**/
            Assert.assertEquals(response.jsonPath().getString("customer.full_name"), strCustomerFullName);
            Assert.assertEquals(response.jsonPath().getString("customer.email"), strCustomerEmail2);
            Assert.assertEquals(response.jsonPath().getString("customer.phone"), strCustomerPhone2);
            Assert.assertEquals(response.jsonPath().getString("customer.full_name"), strCustomerFullName);
            Assert.assertEquals(response.jsonPath().getString("customer.email"), strCustomerEmail2);
            Assert.assertEquals(response.jsonPath().getString("customer.phone"), strCustomerPhone2);
            Assert.assertEquals(response.jsonPath().getString("products[0].price"), strProductsPrice);
            Assert.assertEquals(response.jsonPath().getString("products[1].price"), strProductsPrice);
            Assert.assertEquals(response.jsonPath().getString("products[0].qty"), strProductsQty2);
            Assert.assertEquals(response.jsonPath().getString("products[1].qty"), strProductsQty2);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            org.testng.Assert.fail(e.getMessage());

        }


    }

}
