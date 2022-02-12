import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;


public class Main {

    String Base_Url = "https://api.github.com";
    String Token = "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";
    String User_Agent = "simar2500";
    String HeaderV3 = "application/vnd.github.v3+json";

    @Test
    public void createGistWithValidDataReturnsSuccess() {
        String response = given().baseUri(Base_Url)
                .body(" {\n" +
                        "    \"description\": \"UBC IRP Student QA\",\n" +
                        "    \"public\": true,\n" +
                        "    \"files\": {\n" +
                        "        \"file\": {\n" +
                        "            \"content\": \"UBC IRP Student QA!\"\n" +
                        "        }\n" +
                        "    }}")
                .auth().oauth2(Token)
                .header("Accept", HeaderV3)
                .header("User-Agent", User_Agent)
                .when().post("/gists")
                .then().statusCode(HttpStatus.SC_CREATED)
                .extract().response().asString();
        //extracting data to assert content
        JsonPath jsonPath = new JsonPath(response);
        String gistId = jsonPath.getString("id");
        String content = jsonPath.getString("files.file.content");
        Assert.assertEquals(content, "UBC IRP Student QA!");
        //cleaning data
        given().baseUri(Base_Url)
                .auth().oauth2(Token)
                .header("Accept", HeaderV3)
                .header("User-Agent", User_Agent)
                .and().pathParams("gist_id", gistId)
                .when().delete("/gists/{gist_id}")
                .then().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void createGistWithoutFileReturnsBadEntity() {

        given().baseUri("https://api.github.com")
                .body(" {\n" +
                        "    \"description\": \"UBC IRP Student QA\",\n" +
                        "    \"public\": true\n" +
                        "    }")
                .and().auth().oauth2(Token)
                .header("Accept", HeaderV3)
                .header("User-Agent", User_Agent)
                .when().post("/gists")
                .then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    public void createGistWithoutAuthReturnsUnauthorized() {
        given().baseUri("https://api.github.com")
                .body(" {\n" +
                        "    \"description\": \"UBC IRP Student QA\",\n" +
                        "    \"public\": true,\n" +
                        "    \"files\": {\n" +
                        "        \"file\": {\n" +
                        "            \"content\": \"UBC IRP Student QA!\"\n" +
                        "        }\n" +
                        "    }}")
                .header("User-Agent", User_Agent)
                .when().post("/gists")
                .then().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void createGistWithoutUserAgentReturnsUnauthorized() {
        given().baseUri(Base_Url)
                .body(" {\n" +
                        "    \"description\": \"UBC IRP Student QA\",\n" +
                        "    \"public\": true,\n" +
                        "    \"files\": {\n" +
                        "        \"file\": {\n" +
                        "            \"content\": \"UBC IRP Student QA!\"\n" +
                        "        }\n" +
                        "    }}")
                .auth().oauth2(Token)
                .header("Accept", HeaderV3)
                .when().post("/gists")
                .then().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}


