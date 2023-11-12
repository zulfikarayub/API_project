package glamit.utilities;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static utilities.ConfigReader.getProperty;

public class apiBase extends Driver {
    public static Logger log = LogManager.getLogger(apiBase.class);
    public String  newIdToken;
    public static String apiEnv, idTknResp, domain, dateFrom, dateTo, page, perPage, GlamitEnv, desasignar;


    @Step("To generate ID token")
    public String idToken() {
        //getting the env from properties file
        apiEnv = getProperty("API_ENV");
        log.info("the environment is ..." + apiEnv);

        // getting the url to generate base URI
        String URI = getProperty("refreshTokenUri");
        log.info("The base URI is .." + URI);
        Response response = given().contentType(ContentType.JSON).filter(new AllureRestAssured()).
                queryParam("refresh_token", getProperty("refreshToken")).when().post(URI).then().extract().response();



        //asserting the status code and getting ID token
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.asString().contains("id_token"));
        newIdToken = response.jsonPath().getString("id_token");
        log.info("The id token is.....    "+newIdToken);
        return newIdToken;
    }

    public String getDateFormat(Date fDate, String formatDt) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDt);
        String date = sdf.format(fDate);
        return date;
    }

//    public String getDateTimeFormat(Date fDate, String formatDt) {
//        Date date = sdf.format(fDate);
//        Timestamp ts =  new Timestamp(date.getTime());
//        SimpleDateFormat sdf = new SimpleDateFormat(formatDt);
//
//        return date;
//    }

    /**
     * This method is used to read json file
     *
     * @param filename - pass the file name of json
     * @return - will return the value of json file
     */
    public JSONObject readJSONFromFile(String filename) {
        JSONObject jsonObject = null;
        try {
            InputStream is = new FileInputStream(new File("src/test/resources/testdata/" + filename));
            if (is == null) {
                throw new NullPointerException("cannot find resource file" + filename);
            }
            JSONTokener tokener = new JSONTokener(is);
            jsonObject = new JSONObject(tokener);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            org.testng.Assert.fail(e.getMessage());
        }
        return jsonObject;
    }


}
